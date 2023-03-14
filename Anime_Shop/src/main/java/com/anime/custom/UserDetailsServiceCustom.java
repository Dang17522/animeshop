package com.anime.custom;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.anime.jpa.entity.User;
import com.anime.jpa.service.UserService;

public class UserDetailsServiceCustom implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		if (ObjectUtils.isEmpty(user)) {
			throw new UsernameNotFoundException(username + " not found!");
		}
		return new UserDetailsCustom(user);
	}

}
