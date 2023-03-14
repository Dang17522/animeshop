package com.anime.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anime.constants.ActiveConstant;
import com.anime.jpa.entity.CategoryParent;
import com.anime.jpa.repo.CategoryParentRepo;
import com.anime.jpa.service.CategoryParentService;

@Service
public class CategoryParentServiceImpl implements CategoryParentService {

	@Autowired
	private CategoryParentRepo repo;

	@Override
	public List<CategoryParent> findAll() {
		return repo.findAll();
	}

	@Override
	public CategoryParent findById(Long id) {
		Optional<CategoryParent> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Page<CategoryParent> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public CategoryParent create(CategoryParent entity) {
		return repo.saveAndFlush(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public CategoryParent update(CategoryParent entity) {
		return repo.save(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public List<CategoryParent> findByIsActive() {
		return repo.findByIsActive(ActiveConstant.ENABLE);
	}

	@Override
	public Page<CategoryParent> findByIsActive(Pageable pageable) {
		return repo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void deleteLogical(Long id) {
		repo.deleteLogical(ActiveConstant.DISABLED, id);
	}

	@Override
	public List<CategoryParent> findBykeyword(String keyword) {
		return repo.findBykeyword(ActiveConstant.ENABLE, keyword);
	}
}
