package com.rainforest.eco.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rainforest.eco.models.Treatment;
import com.rainforest.eco.repositories.TreatmentRepository;
import com.rainforest.eco.requests.DayRequest;
import com.rainforest.eco.services.Log;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api")
public class TreatmentController 
{
	@Autowired
	TreatmentRepository treatmentRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	/**
	 * Creates a new treatment.
	 * 
	 * @param treatment Treatment
	 * - Plant (string)
	 * - Device (string)
	 * - Type (string)
	 * - Title (string)
	 * - Action:
	 *     - Perform (string)
	 *     - Params: List of
	 *         - k (string)
	 *         - v (string)
	 * - requestTime (date)
	 * - actionTime (date)
	 * 
	 * @return Status Response and Treatment
	 */
	@RequestMapping(value="/treatments", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Treatment> createTreatment(@RequestBody Treatment treatment)
	{
		String LogHeader = "[/treatments: createTreatment] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			
			// Create new Treatment
			Treatment _treatment = treatmentRepository.save(
				new Treatment(
					treatment.getPlant(),
					treatment.getDevice(),
					treatment.getType(),
					treatment.getTitle(),
					treatment.getAction(),
					treatment.getRequestTime(),
					treatment.getActionTime(),
					treatment.getComment()
				)
			);
				
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(_treatment, HttpStatus.OK);
			
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Return all treatments. 
	 * 
	 * @return Status Response and List ofTreatment
	 * - Plant (string)
	 * - Device (string)
	 * - Type (string)
	 * - Title (string)
	 * - Action:
	 *     - Perform (string)
	 *     - Params: List of
	 *         - k (string)
	 *         - v (string)
	 * - requestTime (date)
	 * - actionTime (date)
	 */
	@RequestMapping(value="/treatments", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Treatment>> getAllTreatments() 
	{
		String LogHeader = "[/treatments: getAllTreatments] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			List<Treatment> treatments = new ArrayList<Treatment>();
			
			// Return all treatments
			treatmentRepository.findAll().forEach(treatments::add);
			
			if (treatments.isEmpty()) {
				Log.logger.info(LogHeader + "No treatments found");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(treatments, HttpStatus.OK);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Get treatment by id.
	 * 
	 * @param id Treatment id
	 * 
	 * @return List of Treatments
	 * - Plant (string)
	 * - Device (string)
	 * - Type (string)
	 * - Title (string)
	 * - Action:
	 *     - Perform (string)
	 *     - Params: List of
	 *         - k (string)
	 *         - v (string)
	 * - requestTime (date)
	 * - actionTime (date)
	 */
	@RequestMapping(value="/treatments/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Treatment> getTreatmentById(@PathVariable("id") String id)
	{
		String LogHeader = "[/treatments/id: getTreatmentById] ";
		
		try {
			// Finds treatment by id
			Log.logger.info(LogHeader + "Requested");
			Optional<Treatment> treatmentData = treatmentRepository.findById(id);
			
			// Treatment present
			if (treatmentData.isPresent()) {
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(treatmentData.get(), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "No treatment found with id: " + id);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Return treatments by device.
	 * 
	 * @param device Device id
	 * 
	 * @return Status Response and Treatment
	 * - Plant (string)
	 * - Device (string)
	 * - Type (string)
	 * - Title (string)
	 * - Action:
	 *     - Perform (string)
	 *     - Params: List of
	 *         - k (string)
	 *         - v (string)
	 * - requestTime (date)
	 * - actionTime (date)
	 */
	@RequestMapping(value="/treatments/device/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Treatment>> getAllTreatmentsByDevice(@PathVariable("id") String device)
	{
		String LogHeader = "[/treatments/device/id: getAllTreatmentsByDevice] ";
		
		try {
			// Get by device
			Log.logger.info(LogHeader + "Requested");
			List<Treatment> treatments = treatmentRepository.findByDevice(new ObjectId(device));
			
			// Is present
			if (!treatments.isEmpty()) {
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(treatments, HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "No treatments found with for device: " + device);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Treatments programmed for for next 24h.
	 * 
	 * @param today DayRequest
	 * - device (string)
	 * - today (date)
	 * 
	 * @return Status response and List of Treatments
	 * - Plant (string)
	 * - Device (string)
	 * - Type (string)
	 * - Title (string)
	 * - Action:
	 *     - Perform (string)
	 *     - Params: List of
	 *         - k (string)
	 *         - v (string)
	 * - requestTime (date)
	 * - actionTime (date)
	 */
	@RequestMapping(value="/treatments/programmed", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<Treatment>> getTreatmentsProgrammedNextDay(@RequestBody DayRequest today)
	{
		String LogHeader = "[/treatments/programmed: getTreatmentsProgrammedNextDay] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			
			// WHERE DEVICE = device
			//       DATE BETWEEN today AND tomorrow
			Query query = new Query();
			query.addCriteria(Criteria.where("device").is(new ObjectId(today.getDevice())));
			query.addCriteria(Criteria.where("actionTime").gte(today.getHoursBack(1)).lte(today.getTomorrow()));
			List<Treatment> treatments = mongoTemplate.find(query, Treatment.class);
			
			// Has values
			if (!treatments.isEmpty()) {
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(treatments, HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "No treatments found for date: " + today.getToday());
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Update a treatment.
	 * 
	 * @param treatment Treatment
	 * - Plant (string)
	 * - Device (string)
	 * - Type (string)
	 * - Title (string)
	 * - Action:
	 *     - Perform (string)
	 *     - Params: List of
	 *         - k (string)
	 *         - v (string)
	 * - requestTime (date)
	 * - actionTime (date) 
	 * 
	 * @return The treatment updated
	 */
	@RequestMapping(value="/treatments", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Treatment> updateTreatment(@RequestBody Treatment treatment) 
	{
		String LogHeader = "[/treatments: updateTreatment] ";
		
		try {
			// Find treatment to update
			Log.logger.info(LogHeader + "Requested");
			Optional<Treatment> productData = treatmentRepository.findById(treatment.getId());
			
			if (productData.isPresent()) 
			{
				// Update values
				Treatment _treatment = productData.get();
				_treatment.setId         (treatment.getId());
				_treatment.setPlant      (treatment.getPlant());
				_treatment.setDevice     (treatment.getDevice());
				_treatment.setType       (treatment.getType());
				_treatment.setAction     (treatment.getAction());
				_treatment.setRequestTime(treatment.getRequestTime());
				_treatment.setActionTime (treatment.getActionTime());
				_treatment.setComment    (treatment.getComment());
				
				// Write changes
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(treatmentRepository.save(_treatment), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "The treatment to update has not been found");
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Delete treatment.
	 * 
	 * @param id Treatment id
	 * 
	 * @return Response status
	 */
	@RequestMapping(value="/treatments/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteTreatment(@PathVariable("id") String id)
	{
		String LogHeader = "[/treatments/id: deleteTreatment] ";
		
		try {
			// Find treatment to delete
			Log.logger.info(LogHeader + "Requested");
			Optional<Treatment> product = treatmentRepository.findById(id);
			
			// Deletes if exists
			if (product.isPresent()) {
				Log.logger.info(LogHeader + "The treatment \"" + product.get().getTitle() + "\" is going to be deleted");
				treatmentRepository.deleteById(id);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Delete all treatments.
	 * 
	 * @return Response status
	 */
	@RequestMapping(value="/treatments/", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteAllTreatments()
	{
		String LogHeader = "[/treatments: deleteAllTreatments] ";
		
		try {
			// Delete all treatments
			Log.logger.info(LogHeader + "Requested");
			treatmentRepository.deleteAll();
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
