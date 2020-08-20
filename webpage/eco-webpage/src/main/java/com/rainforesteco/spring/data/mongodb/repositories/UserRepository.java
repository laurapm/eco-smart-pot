package com.rainforesteco.spring.data.mongodb.repositories;

import java.util.List;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;

import com.rainforesteco.spring.data.mongodb.models.Owner;

public interface OwnerRepository extends MongoRepository<Owner, String> 
{
	List<Owner> findByName(String name); 	
	List<Owner> findByUsername(String username);
}
