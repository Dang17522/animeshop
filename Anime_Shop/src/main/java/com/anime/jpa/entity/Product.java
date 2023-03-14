package com.anime.jpa.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.anime.constants.ActiveConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "products")
public class Product extends Base implements Serializable {

	private static final long serialVersionUID = -4741500190632639850L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "price")
	private Double price;

	@Column(name = "slug")
	private String slug;

	@Column(name = "discount")
	private Float discount;

	@Column(name = "selled")
	private Integer selled;

	@Column(name = "view")
	private Integer view;

	@Column(name = "size")
	private String size;

	@Column(name = "nameMain")
	private String nameMain;

	@Column(name = "material")
	private String material;

	@Column(name = "series")
	private String series;

	@Column(name = "status")
	private String status;

	@Column(name = "description")
	private String description;

	@Column(name = "isActive")
	private Boolean isActive = ActiveConstant.ENABLE;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<MultipleImage> images;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<Post> posts;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<Review> reviews;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetails;

	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private Category category;

	@ManyToOne
	@JoinColumn(name = "unitTypeId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private UnitType unitType;
}
