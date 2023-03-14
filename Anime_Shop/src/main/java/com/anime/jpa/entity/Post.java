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
@Table(name = "posts")
public class Post extends Base implements Serializable {

	private static final long serialVersionUID = 8386181239674201977L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "subject")
	private String subject;

	@Column(name = "body")
	private String body;

	@Column(name = "view")
	private Integer view;

	@Column(name = "slug")
	private String slug;

	@Column(name = "image")
	private String image;

	@Column(name = "description")
	private String description;

	@Column(name = "isActive")
	private Boolean isActive = ActiveConstant.ENABLE;

	@ManyToOne
	@JoinColumn(name = "productId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private Product product;
}
