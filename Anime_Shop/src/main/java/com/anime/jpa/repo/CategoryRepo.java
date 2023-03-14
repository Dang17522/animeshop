package com.anime.jpa.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.jpa.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

	List<Category> findByIsActive(Boolean isActive);

	Page<Category> findByIsActive(Boolean isActive, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Category c SET c.isActive = ?1 WHERE c.id = ?2")
	void deleteLogical(Boolean isActive, Long id);

    List<Category> findByCategoryParentId(Long id);

    @Query("SELECT c FROM Category c WHERE c.isActive = ?1 AND CONCAT(c.name, c.categoryParent.name) LIKE %?2%")
	List<Category> findBykeyword(Boolean isActive, String keyword);
}
