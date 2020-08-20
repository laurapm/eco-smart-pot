package com.rainforesteco.spring.data.mongodb.controllers;

import java.security.MessageDigest;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rainforesteco.spring.data.mongodb.models.User;
import com.rainforesteco.spring.data.mongodb.payload.requests.LoginRequest;
import com.rainforesteco.spring.data.mongodb.repositories.UserRepository;

/**
 * REST API Controller for Authentication against eco database in MongoDB.
 * 
 * @author pablo
 * @creationDate 19/08/2020
 *
 */
@CrossOrigin(
		origins="http:localhost:8080", 
		methods={RequestMethod.GET,RequestMethod.POST}
	)
@RestController
@RequestMapping("/api")
public class AuthController 
{
	@Autowired
	UserRepository userRepository;
	
	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestBody LoginRequest loginRequest) {
		try {
			Optional<User> userData = userRepository.findByUsername(loginRequest.getUser());
			
			if (!userData.isPresent()) {
				userData = userRepository.findByEmail(loginRequest.getUser());
				
				if (!userData.isPresent()) {
					return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
				}
			}
			
			User _user = userData.get();
			String password = getSha256(loginRequest.getPassword());
			
			if(password != _user.getPassword()) {
				return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
			}
			
			return new ResponseEntity<>(_user, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private String getSha256(String message) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(message.getBytes("UTF-8"));
			
			String shaKey = String.format("%064x", hash);
			
			return shaKey;
		} catch (Exception e) {
			return null;
		}
	}
	
	
}
