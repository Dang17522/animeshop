package com.anime.controller;

import com.anime.jpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/anime-shop")
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/index")
    public String doShowIndex(Model model) {
        model.addAttribute("productNews", productService.findTop10NewProd());
        model.addAttribute("productMostViews", productService.findTop10ViewsProd());
        return "/user/index";
    }

    @GetMapping("/blog")
    public String doShowBlog() {
        return "/user/blog";
    }
}
