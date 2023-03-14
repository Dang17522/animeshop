package com.anime.jpa.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.jpa.entity.Poster;

@Repository
public interface PosterRepo extends JpaRepository<Poster, Long> {

	List<Poster> findByIsActive(Boolean isActive);

	Page<Poster> findByIsActive(Boolean isActive, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Poster p SET p.isActive = ?1 WHERE p.id = ?2")
	void deleteLogical(Boolean isActive, Long id);
}
