package com.rainforest.eco.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rainforest.eco.models.Measurements;

@Repository
public interface MeasurementsRepository extends CrudRepository<Measurements, String> 
{
	List<Measurements> findByDevice(ObjectId device);
	List<Measurements> findByPlant(ObjectId plant);
}
