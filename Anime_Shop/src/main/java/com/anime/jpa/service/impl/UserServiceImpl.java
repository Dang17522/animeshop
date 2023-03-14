package com.anime.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.anime.constants.ActiveConstant;
import com.anime.jpa.entity.Role;
import com.anime.jpa.entity.User;
import com.anime.jpa.entity.UserRole;
import com.anime.jpa.repo.RoleRepo;
import com.anime.jpa.repo.UserRepo;
import com.anime.jpa.repo.UserRoleRepo;
import com.anime.jpa.service.UserService;
import com.anime.security.Account;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo repo;

	@Autowired
	private UserRoleRepo userRoleRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public List<User> findAll() {
		return repo.findAll();
	}

	@Override
	public User findById(Long id) {
		Optional<User> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public User create(User entity) {
		return repo.saveAndFlush(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public User update(User entity) {
		return repo.save(entity);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public List<User> findByIsActive() {
		return repo.findByIsActive(ActiveConstant.ENABLE);
	}

	@Override
	public Page<User> findByIsActive(Pageable pageable) {
		return repo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void deleteLogical(Long id) {
		repo.deleteLogical(ActiveConstant.DISABLED, id);
	}

	@Override
	public User findByUsername(String username) {
		return repo.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return repo.findByEmail(email);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void updatePassword(String password, Long id) {
		repo.updatePassword(password, id);
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public User doRegister(User entity) {
		repo.saveAndFlush(entity);
		Role role = roleRepo.findById((long) 3).get();
		UserRole userRole = new UserRole();
		userRole.setUser(entity);
		userRole.setRole(role);
		userRoleRepo.save(userRole);
		return repo.saveAndFlush(entity);
	}

	@Override
	public User findByUsernameOrEmail(String username) {
		return repo.findByUsernameOrEmail(username);
	}

	@Override
	public void setAccount(Account account) {
		Authentication auth = new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Override
	public User createFromSocial(OAuth2User socialUser) {
		User user = new User(socialUser);
		repo.save(user);
		return this.findByUsernameOrEmail(user.getEmail());
	}

	@Override
	public Page<User> listUserCustomer(Pageable pageable) {
		return repo.listUserCustomer(ActiveConstant.ENABLE, pageable);
	}

	@Override
	public Page<User> findByKeyword(String keyword, Pageable pageable) {
		return repo.findByKeyword(ActiveConstant.ENABLE, keyword, pageable);
	}

	@Override
	public User checkEmail(String email) {
		return repo.checkEmail(email);
	}
}
