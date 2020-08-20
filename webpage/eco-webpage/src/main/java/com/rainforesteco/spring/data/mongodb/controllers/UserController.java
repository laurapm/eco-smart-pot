package com.rainforesteco.spring.data.mongodb.controllers;

import com.rainforesteco.spring.data.mongodb.models.Owner;
import com.rainforesteco.spring.data.mongodb.repositories.OwnerRepository;

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
	
	@PostMapping("owners")
	public ResponseEntity<Owner> createOwner(@RequestBody Owner owner)
	{
		try {
			Owner _owner = ownerRepository.save(
				new Owner(
					owner.getUsername(),
					owner.getName(),
					owner.getSurname(),
					owner.getCourtesy_title(),
					owner.getPhone(),
					owner.getEmail(),
					owner.getPassword()
				)
			);
			
			return new ResponseEntity<>(_owner, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
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
	
	@GetMapping("owners/{id}")
	public ResponseEntity<Owner> getOwnerById(@PathVariable("id") String id)
	{
		try {
			Optional<Owner> ownerData = ownerRepository.findById(id);
			
			if (ownerData.isPresent()) {
				return new ResponseEntity<>(ownerData.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("owners/{id}")
	public ResponseEntity<Owner> updateOwner(@PathVariable("id") String id, @RequestBody Owner owner) 
	{
		try {
			Optional<Owner> ownerData = ownerRepository.findById(id);
			
			if (ownerData.isPresent()) 
			{
				Owner _owner = ownerData.get();
				_owner.setUsername(owner.getUsername());
				_owner.setName(owner.getName());
				_owner.setSurname(owner.getSurname());
				_owner.setCourtesy_title(owner.getCourtesy_title());
				_owner.setPhone(owner.getPhone());
				_owner.setEmail(owner.getEmail());
				_owner.setPassword(owner.getPassword());
				
				return new ResponseEntity<>(ownerRepository.save(_owner), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("owners/{id}")
	public ResponseEntity<HttpStatus> deleteOwner(@PathVariable("id") String id)
	{
		try {
			ownerRepository.deleteById(id);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("owners")
	public ResponseEntity<HttpStatus> deleteAllOwners()
	{
		try {
			ownerRepository.deleteAll();
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
