package com.anime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anime.constants.SessionConstant;
import com.anime.dto.CartDto;
import com.anime.jpa.service.CartService;
import com.anime.utils.SessionUtil;

@Controller
@RequestMapping("/anime-shop/shop")
public class CartController {
	
	@Autowired
	private SessionUtil session;
	
	@Autowired
	private CartService cartService;

	@GetMapping("/cart-view")
	public String doShowCartView() {
		return "/user/cart";
	}

	@GetMapping("/update")
	public String doGetUpdate(@RequestParam("productId") Long productId, 
							  @RequestParam("quantity") Integer quantity,
							  @RequestParam("isReplace") boolean isReplace) {

		CartDto currentCart = session.getAttribute(SessionConstant.CURRENT_CART);
		cartService.updateCart(currentCart, productId, quantity, isReplace);
		return "user/cart::#viewCartFragment";

	}
	
	@GetMapping("/checkout")
	public String doShowCheckout() {
		return "user/checkout";
	}

	@GetMapping("/clearAll")
	public String clearCart(CartDto cartDto) {
		cartService.clearAll(cartDto);
		session.removeAttribute(SessionConstant.CURRENT_CART);
		return "redirect:/anime-shop/shop/cart-view";
	}
}
