package com.anime.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/anime-shop/admin/unit-type")
public class UnitTypeController {

	@GetMapping()
	public String doShowAll() {
		return "/admin/unitType";
	}
}
