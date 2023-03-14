package com.anime.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anime.jpa.entity.MultipleImage;
import com.anime.jpa.entity.Product;
import com.anime.jpa.service.MultipleImageService;
import com.anime.jpa.service.ProductService;
import com.anime.utils.SessionUtil;

@Controller
@RequestMapping("/anime-shop")
public class ShopController {

	@Autowired
	private ProductService productService;

	@Autowired
	private MultipleImageService multipleImageService;

	@Autowired
	private SessionUtil session;

	@GetMapping("/shop")
	public String doShowShop(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize, Model model) {
		List<Product> products = new ArrayList<>();
		try {
			Page<Product> pageProducts = productService.findByIsActive(PageRequest.of(pageNum - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("products", products);
		return "/user/shop";
	}

	@GetMapping("/shop/category/{name}")
	public String doFindByCategoryName(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize, Model model,
			@PathVariable("name") String name) {
		List<Product> products = new ArrayList<>();
		try {
			Page<Product> pageProducts = productService.findByCategoryName(name, PageRequest.of(pageNum - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("products", products);
		return "/user/shop";
	}

	@GetMapping("/shop/{slug}")
	public String doFindBySlug(Model model, @PathVariable("slug") String slug, HttpServletRequest request) {
		Product productResp = productService.findBySlug(slug);
		String uri = request.getRequestURI();
		if (uri != null) {
			if (productResp.getView() == 0) {
				productResp.setView(1);
			} else {
				productResp.setView(productResp.getView() + 1);
			}
			productService.update(productResp);
		}
		List<MultipleImage> images = multipleImageService.findByProductId(productResp.getId());
		model.addAttribute("images", images);
		model.addAttribute("product", productResp);
		return "user/productDetail";
	}

	@GetMapping("/shop/size/{size}")
	public String doFindBySize(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize, Model model,
			@PathVariable("size") String size) {
		List<Product> products = new ArrayList<>();
		try {
			Page<Product> pageProducts = productService.findBySize(size, PageRequest.of(pageNum - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("products", products);
		return "/user/shop";
	}

	@GetMapping("/shop/search")
	public String doSearchProduct(Model model, @RequestParam(value = "keyword", required = false) Optional<String> kw,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize) {
		List<Product> products = new ArrayList<>();
		try {
			String keyword = kw.orElse(session.getAttribute("keyword"));
			session.setAttribute("keyword", keyword);
			Page<Product> pageProducts = productService.findByKeyword(keyword,
					PageRequest.of(pageNumber - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("currentPage", pageNumber);
			model.addAttribute("keyword", keyword);
		} catch (Exception e) {
			e.printStackTrace();
			products = productService.findAll();
		}
		model.addAttribute("products", products);
		return "user/shop";
	}

	@GetMapping("/shop/searchPrice")
	public String doSearchPriceProduct(Model model, @RequestParam(value = "min", required = false) Optional<Double> min,
			@RequestParam(value = "max", required = false) Optional<Double> max,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize) {
		List<Product> products = new ArrayList<>();
		try {
			Double minPrice = min.orElse(session.getAttribute("min"));
			Double maxPrice = max.orElse(session.getAttribute("max"));
			session.setAttribute("min", minPrice);
			session.setAttribute("max", maxPrice);
			Page<Product> pageProducts = productService.findByPrice(minPrice, maxPrice,
					PageRequest.of(pageNumber - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("currentPage", pageNumber);
			model.addAttribute("min", minPrice);
			model.addAttribute("max", maxPrice);
		} catch (Exception e) {
			e.printStackTrace();
			products = productService.findAll();
		}
		model.addAttribute("products", products);
		return "user/shop";
	}

	@GetMapping("/shop/sortPriceDesc")
	public String sortPriceDesc(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize, Model model) {
		List<Product> products = new ArrayList<>();
		try {
			Page<Product> pageProducts = productService.sortCaotoThap(PageRequest.of(pageNum - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("products", products);
		return "/user/shop";
	}

	@GetMapping("/shop/sortPriceAsc")
	public String sortPriceAsc(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize, Model model) {
		List<Product> products = new ArrayList<>();
		try {
			Page<Product> pageProducts = productService.sortThaptoCao(PageRequest.of(pageNum - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("products", products);
		return "/user/shop";
	}

	@GetMapping("/shop/sortProductDiscount")
	public String sortProductDiscount(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize, Model model) {
		List<Product> products = new ArrayList<>();
		try {
			Page<Product> pageProducts = productService.sortDiscount(PageRequest.of(pageNum - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("products", products);
		return "/user/shop";
	}
	@GetMapping("/shop/sortView")
	public String sortview(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize, Model model) {
		List<Product> products = new ArrayList<>();
		try {
			Page<Product> pageProducts = productService.sortView(PageRequest.of(pageNum - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("products", products);
		return "/user/shop";
	}
}
