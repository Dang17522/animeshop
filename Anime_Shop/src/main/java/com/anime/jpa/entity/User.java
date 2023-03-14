package com.anime.jpa.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.anime.constants.ActiveConstant;
import com.anime.security.Account;
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
@Table(name = "users")
public class User extends Base implements Serializable, Account {

	private static final long serialVersionUID = -6076752328010130022L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "email")
	private String email;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "gender")
	private Boolean gender;

	@Column(name = "password")
	private String password;

	@Column(name = "isActive")
	private Boolean isActive = ActiveConstant.ENABLE;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<UserRole> userRoles;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Review> reviews;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Order> orders;

	public User(OAuth2User socialUser) {
		this.username = socialUser.getName();
		this.email = socialUser.getAttribute("email");
		this.fullname = socialUser.getAttribute("name");
		this.isActive = true;
		this.gender = true;
		this.password = "";
		this.avatar = "default.jpg";
		this.createDate = new Timestamp(new Date().getTime());
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
