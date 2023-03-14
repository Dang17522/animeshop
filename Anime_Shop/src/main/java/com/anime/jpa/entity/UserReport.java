package com.anime.jpa.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserReport implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    private String username;
    private String fullname;
    private String email;
    private Long count;
}
