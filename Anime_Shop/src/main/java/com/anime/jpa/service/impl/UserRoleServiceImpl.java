package com.anime.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anime.constants.ActiveConstant;
import com.anime.jpa.entity.UserRole;
import com.anime.jpa.repo.UserRoleRepo;
import com.anime.jpa.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleRepo repo;

	@Override
	public List<UserRole> findAll() {
		return repo.findAll();
	}

	@Override
	public UserRole findById(Long id) {
		Optional<UserRole> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Page<UserRole> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public UserRole create(UserRole entity) {
		return repo.saveAndFlush(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public UserRole update(UserRole entity) {
		return repo.save(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public List<UserRole> findByIsActive() {
		return repo.findByIsActive(ActiveConstant.ENABLE);
	}

	@Override
	public Page<UserRole> findByIsActive(Pageable pageable) {
		return repo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void deleteLogical(Long id) {
		repo.deleteLogical(ActiveConstant.DISABLED, id);
	}
}
