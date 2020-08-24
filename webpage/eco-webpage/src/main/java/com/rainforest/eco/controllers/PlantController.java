package com.rainforest.eco.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rainforest.eco.models.Plant;
import com.rainforest.eco.repositories.PlantRepository;
import com.rainforest.eco.services.Log;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api")
public class PlantController 
{
	@Autowired
	PlantRepository plantRepository;
	
	@RequestMapping(value="/plants", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Plant> createPlant(@RequestBody Plant plant)
	{
		String LogHeader = "[/plant: createPlant] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Plant> plantData = plantRepository.findByScientificName(plant.getScientificName()); 
			
			if (plantData.isPresent()) {
				Log.logger.info(LogHeader + "This plant already exists");
				return new ResponseEntity<>(null, HttpStatus.FOUND);
			} else {
				Plant _plant = plantRepository.save(
					new Plant(
						plant.getCommonName(),
						plant.getScientificName(),
						plant.getFamily(),
						plant.getLifesafeRange()
					)
				);
				
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(_plant, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/plants", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Plant>> getAllPlants() 
	{
		String LogHeader = "[/plants: getAllPlants] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			List<Plant> plants = new ArrayList<Plant>();
			
			plantRepository.findAll().forEach(plants::add);
			
			if (plants.isEmpty()) {
				Log.logger.info(LogHeader + "No plants found");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(plants, HttpStatus.OK);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/plants/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Plant> getPlantById(@PathVariable("id") String id)
	{
		String LogHeader = "[/plants/id: getPlantById] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Plant> plantData = plantRepository.findById(id);
			
			if (plantData.isPresent()) {
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(plantData.get(), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "No plant found with id: " + id);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/plants", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Plant> updatePlant(@RequestBody Plant plant) 
	{
		String LogHeader = "[/plants: updatePlant] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Plant> plantData = plantRepository.findById(plant.getId());
			
			if (plantData.isPresent()) 
			{
				Plant _plant = plantData.get();
				_plant.setId            (plant.getId());
				_plant.setCommonName    (plant.getCommonName());
				_plant.setScientificName(plant.getScientificName());
				_plant.setFamily        (plant.getFamily());
				_plant.setLifesafeRange (plant.getLifesafeRange());
				
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(plantRepository.save(_plant), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "The plant to update has not been found");
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/plants/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deletePlant(@PathVariable("id") String id)
	{
		String LogHeader = "[/plant/name: deletePlant] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Plant> plant = plantRepository.findById(id);
			
			if (plant.isPresent()) {
				Log.logger.info(LogHeader + "The plant \"" + plant.get().getScientificName() + "\" is going to be deleted");
				plantRepository.deleteById(id);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/plants/", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteAllPlants()
	{
		String LogHeader = "[/plants: deleteAllPlants] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			plantRepository.deleteAll();
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
