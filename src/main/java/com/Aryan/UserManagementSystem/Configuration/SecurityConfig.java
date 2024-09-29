package com.Aryan.UserManagementSystem.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.Aryan.UserManagementSystem.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserService userService;

	public SecurityConfig(UserService userService) {
		this.userService = userService;
	}

	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/users").hasAnyRole("USER", "ADMIN")
						.requestMatchers("/users/**").hasRole("ADMIN").anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			var user = userService.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					AuthorityUtils.createAuthorityList(user.getRole()));
		}).passwordEncoder(passwordEncoder());
	}

	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
