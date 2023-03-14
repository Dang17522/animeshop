package com.anime.jpa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.anime.constants.ActiveConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review extends Base implements Serializable {

	private static final long serialVersionUID = 1185019110818795941L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "rating")
	private Float rating;

	@Column(name = "comment")
	private String comment;

	@Column(name = "like")
	private Integer like;

	@Column(name = "dislike")
	private Integer dislike;

	@Column(name = "isActive")
	private Boolean isActive = ActiveConstant.ENABLE;

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private User user;

	@ManyToOne
	@JoinColumn(name = "productId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private Product product;
}
