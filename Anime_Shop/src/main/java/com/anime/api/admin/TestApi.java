package com.anime.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anime.jpa.entity.User;
import com.anime.jpa.service.UserService;

@RestController
public class TestApi {

	@Autowired
	private UserService userService;
	
	@GetMapping("/customer")
	public ResponseEntity<Page<User>> users(Pageable pageable) {
		return new ResponseEntity<>(userService.listUserCustomer(pageable), HttpStatus.OK);
	}
}
