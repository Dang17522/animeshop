package com.anime.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anime.constants.ActiveConstant;
import com.anime.jpa.entity.Poster;
import com.anime.jpa.repo.PosterRepo;
import com.anime.jpa.service.PosterService;

@Service
public class PosterServiceImpl implements PosterService {

	@Autowired
	private PosterRepo repo;

	@Override
	public List<Poster> findAll() {
		return repo.findAll();
	}

	@Override
	public Poster findById(Long id) {
		Optional<Poster> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Page<Poster> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public Poster create(Poster entity) {
		return repo.saveAndFlush(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public Poster update(Poster entity) {
		return repo.save(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}
	
	@Override
	public List<Poster> findByIsActive() {
		return repo.findByIsActive(ActiveConstant.ENABLE);
	}

	@Override
	public Page<Poster> findByIsActive(Pageable pageable) {
		return repo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void deleteLogical(Long id) {
		repo.deleteLogical(ActiveConstant.DISABLED, id);
	}
}
