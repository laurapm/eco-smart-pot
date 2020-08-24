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
import com.rainforest.eco.services.Log;

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
		String LogHeader = "[/users: getAllUsers] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			List<User> users = new ArrayList<User>();
			
			if (name == null)
				userRepository.findAll().forEach(users::add);
			else
				userRepository.findByName(name).forEach(users::add);
			
			if (users.isEmpty()) {
				Log.logger.info(LogHeader + "No users found");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<User> getUserById(@PathVariable("id") String id)
	{
		String LogHeader = "[/users/id: getUserById] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<User> userData = userRepository.findById(id);
			
			if (userData.isPresent()) {
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(userData.get(), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "No user found with id:" + id);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/users", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<User> updateUser(@RequestBody User user) 
	{
		String LogHeader = "[/users: updateUser] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<User> userData = userRepository.findById(user.getId());
			
			if (userData.isPresent()) 
			{
				User _user = userData.get();
				_user.setId            (user.getId());
				_user.setUsername      (user.getUsername());
				_user.setName          (user.getName());
				_user.setSurname       (user.getSurname());
				_user.setPhone         (user.getPhone());
				_user.setEmail         (user.getEmail());
				_user.setPassword      (user.getPassword());
				
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "The user to update has not been found");
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id)
	{
		String LogHeader = "[/users/id: deleteUser] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<User> user = userRepository.findById(id);
			
			if (user.isPresent()) {
				Log.logger.info(LogHeader + "The user \"" + user.get().getUsername() + "\" is going to be deleted");
				userRepository.deleteById(id);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/users", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteAllUsers()
	{
		String LogHeader = "[/deleteAllUsers: deleteAllUsers] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			userRepository.deleteAll();
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
