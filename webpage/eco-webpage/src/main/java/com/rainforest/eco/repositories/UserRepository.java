package com.rainforest.eco.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rainforest.eco.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> 
{
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	
	List<User> findByName(String name); 	
	
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
