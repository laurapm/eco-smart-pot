package com.rainforest.eco.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rainforest.eco.models.User;
import com.rainforest.eco.repositories.UserRepository;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api")
public class UserController 
{
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String name) 
	{
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
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	@ResponseBody
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
	
	@RequestMapping(value="/users/", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<User> updateUser(@RequestParam(required=false) String id, @RequestBody User user) 
	{
		try {
			Optional<User> userData; 
			
			if (id == null)
				userData = userRepository.findById(id);
			else
				userData = userRepository.findByUsername(user.getUsername());
			
			
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
	
	@RequestMapping(value="/users/{username}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("username") String username)
	{
		try {
			User user = userRepository.findByUsername(username).get();
			
			userRepository.deleteById(user.getId());
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/users/", method=RequestMethod.DELETE)
	@ResponseBody
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
