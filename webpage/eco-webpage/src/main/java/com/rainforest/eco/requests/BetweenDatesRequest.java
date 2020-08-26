package com.rainforest.eco.requests;

import java.util.Date;

import org.bson.types.ObjectId;

public class BetweenDatesRequest 
{
	private ObjectId device;
	private Date minDate;
	private Date maxDate;

	public BetweenDatesRequest( ) {	}
	
	public BetweenDatesRequest(Date minDate, Date maxDate, String device) {
		super();
		this.device = new ObjectId(device);
		this.minDate = minDate;
		this.maxDate = maxDate;
	}
	
	public String getDevice() {
		return device.toHexString();
	}

	public void setDevice(String device) {
		this.device = new ObjectId(device);
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

}
