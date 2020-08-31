package com.rainforest.eco.requests;

import java.util.Calendar;
import java.util.Date;

import org.bson.types.ObjectId;

public class DayRequest
{
	private ObjectId device;
	private Date today;

	public DayRequest( ) {	}
	
	public DayRequest(Date today) {
		super();
		this.device = null;
		this.today = today;
	}
	
	public DayRequest(String device, Date today) {
		super();
		this.device = new ObjectId(device);
		this.today = today;
	}
	
	public String getDevice() {
		return device.toHexString();
	}

	public void setDevice(String device) {
		this.device = new ObjectId(device);
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}
	
	@SuppressWarnings("static-access")
	public Date getTomorrow() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.today);
		calendar.add(Calendar.DATE, 1);
		
		return calendar.getTime();
	}
	
	@SuppressWarnings("static-access")
	public Date getYestarday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.today);
		calendar.add(Calendar.DATE, -1);
		
		return calendar.getTime();
	}
	
	public Date getHoursBack(int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.today);
		calendar.add(Calendar.HOUR, -hours);
		
		return calendar.getTime();
	}

}
