package com.anime.api.admin;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anime.jpa.entity.Role;
import com.anime.jpa.entity.User;
import com.anime.jpa.entity.UserRole;
import com.anime.jpa.service.RoleService;
import com.anime.jpa.service.UserRoleService;
import com.anime.jpa.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserApi {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private RoleService roleService;

	@GetMapping("/customer")
	public ResponseEntity<?> doGetCustomers(Pageable pageable) {
		Page<User> pageUser = userService.listUserCustomer(pageable);
		return ResponseEntity.ok(pageUser);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> doFindById(@PathVariable("id") Long id) {
		User user = userService.findById(id);
		if (ObjectUtils.isEmpty(user)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok(user);
	}

	@PostMapping()
	public ResponseEntity<?> doCreate(@RequestBody User user) {
		userService.create(user);
		Role role = roleService.findById((long) 3);
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		userRoleService.create(userRole);
		return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
	}

	@PutMapping()
	public ResponseEntity<?> doUpdate(@RequestBody User user) {
		return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void doDelete(@PathVariable("id") Long id) {
		userService.deleteLogical(id);
	}

	@GetMapping("/search")
	public ResponseEntity<?> doSearch(Pageable pageable, @RequestParam("keyword") String keyword) {
		Page<User> pageUser = userService.findByKeyword(keyword, pageable);
		if (pageUser.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(pageUser);
	}
	
	@GetMapping("/customer/check")
	public ResponseEntity<?> doCheckCustomers(String email) {
		User user = userService.checkEmail(email);
		return ResponseEntity.ok(user);
	}
}
