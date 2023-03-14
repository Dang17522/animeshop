package com.anime.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/anime-shop/admin/payment")
public class PaymentMethodController {

	@GetMapping()
	public String doShowAll() {
		return "/admin/paymentMethod";
	}
}
