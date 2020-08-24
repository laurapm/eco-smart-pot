package com.rainforest.eco.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rainforest.eco.models.Device;

@Repository
public interface DeviceRepository extends CrudRepository<Device, String> 
{
	List<Device> findByPlant(ObjectId plant);
	List<Device> findByOwner(ObjectId owner);
	List<Device> findByModel(String model);
}
