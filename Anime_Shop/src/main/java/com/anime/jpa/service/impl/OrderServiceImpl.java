package com.anime.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anime.jpa.entity.Order;
import com.anime.jpa.repo.OrderRepo;
import com.anime.jpa.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo repo;

	@Override
	public List<Order> findAll() {
		return repo.findAll();
	}

	@Override
	public Order findById(Long id) {
		Optional<Order> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Page<Order> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public Order create(Order entity) {
		return repo.saveAndFlush(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public Order update(Order entity) {
		return repo.save(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public List<Order> findByIsActive() {
		return null;
	}

	@Override
	public Page<Order> findByIsActive(Pageable pageable) {
		return null;
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void deleteLogical(Long id) {
	}

	@Override
	public Page<Order> findOrdersByOrderStatusAndUsername(Integer status, String username, Pageable pageable) {
		return repo.findOrdersByOrderStatusAndUsername(status, username, pageable);
	}
}
