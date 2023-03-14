package com.anime.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.anime.custom.UserDetailsServiceCustom;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean 
    public UserDetailsService userDetailsService() {
		return new UserDetailsServiceCustom();
	}
	
	@Bean 
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(getPasswordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.cors().disable();
		
		http.authorizeHttpRequests()
			.antMatchers("/anime-shop/shop/checkout", "/anime-shop/my-order").authenticated()
			.antMatchers("/anime-shop/admin/home-index").hasAnyRole("ADMIN", "STAFF")
			.antMatchers("/anime-shop/admin/category-parent", "/anime-shop/admin/category", "/anime-shop/admin/product",
						 "/anime-shop/admin/payment", "/anime-shop/admin/post", "/anime-shop/admin/shipper",
						 "/anime-shop/admin/unit-type")
			.hasAnyRole("ADMIN", "STAFF")
			.anyRequest()
			.permitAll();
		
		http
			.authenticationProvider(daoAuthenticationProvider());
		
		http.exceptionHandling()
			.accessDeniedPage("/security/access/denied");
		
		http.formLogin()
			.loginPage("/anime-shop/login")
			.loginProcessingUrl("/anime-shop/login")
			.failureUrl("/anime-shop/login/error");
		
		http.formLogin()
			.successHandler((req, resp, auth) ->{
				if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN") || r.getAuthority().equals("ROLE_STAFF"))) {
					resp.sendRedirect("/anime-shop/admin/home-index");
				} else {
					resp.sendRedirect("/anime-shop/index");
				}
			});
		
		http.rememberMe().tokenValiditySeconds(30*24*60*60);
		
		http.logout()
			.logoutUrl("/anime-shop/logout")
			.logoutSuccessUrl("/anime-shop/index")
			.addLogoutHandler(new SecurityContextLogoutHandler());
		
		http.oauth2Login()
			.defaultSuccessUrl("/anime-shop/oauth2/login/success", true)
			.failureUrl("/anime-shop/oauth2/login/failure");
		
        return http.build();
    }

}
