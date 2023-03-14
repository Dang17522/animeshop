package com.anime.jpa.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@SuppressWarnings("unused")
@EntityListeners(AuditingEntityListener.class)
public class Base {

	@CreatedBy
	@Column(name = "createdBy", updatable = false)
	private String createdBy;

	@LastModifiedBy
	@Column(name = "updatedBy", insertable = false)
	private String updatedBy;

	@CreationTimestamp
	@Column(name = "createDate", updatable = false, insertable = true)
	protected Timestamp createDate;

	@UpdateTimestamp
	@Column(name = "updateDate", insertable = false, updatable = true)
	private Timestamp updateDate;
}
