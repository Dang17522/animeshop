package com.anime.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anime.constants.ActiveConstant;
import com.anime.jpa.entity.Category;
import com.anime.jpa.repo.CategoryRepo;
import com.anime.jpa.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo repo;

	@Override
	public List<Category> findAll() {
		return repo.findAll();
	}

	@Override
	public Category findById(Long id) {
		Optional<Category> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public Category create(Category entity) {
		return repo.saveAndFlush(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public Category update(Category entity) {
		return repo.save(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public List<Category> findByIsActive() {
		return repo.findByIsActive(ActiveConstant.ENABLE);
	}

	@Override
	public Page<Category> findByIsActive(Pageable pageable) {
		return repo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void deleteLogical(Long id) {
		repo.deleteLogical(ActiveConstant.DISABLED, id);
	}

	@Override
	public List<Category> findByCategoryParentId(Long id) {
		return repo.findByCategoryParentId(id);
	}

	@Override
	public List<Category> findBykeyword(String keyword) {
		return repo.findBykeyword(ActiveConstant.ENABLE, keyword);
	}
}
