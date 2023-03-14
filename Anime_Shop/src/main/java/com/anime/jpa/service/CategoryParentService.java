package com.anime.jpa.service;

import java.util.List;

import com.anime.jpa.entity.CategoryParent;

public interface CategoryParentService extends IService<CategoryParent> {

	List<CategoryParent> findBykeyword(String keyword);

}
