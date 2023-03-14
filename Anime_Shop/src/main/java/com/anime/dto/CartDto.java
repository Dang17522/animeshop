package com.anime.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto implements Serializable {
	private static final long serialVersionUID = -8101818627375933022L;

	private Long orderId;
	private String address;
	private String phone;
	private Double totalPrice = 0D;
	private Integer totalQuantity = 0;
	private Map<Long, CartDetailDto> details = new HashMap<>();
}
