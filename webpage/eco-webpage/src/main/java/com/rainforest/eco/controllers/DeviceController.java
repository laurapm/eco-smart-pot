package com.rainforest.eco.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.BsonTimestamp;
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

import com.rainforest.eco.models.Device;
import com.rainforest.eco.repositories.DeviceRepository;
import com.rainforest.eco.requests.NewDeviceRequest;
import com.rainforest.eco.services.Log;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api")
public class DeviceController 
{
	@Autowired
	DeviceRepository deviceRepository;
	
	@RequestMapping(value="/devices", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Device> createDevice(@RequestBody NewDeviceRequest device)
	{
		String LogHeader = "[/devices: createDevice] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			int currentTime = (int) (System.currentTimeMillis() / 1000L);
			
			Device _device = deviceRepository.save(
				new Device(
					device.getPlant(),
					device.getOwner(),
					device.getModel(),
					device.getFirmwareUpdate(),
					new BsonTimestamp(currentTime, 0)
				)
			);
				
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(_device, HttpStatus.OK);
			
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/devices", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Device>> getAllDevices() 
	{
		String LogHeader = "[/devices: getAllDevices] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			List<Device> devices = new ArrayList<Device>();
			
			deviceRepository.findAll().forEach(devices::add);
			
			if (devices.isEmpty()) {
				Log.logger.info(LogHeader + "No devices found");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(devices, HttpStatus.OK);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/devices/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Device> getDeviceById(@PathVariable("id") String id)
	{
		String LogHeader = "[/devices/id: getDeviceById] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Device> deviceData = deviceRepository.findById(id);
			
			if (deviceData.isPresent()) {
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(deviceData.get(), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "No device found with id: " + id);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/devices/owned/{owner}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Device>> getDeviceByOwner(@PathVariable("owner") String owner)
	{
		String LogHeader = "[/devices/owned/id: getDeviceByOwner] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			List<Device> devices = deviceRepository.findByOwner(new ObjectId(owner));
			
			if (!devices.isEmpty()) {
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(devices, HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "No device found for owner: " + owner);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/devices", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Device> updateDevice(@RequestBody Device device) 
	{
		String LogHeader = "[/devices: updateDevice] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Device> deviceData = deviceRepository.findById(device.getId());
			
			if (deviceData.isPresent()) 
			{
				Device _device = deviceData.get();
				_device.setId            (device.getId());
				_device.setPlant         (device.getPlant());
				_device.setOwner         (device.getOwner());
				_device.setModel         (device.getModel());
				_device.setFirmwareUpdate(device.getFirmwareUpdate());
				_device.setRegistryDate  (device.getRegistryDate());
				
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(deviceRepository.save(_device), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "The device to update has not been found");
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/devices/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteDevice(@PathVariable("id") String id)
	{
		String LogHeader = "[/devices/id: deleteDevice] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Device> device = deviceRepository.findById(id);
			
			if (device.isPresent()) {
				Log.logger.info(LogHeader + "The device \"" + device.get().getModel() + "\" is going to be deleted");
				deviceRepository.deleteById(id);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/devices/", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteAllDevices()
	{
		String LogHeader = "[/devices: deleteAllDevices] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			deviceRepository.deleteAll();
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred" + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
