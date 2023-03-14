package com.anime.jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.anime.jpa.entity.User;
import com.anime.security.Account;

public interface UserService extends IService<User> {
	User findByUsername(String username);

	User findByEmail(String email);

	User doRegister(User entity);

	void updatePassword(String password, Long id);

	User findByUsernameOrEmail(String username);
	
	public void setAccount(Account account);
	
	public User createFromSocial(OAuth2User socialUser);
	
	Page<User> listUserCustomer(Pageable pageable);

	Page<User> findByKeyword( String keyword, Pageable pageable);
	
	public User checkEmail(String email);
}
