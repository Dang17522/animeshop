package com.anime.constants;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashPasswordConstant {
	public static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

	private HashPasswordConstant() {}
}
