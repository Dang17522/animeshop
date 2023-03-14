package com.anime.jpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.jpa.entity.Shipper;

@Repository
public interface ShipperRepo extends JpaRepository<Shipper, Long> {

	@Query(value = "SELECT * FROM shippers WHERE  name LIKE %?1%", nativeQuery = true)
	List<Shipper> findBykeyword(String keyword);
}
