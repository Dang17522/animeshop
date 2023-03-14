package com.anime.custom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.anime.jpa.entity.User;
import com.anime.jpa.entity.UserRole;

public class UserDetailsCustom implements UserDetails {

	private static final long serialVersionUID = 1281311442217818043L;

	private User user;

	public UserDetailsCustom(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<UserRole> userRoles = user.getUserRoles();
		List<SimpleGrantedAuthority> roles = new ArrayList<>();
		for (UserRole userRole : userRoles) {
			roles.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getName()));
		}
		return roles;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
