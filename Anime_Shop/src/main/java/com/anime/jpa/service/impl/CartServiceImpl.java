package com.anime.jpa.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anime.constants.OrderStatusConstant;
import com.anime.dto.CartDetailDto;
import com.anime.dto.CartDto;
import com.anime.jpa.entity.Order;
import com.anime.jpa.entity.OrderStatus;
import com.anime.jpa.entity.PaymentMethod;
import com.anime.jpa.entity.Product;
import com.anime.jpa.entity.Shipper;
import com.anime.jpa.entity.User;
import com.anime.jpa.service.CartService;
import com.anime.jpa.service.OrderDetailService;
import com.anime.jpa.service.OrderService;
import com.anime.jpa.service.OrderStatusService;
import com.anime.jpa.service.PaymentMethodService;
import com.anime.jpa.service.ProductService;
import com.anime.jpa.service.ShipperService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private PaymentMethodService paymentMethodService;

	@Autowired
	private ShipperService shipperService;

	@Autowired
	private OrderStatusService orderStatusService;

	@Autowired
	private HttpServletRequest request;

	@Override
	public CartDto updateCart(CartDto cart, Long productId, Integer quantity, boolean isReplace) {
		Product product = productService.findById(productId);
		Map<Long, CartDetailDto> details = cart.getDetails();

		if (!details.containsKey(productId)) {
			CartDetailDto cartDetailDto = createNewCartDetail(product, quantity);
			details.put(productId, cartDetailDto);
		} else if (quantity > 0) {
			if (isReplace) {
				details.get(productId).setQuantity(quantity);
			} else {
				Integer currentQuantity = details.get(productId).getQuantity();
				Integer newQuantity = currentQuantity + quantity;
				details.get(productId).setQuantity(newQuantity);
			}
		} else {
			details.remove(productId);
		}

		cart.setTotalQuantity(getTotalQuantity(cart));
		cart.setTotalPrice(getTotalPrice(cart));
		return cart;
	}

	@Override
	public Integer getTotalQuantity(CartDto cart) {
		Integer totalQuantity = 0;
		Map<Long, CartDetailDto> details = cart.getDetails();
		return totalQuantity += details.values().stream().mapToInt(item -> item.getQuantity()).sum();
	}

	@Override
	public Double getTotalPrice(CartDto cart) {
		Double totalPrice = 0D;
		Map<Long, CartDetailDto> details = cart.getDetails();
		return totalPrice += details.values().stream().mapToDouble(item -> (item.getQuantity() * item.getPrice()))
				.sum();
	}

	@Override
	public void clearAll(CartDto cart) {
		Map<Long, CartDetailDto> details = cart.getDetails();
		details.clear();
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void checkout(CartDto cartDto, User user, String address, String phone, Long paymentMethodId)
			throws Exception {
		if (StringUtils.isAnyEmpty(address, phone)) {
			throw new Exception("Address or Phone must be not null or empty or whitespace!");
		}

		Order order = new Order();
		String createBy = request.getRemoteUser();
		PaymentMethod paymentMethod = paymentMethodService.findById(paymentMethodId);
		Shipper shipper = shipperService.findById((long) 1);
		OrderStatus orderStatus = orderStatusService.findByStatus(OrderStatusConstant.NEW);

		order.setUser(user);
		order.setAddress(address);
		order.setPhone(phone);
		order.setPaymentMethod(paymentMethod);
		order.setShipper(shipper);
		order.setCreatedBy(createBy);
		order.setOrderStatus(orderStatus);

		Order orderResponse = orderService.create(order);

		if (ObjectUtils.isEmpty(orderResponse)) {
			throw new Exception("Insert Orders Failed!");
		}

		Product product = null;
		for (CartDetailDto cartDetailDto : cartDto.getDetails().values()) {
			product = productService.findById(cartDetailDto.getProductId());

			if (cartDetailDto.getQuantity() <= product.getQuantity()) {
				cartDetailDto.setOrderId(orderResponse.getId());
				orderDetailService.insert(cartDetailDto);

				Integer newQuantity = product.getQuantity() - cartDetailDto.getQuantity();
				productService.updateQuantityAndSelled(newQuantity, product.getSelled() + 1, product.getId());
			} else {
				throw new Exception("Order quantity must be less than current product quantity!");
			}

		}
		orderStatusService.create(orderStatus);
	}

	private CartDetailDto createNewCartDetail(Product product, Integer quantity) {
		CartDetailDto cartDetailDto = new CartDetailDto();
		cartDetailDto.setProductId(product.getId());
		cartDetailDto.setName(product.getName());
		cartDetailDto.setImage(product.getImages().get(0).getName());
		cartDetailDto.setQuantity(quantity);
		cartDetailDto.setPrice(product.getPrice());
		cartDetailDto.setSlug(product.getSlug());
		return cartDetailDto;
	}

}
