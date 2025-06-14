package com.abbYs.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abbYs.dao.UserDAO;
import com.abbYs.model.UserModel;

@RestController
public class UserController {
	@Autowired
	private UserDAO userDAO;
	@PostMapping("/users")
	public ResponseEntity<Map<String, Object>> validateUser(@RequestBody UserModel user) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			String role = userDAO.validateUser(user.getEmail(), user.getPassword());
			if (role == null || role.equals("Invalid credentials")) {
				response.put("success", false);
				response.put("message", "Invalid credentials");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			response.put("success", true);
            response.put("role", role);
            return ResponseEntity.ok(response);
		}
		catch (Exception e) {
			response.put("message", "Error validating user: " + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
