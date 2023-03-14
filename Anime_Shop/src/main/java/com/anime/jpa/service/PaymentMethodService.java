package com.anime.jpa.service;

import java.util.List;

import com.anime.jpa.entity.PaymentMethod;

public interface PaymentMethodService extends IService<PaymentMethod> {

	List<PaymentMethod> findBykeyword(String keyword);

}
