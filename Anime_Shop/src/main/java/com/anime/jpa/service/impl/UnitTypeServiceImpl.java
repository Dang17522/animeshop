package com.anime.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anime.constants.ActiveConstant;
import com.anime.jpa.entity.UnitType;
import com.anime.jpa.repo.UnitTypeRepo;
import com.anime.jpa.service.UnitTypeService;

@Service
public class UnitTypeServiceImpl implements UnitTypeService {

	@Autowired
	private UnitTypeRepo repo;

	@Override
	public List<UnitType> findAll() {
		return repo.findAll();
	}

	@Override
	public UnitType findById(Long id) {
		Optional<UnitType> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Page<UnitType> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public UnitType create(UnitType entity) {
		return repo.saveAndFlush(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public UnitType update(UnitType entity) {
		return repo.save(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public List<UnitType> findByIsActive() {
		return repo.findByIsActive(ActiveConstant.ENABLE);
	}

	@Override
	public Page<UnitType> findByIsActive(Pageable pageable) {
		return repo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void deleteLogical(Long id) {
		repo.deleteLogical(ActiveConstant.DISABLED, id);
	}

	@Override
	public List<UnitType> findBykeyword(String keyword) {
		return repo.findBykeyword(ActiveConstant.ENABLE, keyword);
	}
}
