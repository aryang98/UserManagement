package com.Aryan.UserManagementSystem.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.Aryan.UserManagementSystem.Entity.User;
import com.Aryan.UserManagementSystem.Service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> getAllUsers() {
		logger.info("GET request to fetch all users");
		return userService.getAllUsers();
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public User createUser(@RequestBody User user) {
		logger.info("POST request to create user: {}", user.getUsername());
		return userService.saveUser(user);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(@PathVariable Long id) {
		logger.info("DELETE request to delete user with id: {}", id);
		userService.deleteUser(id);
	}
}
