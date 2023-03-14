package com.anime.jpa.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.jpa.entity.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o WHERE o.orderStatus.status = ?1 AND o.user.username = ?2")
	Page<Order> findOrdersByOrderStatusAndUsername(Integer status, String username, Pageable pageable);
}
