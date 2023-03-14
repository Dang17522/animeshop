package com.anime.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anime.constants.ActiveConstant;
import com.anime.jpa.entity.Post;
import com.anime.jpa.repo.PostRepo;
import com.anime.jpa.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo repo;

	@Override
	public List<Post> findAll() {
		return repo.findAll();
	}

	@Override
	public Post findById(Long id) {
		Optional<Post> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Page<Post> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public Post create(Post entity) {
		return repo.saveAndFlush(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public Post update(Post entity) {
		return repo.save(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}
	
	@Override
	public List<Post> findByIsActive() {
		return repo.findByIsActive(ActiveConstant.ENABLE);
	}

	@Override
	public Page<Post> findByIsActive(Pageable pageable) {
		return repo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void deleteLogical(Long id) {
		repo.deleteLogical(ActiveConstant.DISABLED, id);
	}

	@Override
	public Page<Post> findByKeyword(String keyword, Pageable pageable) {
		return repo.findByKeyword(ActiveConstant.ENABLE, keyword, pageable);
	}
}
