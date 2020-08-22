package com.rainforest.eco.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rainforest.eco.models.LoginRequest;
import com.rainforest.eco.models.User;
import com.rainforest.eco.repositories.UserRepository;
import com.rainforest.eco.services.Operation;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api")
public class AuthController 
{
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
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
			String password = Operation.getSha256(loginRequest.getPassword());
			
			if(password != _user.getPassword()) {
				return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
			}
			
			return new ResponseEntity<>(_user, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User> createUser(@RequestBody User user)
	{
		try {
			Optional<User> userData = userRepository.findByEmail(user.getEmail());
			
			if (userData.isPresent()) {
				return new ResponseEntity<>(null, HttpStatus.FOUND);
			} else {
				User _user = userRepository.save(
					new User(
						user.getUsername(),
						user.getName(),
						user.getSurname(),
						user.getCourtesy_title(),
						user.getPhone(),
						user.getEmail(),
						Operation.getSha256(user.getPassword())
					)
				);
				
				return new ResponseEntity<>(_user, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
