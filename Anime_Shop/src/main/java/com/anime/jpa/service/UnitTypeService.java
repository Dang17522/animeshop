package com.anime.jpa.service;

import java.util.List;

import com.anime.jpa.entity.UnitType;

public interface UnitTypeService extends IService<UnitType> {

	List<UnitType> findBykeyword(String keyword);

}
