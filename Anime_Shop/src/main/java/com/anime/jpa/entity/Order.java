package com.anime.jpa.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name = "orders")
public class Order extends Base implements Serializable {

	private static final long serialVersionUID = 4702263621301512351L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "address")
	private String address;

	@Column(name = "phone")
	private String phone;
	
	@JsonIgnore
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetails;

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private User user;

	@ManyToOne
	@JoinColumn(name = "orderStatusId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private OrderStatus orderStatus;

	@ManyToOne
	@JoinColumn(name = "paymentMethodId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private PaymentMethod paymentMethod;

	@ManyToOne
	@JoinColumn(name = "shipperId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private Shipper shipper;
}
