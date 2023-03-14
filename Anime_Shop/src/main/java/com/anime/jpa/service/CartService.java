package com.anime.jpa.service;

import com.anime.dto.CartDto;
import com.anime.jpa.entity.User;

public interface CartService {
	// add, update, delete to cart
	CartDto updateCart(CartDto cart, Long productId, Integer quantity, boolean isReplace);

	Integer getTotalQuantity(CartDto cart);

	Double getTotalPrice(CartDto cart);

	void clearAll(CartDto cart);

	void checkout(CartDto cartDto, User user, String address, String phone, Long paymentMethodId) throws Exception;
}
