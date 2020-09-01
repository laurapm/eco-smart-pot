package com.rainforest.eco.models;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.rainforest.eco.models.documents.Action;

@Document(collection="treatment")
public class Treatment 
{
	@Id
	private ObjectId id;
	
	private ObjectId plant;
	private ObjectId device;
	private String type;
	private String title;
	private List<Action> action;
	private Date requestTime;
	private Date actionTime;
	private String comment;
	
	public Treatment( ) { }
	
	public Treatment(String plant, String device, String type, String title, List<Action> action, 
			Date requestTime, Date actionTime, String comment) {
		super();
		this.plant = new ObjectId(plant);
		this.device = new ObjectId(device);
		this.type = type;
		this.title = title;
		this.action = action;
		this.requestTime = requestTime;
		this.actionTime = actionTime;
		this.comment = comment;
	}
	
	public Treatment(String id, String plant, String device, String type, String title, List<Action> action, 
			Date requestTime, Date actionTime, String comment) {
		super();
		this.id = new ObjectId(id);
		this.plant = new ObjectId(plant);
		this.device = new ObjectId(device);
		this.type = type;
		this.title = title;
		this.action = action;
		this.requestTime = requestTime;
		this.actionTime = actionTime;
		this.comment = comment;
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
	
	public String getDevice() {
		return device.toHexString();
	}

	public void setDevice(String device) {
		this.device = new ObjectId(device);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Action> getAction() {
		return action;
	}

	public void setAction(List<Action> action) {
		this.action = action;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
