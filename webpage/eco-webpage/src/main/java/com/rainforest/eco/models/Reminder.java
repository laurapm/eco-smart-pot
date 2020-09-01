package com.rainforest.eco.models;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="reminder")
public class Reminder 
{
	@Id
	private ObjectId id;
	
	private ObjectId device;
	private String title;
	private String message;
	private Date requestTime; 
	private Date remindingTime;
	
	public Reminder( ) { }
	
	public Reminder(String device, String title, String message, Date requestTime, Date remindingTime) {
		super();
		this.device = new ObjectId(device);
		this.title = title;
		this.message = message;
		this.requestTime = requestTime;
		this.remindingTime = remindingTime;
	}

	public Reminder(String id, String device, String title, String message, boolean repeat, double period,
			Date lastReminded, Date finalRepetition) {
		super();
		this.id = new ObjectId(id);
		this.device = new ObjectId(device);
		this.title = title;
		this.message = message;
		this.requestTime = lastReminded;
		this.remindingTime = finalRepetition;
	}

	public String getId() {
		return id.toHexString();
	}

	public void setId(String id) {
		this.id = new ObjectId(id);
	}

	public String getDevice() {
		return device.toHexString();
	}

	public void setDevice(String device) {
		this.device = new ObjectId(device);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Date getRemindingTime() {
		return remindingTime;
	}

	public void setRemindingTime(Date remindingTime) {
		this.remindingTime = remindingTime;
	}
	
}
