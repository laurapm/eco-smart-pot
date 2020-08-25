package com.rainforest.eco.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
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

import com.rainforest.eco.models.Ticket;
import com.rainforest.eco.repositories.TicketRepository;
import com.rainforest.eco.services.Log;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api")
public class TicketController 
{
	@Autowired
	TicketRepository ticketRepository;
	
	@RequestMapping(value="/tickets", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket)
	{
		String LogHeader = "[/tickets: createTicket] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			long currentTime = System.currentTimeMillis();
			
			Ticket _device = ticketRepository.save(
				new Ticket(
					ticket.getOwner(),
					ticket.getItem(),
					new Date(currentTime)
				)
			);
				
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(_device, HttpStatus.OK);
			
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/tickets", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Ticket>> getAllTickets() 
	{
		String LogHeader = "[/tickets: getAllTickets] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			List<Ticket> tickets = new ArrayList<Ticket>();
			
			ticketRepository.findAll().forEach(tickets::add);
			
			if (tickets.isEmpty()) {
				Log.logger.info(LogHeader + "No tickets found");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(tickets, HttpStatus.OK);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/tickets/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Ticket> getTicketById(@PathVariable("id") String id)
	{
		String LogHeader = "[/tickets/id: getTicketById] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Ticket> ticketData = ticketRepository.findById(id);
			
			if (ticketData.isPresent()) {
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(ticketData.get(), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "No ticket found with id: " + id);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/tickets/owned/{owner}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Ticket>> getTicketByOwner(@PathVariable("owner") String owner)
	{
		String LogHeader = "[/tickets/owned/id: getTicketByOwner] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			List<Ticket> tickets = ticketRepository.findByOwner(new ObjectId(owner));
			
			if (!tickets.isEmpty()) {
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(tickets, HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "No ticket found for owner: " + owner);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/tickets", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket) 
	{
		String LogHeader = "[/tickets: updateTicket] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Ticket> ticketData = ticketRepository.findById(ticket.getId());
			
			if (ticketData.isPresent()) 
			{
				Ticket _ticket = ticketData.get();
				_ticket.setId   (ticket.getId());
				_ticket.setOwner(ticket.getOwner());
				_ticket.setItem (ticket.getItem());
				_ticket.setDate (ticket.getDate());
				
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(ticketRepository.save(_ticket), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "The ticket to update has not been found");
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/tickets/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteTicket(@PathVariable("id") String id)
	{
		String LogHeader = "[/tickets/id: deleteTicket] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Ticket> device = ticketRepository.findById(id);
			
			if (device.isPresent()) {
				Log.logger.info(LogHeader + "The ticket \"" + device.get().getId() + "\" is going to be deleted");
				ticketRepository.deleteById(id);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/tickets/", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteAllTickets()
	{
		String LogHeader = "[/tickets: deleteAllTickets] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			ticketRepository.deleteAll();
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
