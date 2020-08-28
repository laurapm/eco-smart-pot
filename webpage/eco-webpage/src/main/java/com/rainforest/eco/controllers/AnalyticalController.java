package com.rainforest.eco.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rainforest.eco.models.Measurements;
import com.rainforest.eco.models.Plant;
import com.rainforest.eco.models.Treatment;
import com.rainforest.eco.models.documents.Action;
import com.rainforest.eco.models.documents.KeyValue;
import com.rainforest.eco.models.documents.MinMax;
import com.rainforest.eco.repositories.MeasurementsRepository;
import com.rainforest.eco.repositories.PlantRepository;
import com.rainforest.eco.repositories.TreatmentRepository;
import com.rainforest.eco.requests.DayRequest;
import com.rainforest.eco.services.Log;


@Controller
@EnableAutoConfiguration
@RequestMapping("/api")
public class AnalyticalController 
{
	@Autowired
	MeasurementsRepository measurementsRepository;
	@Autowired
	PlantRepository PlantRepository;
	@Autowired
	TreatmentRepository treatmentRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@RequestMapping(value="/analyze/measurements", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Boolean> analyzeData() 
	{
		String LogHeader = "[/analyze/measurements: analyzeData] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			
			DayRequest day = new DayRequest(new Date(System.currentTimeMillis()));
			
			// Query to return measurements from the last 24h
			List<Measurements> measurements  = getMeasurementsToAnalyze(day.getYestarday(), day.getHoursBack(2));
			Map<String, List<MinMax>> plants = getMappedPlantRanges();
			
			// Interpret measurements
			Measurements last = null;
			boolean done      = false;
			boolean needsNext = false;
			for (Measurements m : measurements) {
				if (done && last != null) {
					done = last.getDevice().equals(m.getDevice());
				}
				
				if (!done) {
					if (!needsNext) {
						List<Double> temperatures = m.getLastNTemperatureExt(1);
						
						if (temperatures != null) {
							MinMax temperatureRange = getRange(plants.get(m.getPlant()), "temperature");
							// Freeze
							if (temperatures.get(0) < temperatureRange.getMin()) {
								Log.logger.info("Prescribing Treatment: avoid freezing plant: " + m.getPlant() + "(monitored by " + m.getDevice() + ")");
								// Treatment parameters
								String plant        = m.getPlant();
								String device       = m.getDevice();
								String type         = "scheduled";
								String title        = "warmup";
								List<Action> action = Arrays.asList(new Action("move-hotter", null));
								Date requestTime    = new Date(System.currentTimeMillis());
								Date actionTime     = incrementTime(requestTime, "minute", 30); 
								String comment      = "The temperature is low for the plant. Move it to a place with more temperature.";
								// Create new treatment
								Treatment treatment = new Treatment(plant, device, type, title, action, requestTime, actionTime, comment); 
								// Save treatment
								treatmentRepository.save(treatment);
								Log.logger.info("Treatment Prescribed");
							// Burn
							} else if (temperatures.get(0) > temperatureRange.getMax()) {
								Log.logger.info("Prescribing Treatment: avoid burning plant: " + m.getPlant() + "(monitored by " + m.getDevice() + ")");
								// Treatment parameters
								String plant        = m.getPlant();
								String device       = m.getDevice();
								String type         = "scheduled";
								String title        = "cooldown";
								List<Action> action = Arrays.asList(new Action("move-colder", null));
								Date requestTime    = new Date(System.currentTimeMillis());
								Date actionTime     = incrementTime(requestTime, "minute", 30);
								String comment      = "The temperature is high for the plant. Move it to a place with less temperature.";
								// Create new treatment
								Treatment treatment = new Treatment(plant, device, type, title, action, requestTime, actionTime, comment); 
								// Save treatment
								treatmentRepository.save(treatment);
								Log.logger.info("Treatment Prescribed");
							}
						} else {
							Log.logger.info("Needs next");
							needsNext = true;
						}
						
						List<Integer> humidityInt = m.getLastNHumidityInt(1);
						
						if (humidityInt != null) {
							MinMax humidityIntRange = getRange(plants.get(m.getPlant()), "humidity");
							
							if (humidityInt.get(0) < humidityIntRange.getMin()) {
								Log.logger.info("Prescribing Treatment: water plant: " + m.getPlant() + "(monitored by " + m.getDevice() + ")");
								// Treatment parameters
								String plant        = m.getPlant();
								String device       = m.getDevice();
								String type         = "scheduled";
								String title        = "irrigation";
								List<Action> action = Arrays.asList(
									new Action("watering", Arrays.asList(
										new KeyValue("time", "5 s"), 
										new KeyValue("flow", "max"))));
								Date requestTime    = new Date(System.currentTimeMillis());
								Date actionTime     = incrementTime(requestTime, "minute", 10);
								String comment      = "Water the plant due to low pot humidity.";
								// Create new treatment
								Treatment treatment = new Treatment(plant, device, type, title, action, requestTime, actionTime, comment); 
								// Save treatment
								treatmentRepository.save(treatment);
								Log.logger.info("Treatment Prescribed");
							}
						} else {
							Log.logger.info("Needs next");
							needsNext = true;
						}
					// Needs measures from different hours
					} else {
						
					}
				}
				// Gets next measure
				last = m;
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(true, HttpStatus.OK);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private List<Measurements> getMeasurementsToAnalyze(Date min, Date max) {
		List<Measurements> measurements = new ArrayList<Measurements>();
		
		Query query = new Query();
		query.addCriteria(Criteria.where("date").gte(min).lte(max));//.gte(1598464800000L)
		Sort deviceSort = Sort.by(Sort.Direction.DESC, "device");
		Sort dateSort   = Sort.by(Sort.Direction.DESC, "date");
		Sort hourSort   = Sort.by(Sort.Direction.DESC, "hour");
		Sort plantSort  = Sort.by(Sort.Direction.DESC, "plant");
		Sort groupBySourt = deviceSort.and(dateSort).and(hourSort).and(plantSort);
		query.with(groupBySourt);
		
		// Return measurements
		mongoTemplate.find(query, Measurements.class).forEach(measurements::add);
		
		return measurements;
	}
	
	private Map<String, List<MinMax>> getMappedPlantRanges() {
		
		List<Plant> plantsList = new ArrayList<Plant>();
		// Get all plants
		PlantRepository.findAll().forEach(plantsList::add);
		// Map Ranges
		Map<String, List<MinMax>> plants = plantsList.stream().collect(Collectors.toMap(Plant::getId, Plant::getLifesafeRange));
		
		return plants;
	}
	
	private MinMax getRange(List<MinMax> lifesafeRanges, String type) {
		MinMax desired = null;
		
		for (MinMax range : lifesafeRanges) {
			if (range.getType().equals(type)) {
				desired = range;
				break;
			}
		}
		
		return desired;
	}
	
	private Date incrementTime(Date toIncrement, String type, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(toIncrement);
		
		if (type.equals("day")) {
			cal.add(Calendar.DATE, amount);
		} else if (type.equals("hour")) {
			cal.add(Calendar.HOUR, amount);
		} else if (type.equals("minutes")) {
			cal.add(Calendar.MINUTE, amount);
		}
		
		return cal.getTime();
	}
}
