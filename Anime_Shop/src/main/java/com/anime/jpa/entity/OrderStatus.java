package com.anime.jpa.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.anime.constants.ActiveConstant;
import com.anime.constants.OrderStatusConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_status")
public class OrderStatus extends Base implements Serializable {

	private static final long serialVersionUID = 8374287057745530620L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "status")
	private Integer status = OrderStatusConstant.NEW;

	@Column(name = "isActive")
	private Boolean isActive = ActiveConstant.ENABLE;

	@JsonIgnore
	@OneToMany(mappedBy = "orderStatus")
	private List<Order> orders;
}
