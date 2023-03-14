package com.anime.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anime.jpa.entity.User;
import com.anime.jpa.service.UserService;

@Service("Auth")
public class AuthUtil {

	@Autowired
	private UserService userService;

	@Autowired
	private HttpServletRequest request;

	public User getInforUser() {
		String username = request.getRemoteUser();
		return userService.findByUsername(username);
	}
}
