package com.rainforesteco.spring.data.mongodb.controllers;

import com.rainforesteco.spring.data.mongodb.models.User;
import com.rainforesteco.spring.data.mongodb.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest API Controller for User queries against eco database in MongoDB.
 * 
 * @author PabloAceG
 * @creationDate 18/08/2020
 *
 */
@CrossOrigin(origins = "http:localhost:8080")
@RestController
@RequestMapping("/api")
public class UserController 
{
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("users")
	public ResponseEntity<User> createUser(@RequestBody User user)
	{
		try {
			User _user = userRepository.save(
				new User(
					user.getUsername(),
					user.getName(),
					user.getSurname(),
					user.getCourtesy_title(),
					user.getPhone(),
					user.getEmail(),
					user.getPassword()
				)
			);
			
			return new ResponseEntity<>(_user, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("users")
	public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String name) {
		try {
			List<User> users = new ArrayList<User>();
			
			if (name == null)
				userRepository.findAll().forEach(users::add);
			else
				userRepository.findByName(name).forEach(users::add);
			
			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@GetMapping("users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") String id)
	{
		try {
			Optional<User> userData = userRepository.findById(id);
			
			if (userData.isPresent()) {
				return new ResponseEntity<>(userData.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User user) 
	{
		try {
			Optional<User> userData = userRepository.findById(id);
			
			if (userData.isPresent()) 
			{
				User _user = userData.get();
				_user.setUsername      (user.getUsername());
				_user.setName          (user.getName());
				_user.setSurname       (user.getSurname());
				_user.setCourtesy_title(user.getCourtesy_title());
				_user.setPhone         (user.getPhone());
				_user.setEmail         (user.getEmail());
				_user.setPassword      (user.getPassword());
				
				return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("users/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id)
	{
		try {
			userRepository.deleteById(id);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("users")
	public ResponseEntity<HttpStatus> deleteAllUsers()
	{
		try {
			userRepository.deleteAll();
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
