package com.rainforest.eco.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rainforest.eco.models.Plant;

@Repository
public interface PlantRepository extends CrudRepository<Plant, String> 
{
	Optional<Plant> findByScientificName(String scientific_name);
	
	List<Plant> findByFamily(String family);
}
