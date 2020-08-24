package com.rainforest.eco.requests;

import org.bson.types.ObjectId;

public class NewDeviceRequest 
{
	
	private ObjectId plant;
	private ObjectId owner;
	private String model;
	private String firmwareUpdate;
	
	public NewDeviceRequest( ) { }
	
	public NewDeviceRequest(String plant, String owner, String model, String firmwareUpdate) {
		super();
		this.plant = new ObjectId(plant);
		this.owner = new ObjectId(owner);
		this.model = model;
		this.firmwareUpdate = firmwareUpdate;
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
	
}
