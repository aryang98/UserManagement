package com.Aryan.UserManagementSystem.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Aryan.UserManagementSystem.Entity.User;
import com.Aryan.UserManagementSystem.Exception.UserNotFoundException;
import com.Aryan.UserManagementSystem.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		logger.info("Fetching all users");
		return userRepository.findAll();
	}

	public User saveUser(User user) {
		logger.info("Saving user: {}", user.getUsername());
		return userRepository.save(user);
	}

	public void deleteUser(Long id) {
		logger.info("Deleting user with id: {}", id);
		if (!userRepository.existsById(id)) {
			throw new UserNotFoundException("User not found with id: " + id);
		}
		userRepository.deleteById(id);
	}

	public Optional<User> findByUsername(String username) {
		logger.info("Finding user by username: {}", username);
		return userRepository.findByUsername(username);
	}
}
