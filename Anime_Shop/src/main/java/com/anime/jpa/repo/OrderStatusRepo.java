package com.anime.jpa.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.jpa.entity.OrderStatus;

@Repository
public interface OrderStatusRepo extends JpaRepository<OrderStatus, Long> {

	List<OrderStatus> findByIsActive(Boolean isActive);

	Page<OrderStatus> findByIsActive(Boolean isActive, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE OrderStatus os SET os.isActive = ?1 WHERE os.id = ?2")
	void deleteLogical(Boolean isActive, Long id);

	OrderStatus findByStatus(Integer status);
}
