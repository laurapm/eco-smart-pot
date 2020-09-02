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

import com.rainforest.eco.models.User;
import com.rainforest.eco.repositories.UserRepository;
import com.rainforest.eco.requests.LoginRequest;
import com.rainforest.eco.services.Log;
import com.rainforest.eco.services.Operation;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api")
public class AuthController 
{
	@Autowired
	UserRepository userRepository;
	
	/**
	 * Login user to webpage. Checkc if the user with email or username exists
	 * in the database. The password needs to match the one stored.
	 * 
	 * @param loginRequest LoginRequest
	 * - user (string) -> can be the email or the username
	 * - password -> does not have to be encoded
	 * 
	 * @return  User
	 * - Username (string)
	 * - Name (string)
	 * - Surname (string)
	 * - Phone (string)
	 * - Email (string)
	 * - Password (string) -> returns it codified. 
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User> loginUser(@RequestBody LoginRequest loginRequest) {
		String LogHeader = "[/login: loginUser]";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			
			// User can make the login using the username or the email
			Optional<User> userData = userRepository.findByUsername(loginRequest.getUser());
			if (!userData.isPresent()) {
				userData = userRepository.findByEmail(loginRequest.getUser());
				
				if (!userData.isPresent()) {
					Log.logger.info(LogHeader + "No user with that username or email");
					return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
				}
			}
			
			// Encode password
			User _user = userData.get();
			String password = Operation.getSha256(loginRequest.getPassword());
			
			// Stored password and request password match
			if(!password.equals(_user.getPassword())) {
				Log.logger.info(LogHeader + "The request password and the stored password do not match");
				return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
			}
			
			// Return user
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(_user, HttpStatus.OK);
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Sign up (create) a new user.
	 * 
	 * @param user User
	 * - Username (string)
	 * - Name (string)
	 * - Surname (string)
	 * - Phone (string)
	 * - Email (string)
	 * - Password (string) -> codification made inside code.
	 * 
	 * @return Status code and User created.
	 */
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User> signupUser(@RequestBody User user)
	{
		String LogHeader = "[/signup: signupUser] ";
		
		try {			
			// Email cannot be in use
			Log.logger.info(LogHeader + "Requested");
			Optional<User> userData = userRepository.findByEmail(user.getEmail());
			
			// Email in use
			if (userData.isPresent()) {
				Log.logger.info(LogHeader + "Email is already being used");
				return new ResponseEntity<>(null, HttpStatus.FOUND);
			} 
			
			// Username cannot be in use
			userData = userRepository.findByUsername(user.getUsername());
			// Username in use
			if (userData.isPresent()) {
				Log.logger.info(LogHeader + "Username is already being used");
				return new ResponseEntity<>(null, HttpStatus.FOUND);
			}
			
			// Sign up (create) user
			User _user = userRepository.save(
				new User(
					user.getUsername(),
					user.getName(),
					user.getSurname(),
					user.getPhone(),
					user.getEmail(),
					Operation.getSha256(user.getPassword())
				)
			);
				
			// Return user
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(_user, HttpStatus.OK);
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
