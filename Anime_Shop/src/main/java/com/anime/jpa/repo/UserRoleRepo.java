package com.anime.jpa.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.jpa.entity.UserRole;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {

	List<UserRole> findByIsActive(Boolean isActive);

	Page<UserRole> findByIsActive(Boolean isActive, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE UserRole ur SET ur.isActive = ?1 WHERE ur.id = ?2")
	void deleteLogical(Boolean isActive, Long id);
}
