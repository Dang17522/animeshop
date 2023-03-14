package com.anime.jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anime.jpa.entity.Post;

public interface PostService extends IService<Post> {


	Page<Post> findByKeyword(String keyword, Pageable pageable);

}
