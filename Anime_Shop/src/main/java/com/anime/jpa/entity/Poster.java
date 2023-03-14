package com.anime.jpa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.anime.constants.ActiveConstant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posters")
public class Poster extends Base implements Serializable {

	private static final long serialVersionUID = -6479967763831891978L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "image")
	private String image;

	@Column(name = "note")
	private String note;

	@Column(name = "isActive")
	private Boolean isActive = ActiveConstant.ENABLE;
}
