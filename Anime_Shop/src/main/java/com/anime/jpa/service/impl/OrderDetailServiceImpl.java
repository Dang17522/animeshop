package com.anime.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anime.dto.CartDetailDto;
import com.anime.jpa.entity.OrderDetail;
import com.anime.jpa.repo.OrderDetailRepo;
import com.anime.jpa.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrderDetailRepo repo;

	@Override
	public List<OrderDetail> findAll() {
		return repo.findAll();
	}

	@Override
	public OrderDetail findById(Long id) {
		Optional<OrderDetail> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Page<OrderDetail> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public OrderDetail create(OrderDetail entity) {
		return repo.saveAndFlush(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public OrderDetail update(OrderDetail entity) {
		return repo.save(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public List<OrderDetail> findByIsActive() {
		return null;
	}

	@Override
	public Page<OrderDetail> findByIsActive(Pageable pageable) {
		return null;
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void deleteLogical(Long id) {
	}

	@Transactional(value = TxType.REQUIRED)
	@Override
	public void insert(CartDetailDto cartDetailDto) {
		repo.insert(cartDetailDto);
	}
	
	@Override
	public Page<OrderDetail> findByOrderId(Long id, Pageable pageable) {
		return repo.findByOrderId(id, pageable);
	}
}
