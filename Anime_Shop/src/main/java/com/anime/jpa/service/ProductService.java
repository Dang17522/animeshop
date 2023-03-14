package com.anime.jpa.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anime.jpa.entity.Product;

public interface ProductService extends IService<Product> {
	List<Product> findTop10NewProd();

	Product findBySlug(String slug);

	Page<Product> findByCategoryName(String name, Pageable pageable);

	List<Product> findTop10SellingProd();

	List<Product> findTop10ViewsProd();

	List<String> findAllSize();

	Page<Product> findBySize(String size, Pageable pageable);

	Page<Product> findByPrice(Double min, Double max, Pageable pageable);

	Page<Product> findByKeyword(String keyword, Pageable pageable);

	Page<Product> sortCaotoThap(Pageable pageable);

	Page<Product> sortThaptoCao(Pageable pageable);

	Page<Product> sortDiscount(Pageable pageable);

	Page<Product> sortView(Pageable pageable);
	
	void updateQuantityAndSelled(Integer quantity, Integer selled, Long id);
}
