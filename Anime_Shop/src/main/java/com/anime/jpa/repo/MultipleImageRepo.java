package com.anime.jpa.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.jpa.entity.MultipleImage;

@Repository
public interface MultipleImageRepo extends JpaRepository<MultipleImage, Long> {

	List<MultipleImage> findByIsActive(Boolean isActive);

	Page<MultipleImage> findByIsActive(Boolean isActive, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE MultipleImage mi SET mi.isActive = ?1 WHERE mi.id = ?2")
	void deleteLogical(Boolean isActive, Long id);

	List<MultipleImage> findByProductId(Long id);
}
