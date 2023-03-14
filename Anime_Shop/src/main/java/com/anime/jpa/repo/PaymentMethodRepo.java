package com.anime.jpa.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.jpa.entity.PaymentMethod;

@Repository
public interface PaymentMethodRepo extends JpaRepository<PaymentMethod, Long> {

	List<PaymentMethod> findByIsActive(Boolean isActive);

	Page<PaymentMethod> findByIsActive(Boolean isActive, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE PaymentMethod p SET p.isActive = ?1 WHERE p.id = ?2")
	void deleteLogical(Boolean isActive, Long id);

	@Query(value = "SELECT * FROM payment_methods WHERE isActive = ?1 AND name LIKE %?2%", nativeQuery = true)
	List<PaymentMethod> findBykeyword(Boolean isActive, String keyword);
}
