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
import com.rainforest.eco.services.Log;
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
		String LogHeader = "[/login: loginUser]";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<User> userData = userRepository.findByUsername(loginRequest.getUser());
			
			if (!userData.isPresent()) {
				userData = userRepository.findByEmail(loginRequest.getUser());
				
				if (!userData.isPresent()) {
					Log.logger.info(LogHeader + "No user with that username or email");
					return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
				}
			}
			
			User _user = userData.get();
			String password = Operation.getSha256(loginRequest.getPassword());
			
			if(!password.equals(_user.getPassword())) {
				Log.logger.info(LogHeader + "The request password and the stored password do not match");
				return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(_user, HttpStatus.OK);
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User> signupUser(@RequestBody User user)
	{
		String LogHeader = "[/signupUser: loginUser] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<User> userData = userRepository.findByEmail(user.getEmail());
			
			if (userData.isPresent()) {
				Log.logger.info(LogHeader + "Email is already being used");
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
				
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(_user, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
