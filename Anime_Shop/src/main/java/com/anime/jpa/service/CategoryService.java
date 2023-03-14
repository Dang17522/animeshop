package com.anime.jpa.service;

import java.util.List;

import com.anime.jpa.entity.Category;

public interface CategoryService extends IService<Category> {
    List<Category> findByCategoryParentId(Long id);

	List<Category> findBykeyword(String keyword);
}
