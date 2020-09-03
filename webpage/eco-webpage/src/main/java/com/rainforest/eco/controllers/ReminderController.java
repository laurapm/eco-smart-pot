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

import com.rainforest.eco.models.Reminder;
import com.rainforest.eco.repositories.ReminderRepository;
import com.rainforest.eco.requests.DayRequest;
import com.rainforest.eco.services.Log;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api")
public class ReminderController 
{
	@Autowired
	ReminderRepository reminderRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@RequestMapping(value="/reminders", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reminder)
	{
		String LogHeader = "[/reminders: createReminder] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			
			Reminder _product = reminderRepository.save(
				new Reminder(
					reminder.getDevice(),
					reminder.getTitle().replace(' ', '-'),
					reminder.getMessage(),
					reminder.getRequestTime(),
					reminder.getRemindingTime()
				)
			);
				
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(_product, HttpStatus.OK);
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/reminders", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Reminder>> getAllReminders() 
	{
		String LogHeader = "[/reminders: getAllReminders] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			List<Reminder> reminders = new ArrayList<Reminder>();
			
			reminderRepository.findAll().forEach(reminders::add);
			
			if (reminders.isEmpty()) {
				Log.logger.info(LogHeader + "No reminders found");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(reminders, HttpStatus.OK);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/reminders/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Reminder> getReminderById(@PathVariable("id") String id)
	{
		String LogHeader = "[/reminders/id: getReminderById] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Reminder> productData = reminderRepository.findById(id);
			
			if (productData.isPresent()) {
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(productData.get(), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "No reminder found with id: " + id);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/reminders/titled/{title}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Reminder>> getReminderByTitle(@PathVariable("title") String title)
	{
		String LogHeader = "[/reminders/titled/id: getReminderByTitle] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			List<Reminder> reminderData = reminderRepository.findByTitle(title);
			
			if (!reminderData.isEmpty()) {
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(reminderData, HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "No reminder found with title: " + title);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/reminders/programmed", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<Reminder>> getReminderProgrammedNextDay(@RequestBody DayRequest today)
	{
		String LogHeader = "[/reminders/programmed: getReminderProgrammedNextDay] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			
			Query query = new Query();
			query.addCriteria(Criteria.where("device").is(new ObjectId(today.getDevice())));
			query.addCriteria(Criteria.where("remindingTime").gte(today.getHoursBack(1)).lte(today.getTomorrow()));
			List<Reminder> reminders = mongoTemplate.find(query, Reminder.class);
			
			if (!reminders.isEmpty()) {
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(reminders, HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "No reminders found for date: " + today.getToday());
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/reminders", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Reminder> updateReminder(@RequestBody Reminder reminder) 
	{
		String LogHeader = "[/reminders: updateReminder] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Reminder> reminderData = reminderRepository.findById(reminder.getId());
			
			if (reminderData.isPresent()) 
			{
				Reminder _reminder = reminderData.get();
				_reminder.setId           (reminder.getId());
				_reminder.setDevice       (reminder.getDevice());
				_reminder.setTitle        (reminder.getTitle());
				_reminder.setMessage      (reminder.getMessage());
				_reminder.setRequestTime  (reminder.getRequestTime());
				_reminder.setRemindingTime(reminder.getRemindingTime());
				
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(reminderRepository.save(_reminder), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "The reminder to update has not been found");
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/reminders/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteReminder(@PathVariable("id") String id)
	{
		String LogHeader = "[/reminders/id: deleteReminder] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Reminder> reminder = reminderRepository.findById(id);
			
			if (reminder.isPresent()) {
				Log.logger.info(LogHeader + "The reminder \"" + reminder.get().getTitle() + "\" is going to be deleted");
				reminderRepository.deleteById(id);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/reminders", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteAllReminders()
	{
		String LogHeader = "[/reminders: deleteAllReminders] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			reminderRepository.deleteAll();
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
