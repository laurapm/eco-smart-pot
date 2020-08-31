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
	private String firmwareUpdate;
	private Date registryDate;
	
	public Device( ) { }
	
	public Device(String plant, String owner, String model, String firmwareUpdate,
			Date registryDate) {
		super();
		this.plant = new ObjectId(plant);
		this.owner = new ObjectId(owner);
		this.model = model;
		this.firmwareUpdate = firmwareUpdate;
		this.registryDate = registryDate;
	}
	
	public Device(String id, String plant, String owner, String model, String firmwareUpdate,
			Date registryDate) {
		super();
		this.id = new ObjectId(id);
		this.plant = new ObjectId(plant);
		this.owner = new ObjectId(owner);
		this.model = model;
		this.firmwareUpdate = firmwareUpdate;
		this.registryDate = registryDate;
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

	public String getFirmwareUpdate() {
		return firmwareUpdate;
	}

	public void setFirmwareUpdate(String firmwareUpdate) {
		this.firmwareUpdate = firmwareUpdate;
	}

	public Date getRegistryDate() {
		return registryDate;
	}

	public void setRegistryDate(Date registryDate) {
		this.registryDate = registryDate;
	}	
	
}
