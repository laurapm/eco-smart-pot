package com.rainforesteco.spring.data.mongodb.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.rainforesteco.spring.data.mongodb.models.User;

public interface UserRepository extends CrudRepository<User, String> 
{
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	
	List<User> findByName(String name); 	
	
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
