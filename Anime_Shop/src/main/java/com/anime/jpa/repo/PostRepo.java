package com.anime.jpa.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.jpa.entity.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

	List<Post> findByIsActive(Boolean isActive);

	Page<Post> findByIsActive(Boolean isActive, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Post p SET p.isActive = ?1 WHERE p.id = ?2")
	void deleteLogical(Boolean isActive, Long id);

	@Query("SELECT p FROM Post p WHERE p.isActive = ?1 AND CONCAT(p.subject, p.slug, p.body, p.description, p.product.name) LIKE %?2%")
	Page<Post> findByKeyword(Boolean isActive, String keyword, Pageable pageable);
}
