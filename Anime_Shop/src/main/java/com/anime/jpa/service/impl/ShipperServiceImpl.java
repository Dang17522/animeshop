package com.anime.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anime.jpa.entity.Shipper;
import com.anime.jpa.repo.ShipperRepo;
import com.anime.jpa.service.ShipperService;

@Service
public class ShipperServiceImpl implements ShipperService {

	@Autowired
	private ShipperRepo repo;

	@Override
	public List<Shipper> findAll() {
		return repo.findAll();
	}

	@Override
	public Shipper findById(Long id) {
		Optional<Shipper> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Page<Shipper> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public Shipper create(Shipper entity) {
		return repo.saveAndFlush(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public Shipper update(Shipper entity) {
		return repo.save(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public List<Shipper> findByIsActive() {
		return null;
	}

	@Override
	public Page<Shipper> findByIsActive(Pageable pageable) {
		return null;
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void deleteLogical(Long id) {
	}

	@Override
	public List<Shipper> findBykeyword(String keyword) {
		return repo.findBykeyword(keyword);
	}

	@Override
	public <S extends Shipper> Page<S> findAll(Example<S> example, Pageable pageable) {
		return repo.findAll(example, pageable);
	}

	@Override
	public <S extends Shipper> S save(S entity) {
		return repo.save(entity);
	}

	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}
	
	
	
}
