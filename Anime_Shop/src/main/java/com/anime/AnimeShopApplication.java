package com.anime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@EnableJpaAuditing(auditorAwareRef = "audiAwareListener")
public class AnimeShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimeShopApplication.class, args);
	}

}
