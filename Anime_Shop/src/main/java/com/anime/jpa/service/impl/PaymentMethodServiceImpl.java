package com.anime.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anime.constants.ActiveConstant;
import com.anime.jpa.entity.PaymentMethod;
import com.anime.jpa.repo.PaymentMethodRepo;
import com.anime.jpa.service.PaymentMethodService;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

	@Autowired
	private PaymentMethodRepo repo;

	@Override
	public List<PaymentMethod> findAll() {
		return repo.findAll();
	}

	@Override
	public PaymentMethod findById(Long id) {
		Optional<PaymentMethod> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Page<PaymentMethod> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public PaymentMethod create(PaymentMethod entity) {
		return repo.saveAndFlush(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public PaymentMethod update(PaymentMethod entity) {
		return repo.save(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}
	
	@Override
	public List<PaymentMethod> findByIsActive() {
		return repo.findByIsActive(ActiveConstant.ENABLE);
	}

	@Override
	public Page<PaymentMethod> findByIsActive(Pageable pageable) {
		return repo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void deleteLogical(Long id) {
		repo.deleteLogical(ActiveConstant.DISABLED, id);
	}

	@Override
	public List<PaymentMethod> findBykeyword(String keyword) {
		return repo.findBykeyword(ActiveConstant.ENABLE, keyword);
	}
}
