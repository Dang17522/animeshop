package com.anime.jpa.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IService<T> {

	List<T> findAll();

	T findById(Long id);

	Page<T> findAll(Pageable pageable);

	T create(T entity);

	T update(T entity);

	void delete(Long id);
	
	List<T> findByIsActive();

	Page<T> findByIsActive(Pageable pageable);

	void deleteLogical(Long id);
}
