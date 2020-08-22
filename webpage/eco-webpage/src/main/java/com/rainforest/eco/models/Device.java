package com.rainforest.eco.models;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="device")
public class Device 
{
	@Id
	private ObjectId id;
	
	private ObjectId plant;
	private ObjectId owner;
	private String model;
	private String firmware_update;
	private Date registry_date;
	
	public Device( ) { }
	
	public Device(ObjectId id, ObjectId plant, ObjectId owner, String model, String firmware_update,
			Date registry_date) {
		super();
		this.id = id;
		this.plant = plant;
		this.owner = owner;
		this.model = model;
		this.firmware_update = firmware_update;
		this.registry_date = registry_date;
	}

	public String getId() {
		return id.toHexString();
	}

	public void setId(String id) {
		this.id = new ObjectId(id);
	}

	public String getPlant() {
		return plant.toHexString();
	}

	public void setPlant(String plant) {
		this.plant = new ObjectId(plant);
	}

	public String getOwner() {
		return owner.toHexString();
	}

	public void setOwner(String owner) {
		this.owner = new ObjectId(owner);
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFirmware_update() {
		return firmware_update;
	}

	public void setFirmware_update(String firmware_update) {
		this.firmware_update = firmware_update;
	}

	public Date getRegistry_date() {
		return registry_date;
	}

	public void setRegistry_date(Date registry_date) {
		this.registry_date = registry_date;
	}	
	
}
