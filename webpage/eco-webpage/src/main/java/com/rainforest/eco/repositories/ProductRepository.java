package com.rainforest.eco.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rainforest.eco.models.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> 
{
	Optional<Product> findByName(String name);
	
	List<Product> findByPrice(double price);
}
