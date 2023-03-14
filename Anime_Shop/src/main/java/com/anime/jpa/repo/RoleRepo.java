package com.anime.jpa.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.jpa.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

	List<Role> findByIsActive(Boolean isActive);

	Page<Role> findByIsActive(Boolean isActive, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Role r SET r.isActive = ?1 WHERE r.id = ?2")
	void deleteLogical(Boolean isActive, Long id);
}
