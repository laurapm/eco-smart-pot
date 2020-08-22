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
	private Date last_reminded; 
	private Date final_repetition;
	
	public Reminder( ) { }

	public Reminder(ObjectId id, ObjectId device, String title, String message, boolean repeat, double period,
			Date last_reminded, Date final_repetition) {
		super();
		this.id = id;
		this.device = device;
		this.title = title;
		this.message = message;
		this.repeat = repeat;
		this.period = period;
		this.last_reminded = last_reminded;
		this.final_repetition = final_repetition;
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

	public Date getLast_reminded() {
		return last_reminded;
	}

	public void setLast_reminded(Date last_reminded) {
		this.last_reminded = last_reminded;
	}

	public Date getFinal_repetition() {
		return final_repetition;
	}

	public void setFinal_repetition(Date final_repetition) {
		this.final_repetition = final_repetition;
	}	
	
}
