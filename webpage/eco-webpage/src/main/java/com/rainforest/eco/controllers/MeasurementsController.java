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
import com.rainforest.eco.models.Measurements;
import com.rainforest.eco.repositories.MeasurementsRepository;
import com.rainforest.eco.services.Log;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api")
public class MeasurementsController 
{
	@Autowired
	MeasurementsRepository measurementsRepository;
	
	@RequestMapping(value="/measurements", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Measurements> createMeasurements(@RequestBody Measurements measurements)
	{
		String LogHeader = "[/measurements: createMeasurements] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
				
			Measurements _measurements = measurementsRepository.save(
				new Measurements(
					measurements.getPlant(),
					measurements.getDevice(),
					measurements.getDate(),
					measurements.getHour(),
					measurements.getHumidityInt(),
					measurements.getHumidityExt(),
					measurements.getLuminosityExt(),
					measurements.getTemperatureExt()
				)
			);
				
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(_measurements, HttpStatus.OK);
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/measurements", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Measurements>> getAllMeasurements() 
	{
		String LogHeader = "[/measurements: getAllMeasurements] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			List<Measurements> measurements = new ArrayList<Measurements>();
			
			measurementsRepository.findAll().forEach(measurements::add);
			
			if (measurements.isEmpty()) {
				Log.logger.info(LogHeader + "No measurements found");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(measurements, HttpStatus.OK);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/measurements/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Measurements> getMeasurementsById(@PathVariable("id") String id)
	{
		String LogHeader = "[/measurements/id: getMeasurementsById] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Measurements> measurementsData = measurementsRepository.findById(id);
			
			if (measurementsData.isPresent()) {
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(measurementsData.get(), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "No measurements found with id: " + id);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/measurements", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Measurements> updateMeasurements(@RequestBody Measurements measurements) 
	{
		String LogHeader = "[/measurements: updateMeasurements] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Measurements> measurementsData = measurementsRepository.findById(measurements.getId());
			
			if (measurementsData.isPresent()) 
			{
				Measurements _measurements = measurementsData.get();
				_measurements.setId            (measurements.getId());
				_measurements.setPlant         (measurements.getPlant());
				_measurements.setDevice        (measurements.getDevice());
				_measurements.setDate          (measurements.getDate());
				_measurements.setHour          (measurements.getHour());
				_measurements.setHumidityInt   (measurements.getHumidityInt());
				_measurements.setHumidityExt   (measurements.getHumidityExt());
				_measurements.setLuminosityExt (measurements.getLuminosityExt());
				_measurements.setTemperatureExt(measurements.getTemperatureExt());
				
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(measurementsRepository.save(_measurements), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "The measurements to update has not been found");
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/measurements/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteMeasurements(@PathVariable("id") String id)
	{
		String LogHeader = "[/measurements/id: deleteMeasurements] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Measurements> product = measurementsRepository.findById(id);
			
			if (product.isPresent()) {
				Log.logger.info(LogHeader + "The measurements \"" + product.get().getId() + "\" are going to be deleted");
				measurementsRepository.deleteById(id);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteAllMeasurements()
	{
		String LogHeader = "[/measurements: deleteAllMeasurements] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			measurementsRepository.deleteAll();
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
