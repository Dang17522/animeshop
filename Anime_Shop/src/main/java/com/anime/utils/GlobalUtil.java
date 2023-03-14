package com.anime.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anime.jpa.entity.Category;
import com.anime.jpa.entity.CategoryParent;
import com.anime.jpa.entity.PaymentMethod;
import com.anime.jpa.entity.Poster;
import com.anime.jpa.entity.Product;
import com.anime.jpa.entity.UnitType;
import com.anime.jpa.service.CategoryParentService;
import com.anime.jpa.service.CategoryService;
import com.anime.jpa.service.PaymentMethodService;
import com.anime.jpa.service.PosterService;
import com.anime.jpa.service.ProductService;
import com.anime.jpa.service.UnitTypeService;

@Service("Utils")
public class GlobalUtil {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryParentService categoryParentService;

	@Autowired
	private PosterService posterService;

	@Autowired
	private ProductService productService;

	@Autowired
	private PaymentMethodService paymentMethodService;

	@Autowired
	private UnitTypeService unitTypeService;

	public List<Category> getCategories() {
		List<Category> categories = categoryService.findByIsActive();
		return !categories.isEmpty() ? categories : null;
	}

	public List<CategoryParent> getCategoryParents() {
		List<CategoryParent> categoriesParents = categoryParentService.findByIsActive();
		return !categoriesParents.isEmpty() ? categoriesParents : null;
	}

	public List<Poster> getPosters() {
		List<Poster> posters = posterService.findByIsActive();
		return !posters.isEmpty() ? posters : null;
	}

	public String getOutOfStock() {
		return "Hết hàng";
	}

	public List<String> getSizes() {
		List<String> sizes = productService.findAllSize();
		return !sizes.isEmpty() ? sizes : null;
	}

	public List<PaymentMethod> getPaymentMethods() {
		List<PaymentMethod> paymentMethods = paymentMethodService.findByIsActive();
		return !paymentMethods.isEmpty() ? paymentMethods : null;
	}

	public List<Product> getProducts() {
		List<Product> products = productService.findByIsActive();
		return !products.isEmpty() ? products : null;
	}

	public List<UnitType> getUnitTypes() {
		List<UnitType> unitTypes = unitTypeService.findByIsActive();
		return !unitTypes.isEmpty() ? unitTypes : null;
	}

}
