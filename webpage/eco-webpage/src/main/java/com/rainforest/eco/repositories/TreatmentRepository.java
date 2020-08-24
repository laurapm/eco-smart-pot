package com.rainforest.eco.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rainforest.eco.models.Treatment;

@Repository
public interface TreatmentRepository extends CrudRepository<Treatment, String> 
{
	List<Treatment> findByDevice(ObjectId device);
	List<Treatment> findByPlant(ObjectId plant);
	List<Treatment> findByType(String type);
	List<Treatment> findByTitle(String title);
}
