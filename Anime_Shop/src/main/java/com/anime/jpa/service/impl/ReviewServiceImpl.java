package com.anime.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anime.constants.ActiveConstant;
import com.anime.jpa.entity.Review;
import com.anime.jpa.repo.ReviewRepo;
import com.anime.jpa.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepo repo;

	@Override
	public List<Review> findAll() {
		return repo.findAll();
	}

	@Override
	public Review findById(Long id) {
		Optional<Review> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Page<Review> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public Review create(Review entity) {
		return repo.saveAndFlush(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public Review update(Review entity) {
		return repo.save(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}
	
	@Override
	public List<Review> findByIsActive() {
		return repo.findByIsActive(ActiveConstant.ENABLE);
	}

	@Override
	public Page<Review> findByIsActive(Pageable pageable) {
		return repo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void deleteLogical(Long id) {
		repo.deleteLogical(ActiveConstant.DISABLED, id);
	}
}
