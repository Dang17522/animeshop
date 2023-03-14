package com.anime.jpa.service;

import com.anime.jpa.entity.OrderStatus;

public interface OrderStatusService extends IService<OrderStatus> {
	OrderStatus findByStatus(Integer status);
}
