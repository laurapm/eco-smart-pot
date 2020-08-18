package com.rainforesteco.spring.data.mongodb.controllers;

import com.rainforesteco.spring.data.mongodb.models.Owner;
import com.rainforesteco.spring.data.mongodb.repositories.OwnerRepository;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest API Controller for Owner queries to MongDB.
 * 
 * @author PabloAceG
 * @date 18/08/2020
 *
 */
@CrossOrigin(origins = "http:localhost:8080")
@RestController
@RequestMapping("/api")
public class OwnerController 
{
	@Autowired
	OwnerRepository ownerRepository;
	
	@GetMapping("owners")
	public ResponseEntity<List<Owner>> getAllOwners(@RequestParam(required = false) String name) {
		try {
			List<Owner> owners = new ArrayList<Owner>();
			
			if (name == null)
				ownerRepository.findAll().forEach(owners::add);
			else
				ownerRepository.findByName(name).forEach(owners::add);
			
			if (owners.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(owners, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	/*
	
	@RequestMapping(method=RequestMethod.GET, value="/owner")
	public Iterable<Owner> owner() {
		return ownerRepository.findAll();
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/owner")
	public Owner save(@RequestBody Owner owner) {
		ownerRepository.save(owner);
		
		return owner;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/owner")
	public Owner show(@PathVariable String id) {
		return ownerRepository.findOne(id);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/owner")
	public Owner update(@PathVariable String id, @RequestBody Owner owner) {
		Owner o = ownerRepository.findOne(id);
		
		if(owner.getUsername() != null) 
			o.setUsername(owner.getUsername());
		if(owner.getName() != null) 
			o.setName(owner.getName());
		if(owner.getSurname() != null) 
			o.setSurname(owner.getSurname());
		if(owner.getCourtesy_title() != null) 
			o.setCourtesy_title(owner.getCourtesy_title());
		if(owner.getPhone() != null) 
			o.setPhone(owner.getPhone());
		if(owner.getEmail() != null) 
			o.setEmail(owner.getEmail());
		if(owner.getPassword() != null) 
			o.setPassword(owner.getPassword());
		
		
		ownerRepository.save(o);
		return owner;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/owner")
	public String delete(@PathVariable String id) {
		Owner owner = ownerRepository.findOne(id);
		ownerRepository.delete(owner);
		
		return "";
	}
	*/

}

