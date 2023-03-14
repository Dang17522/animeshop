package com.anime.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailDto implements Serializable {
	private static final long serialVersionUID = 8620544573580568311L;

	private Long orderId;
	private Long productId;
	private String slug;
	private String name;
	private Double price;
	private Integer quantity;
	private String image;
}
