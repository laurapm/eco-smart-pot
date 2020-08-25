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
	private boolean repeat;
	private double period;
	private Date lastReminded; 
	private Date finalRepetition;
	
	public Reminder( ) { }
	
	public Reminder(String device, String title, String message, boolean repeat, double period,
			Date lastReminded, Date finalRepetition) {
		super();
		this.device = new ObjectId(device);
		this.title = title;
		this.message = message;
		this.repeat = repeat;
		this.period = period;
		this.lastReminded = lastReminded;
		this.finalRepetition = finalRepetition;
	}

	public Reminder(String id, String device, String title, String message, boolean repeat, double period,
			Date lastReminded, Date finalRepetition) {
		super();
		this.id = new ObjectId(id);
		this.device = new ObjectId(device);
		this.title = title;
		this.message = message;
		this.repeat = repeat;
		this.period = period;
		this.lastReminded = lastReminded;
		this.finalRepetition = finalRepetition;
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

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public double getPeriod() {
		return period;
	}

	public void setPeriod(double period) {
		this.period = period;
	}

	public Date getLastReminded() {
		return lastReminded;
	}

	public void setLastReminded(Date lastReminded) {
		this.lastReminded = lastReminded;
	}

	public Date getFinalRepetition() {
		return finalRepetition;
	}

	public void setFinalRepetition(Date finalRepetition) {
		this.finalRepetition = finalRepetition;
	}	
	
}
