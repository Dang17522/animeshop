package com.anime.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anime.constants.ActiveConstant;
import com.anime.jpa.entity.MultipleImage;
import com.anime.jpa.repo.MultipleImageRepo;
import com.anime.jpa.service.MultipleImageService;

@Service
public class MultipleImageServiceImpl implements MultipleImageService {

	@Autowired
	private MultipleImageRepo repo;

	@Override
	public List<MultipleImage> findAll() {
		return repo.findAll();
	}

	@Override
	public MultipleImage findById(Long id) {
		Optional<MultipleImage> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Page<MultipleImage> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public MultipleImage create(MultipleImage entity) {
		return repo.saveAndFlush(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public MultipleImage update(MultipleImage entity) {
		return repo.save(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public List<MultipleImage> findByIsActive() {
		return repo.findByIsActive(ActiveConstant.ENABLE);
	}

	@Override
	public Page<MultipleImage> findByIsActive(Pageable pageable) {
		return repo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void deleteLogical(Long id) {
		repo.deleteLogical(ActiveConstant.DISABLED, id);
	}

	@Override
	public List<MultipleImage> findByProductId(Long id) {
		return repo.findByProductId(id);
	}
	
	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void saveImages(List<MultipleImage> images) {
		for(MultipleImage image: images) {
			repo.save(image);
		}
	}
}
