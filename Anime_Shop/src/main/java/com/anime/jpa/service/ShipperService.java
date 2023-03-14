package com.anime.jpa.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anime.jpa.entity.Shipper;

public interface ShipperService extends IService<Shipper> {

	List<Shipper> findBykeyword(String keyword);

	<S extends Shipper> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends Shipper> S save(S entity);

	void deleteById(Long id);

}
