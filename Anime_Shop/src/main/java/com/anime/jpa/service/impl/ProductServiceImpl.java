package com.anime.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anime.constants.ActiveConstant;
import com.anime.jpa.entity.Product;
import com.anime.jpa.repo.ProductRepo;
import com.anime.jpa.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo repo;

	@Override
	public List<Product> findAll() {
		return repo.findAll();
	}

	@Override
	public Product findById(Long id) {
		Optional<Product> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public Product create(Product entity) {
		return repo.saveAndFlush(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public Product update(Product entity) {
		return repo.save(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public List<Product> findByIsActive() {
		return repo.findByIsActive(ActiveConstant.ENABLE);
	}

	@Override
	public Page<Product> findByIsActive(Pageable pageable) {
		return repo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void deleteLogical(Long id) {
		repo.deleteLogical(ActiveConstant.DISABLED, id);
	}

	@Override
	public List<Product> findTop10NewProd() {
		return repo.findTop10NewProd(ActiveConstant.ENABLE);
	}

	@Override
	public Product findBySlug(String slug) {
		return repo.findBySlug(slug);
	}

	@Override
	public Page<Product> findByCategoryName(String name, Pageable pageable) {
		return repo.findByCategoryName(name, pageable);
	}

	@Override
	public List<Product> findTop10SellingProd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findTop10ViewsProd() {
		return repo.findTop10ViewsProd(ActiveConstant.ENABLE);
	}

	@Override
	public List<String> findAllSize() {
		return repo.findAllSize(ActiveConstant.ENABLE);
	}

	@Override
	public Page<Product> findBySize(String size, Pageable pageable) {
		return repo.findBySize(ActiveConstant.ENABLE, size, pageable);
	}

	@Override
	public Page<Product> findByKeyword(String keyword, Pageable pageable) {
		return repo.findByKeyword(ActiveConstant.ENABLE, keyword, pageable);
	}

	@Override
	public Page<Product> findByPrice(Double min, Double max, Pageable pageable) {
		return repo.findByPrice(min, max, ActiveConstant.ENABLE, pageable);
	}

	@Override
	public Page<Product> sortCaotoThap(Pageable pageable) {
		return repo.sortCaotoThap(ActiveConstant.ENABLE, pageable);
	}

	@Override
	public Page<Product> sortThaptoCao(Pageable pageable) {
		return repo.sortThaptoCao(ActiveConstant.ENABLE, pageable);
	}

	@Override
	public Page<Product> sortDiscount(Pageable pageable) {
		return repo.sortDiscount(ActiveConstant.ENABLE, pageable);
	}

	@Override
	public Page<Product> sortView(Pageable pageable) {
		return repo.sortView(ActiveConstant.ENABLE, pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void updateQuantityAndSelled(Integer quantity, Integer selled, Long id) {
		repo.updateQuantityAndSelled(quantity, selled, id);
	}
}
