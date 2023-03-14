package com.anime.jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anime.dto.CartDetailDto;
import com.anime.jpa.entity.OrderDetail;

public interface OrderDetailService extends IService<OrderDetail> {
	void insert(CartDetailDto cartDetailDto);

	Page<OrderDetail> findByOrderId(Long id, Pageable pageable);
}
