package com.anime.jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anime.jpa.entity.Order;

public interface OrderService extends IService<Order> {
	Page<Order> findOrdersByOrderStatusAndUsername(Integer status, String username, Pageable pageable);
}
