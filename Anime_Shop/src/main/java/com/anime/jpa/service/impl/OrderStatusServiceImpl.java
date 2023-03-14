package com.anime.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anime.constants.ActiveConstant;
import com.anime.jpa.entity.OrderStatus;
import com.anime.jpa.repo.OrderStatusRepo;
import com.anime.jpa.service.OrderStatusService;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

	@Autowired
	private OrderStatusRepo repo;

	@Override
	public List<OrderStatus> findAll() {
		return repo.findAll();
	}

	@Override
	public OrderStatus findById(Long id) {
		Optional<OrderStatus> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Page<OrderStatus> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public OrderStatus create(OrderStatus entity) {
		return repo.saveAndFlush(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public OrderStatus update(OrderStatus entity) {
		return repo.save(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public List<OrderStatus> findByIsActive() {
		return repo.findByIsActive(ActiveConstant.ENABLE);
	}

	@Override
	public Page<OrderStatus> findByIsActive(Pageable pageable) {
		return repo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void deleteLogical(Long id) {
		repo.deleteLogical(ActiveConstant.DISABLED, id);
	}

	@Override
	public OrderStatus findByStatus(Integer status) {
		return repo.findByStatus(status);
	}
}
