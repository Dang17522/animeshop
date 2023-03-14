package com.anime.jpa.service;

import java.util.List;

import com.anime.jpa.entity.MultipleImage;

public interface MultipleImageService extends IService<MultipleImage> {

	List<MultipleImage>findByProductId(Long id);
	void saveImages(List<MultipleImage> images);
}
