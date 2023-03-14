package com.anime.jpa.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.jpa.entity.UnitType;

@Repository
public interface UnitTypeRepo extends JpaRepository<UnitType, Long> {

	List<UnitType> findByIsActive(Boolean isActive);

	Page<UnitType> findByIsActive(Boolean isActive, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE UnitType ut SET ut.isActive = ?1 WHERE ut.id = ?2")
	void deleteLogical(Boolean isActive, Long id);
	
	@Query(value = "SELECT * FROM unit_types WHERE isActive = ?1 AND name LIKE %?2%", nativeQuery = true)
	List<UnitType> findBykeyword(Boolean isActive, String keyword);
}
