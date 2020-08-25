package com.rainforest.eco.models;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.rainforest.eco.models.documents.MeasureDecimal;
import com.rainforest.eco.models.documents.MeasureInteger;
import com.rainforest.eco.models.documents.MeasureVariant;

@Document(collection="measurements")
public class Measurements 
{
	@Id
	private ObjectId id;
	
	private ObjectId plant;
	private ObjectId device;
	private Date date;
	private int hour;
	private List<MeasureVariant> humidityInt;
	private List<MeasureDecimal> humidityExt;
	private List<MeasureInteger> luminosityExt;
	private List<MeasureDecimal> temperatureExt;
	
	public Measurements( ) { }
	
	public Measurements(String plant, String device, Date date, int hour, List<MeasureVariant> humidityInt,
			List<MeasureDecimal> humidityExt, List<MeasureInteger> luminosityExt, List<MeasureDecimal> temperatureExt) {
		super();
		this.plant = new ObjectId(plant);
		this.device = new ObjectId(device);
		this.date = date;
		this.hour = hour;
		this.humidityInt = humidityInt;
		this.humidityExt = humidityExt;
		this.luminosityExt = luminosityExt;
		this.temperatureExt = temperatureExt;
	}
	
	public Measurements(String id, String plant, String device, Date date, int hour, List<MeasureVariant> humidityInt,
			List<MeasureDecimal> humidityExt, List<MeasureInteger> luminosityExt, List<MeasureDecimal> temperatureExt) {
		super();
		this.id = new ObjectId(id);
		this.plant = new ObjectId(plant);
		this.device = new ObjectId(device);
		this.date = date;
		this.hour = hour;
		this.humidityInt = humidityInt;
		this.humidityExt = humidityExt;
		this.luminosityExt = luminosityExt;
		this.temperatureExt = temperatureExt;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public List<MeasureVariant> getHumidityInt() {
		return humidityInt;
	}

	public void setHumidityInt(List<MeasureVariant> humidityInt) {
		this.humidityInt = humidityInt;
	}

	public List<MeasureDecimal> getHumidityExt() {
		return humidityExt;
	}

	public void setHumidityExt(List<MeasureDecimal> humidityExt) {
		this.humidityExt = humidityExt;
	}

	public List<MeasureInteger> getLuminosityExt() {
		return luminosityExt;
	}

	public void setLuminosityExt(List<MeasureInteger> luminosityExt) {
		this.luminosityExt = luminosityExt;
	}

	public List<MeasureDecimal> getTemperatureExt() {
		return temperatureExt;
	}

	public void setTemperatureExt(List<MeasureDecimal> temperatureExt) {
		this.temperatureExt = temperatureExt;
	}
	
}
