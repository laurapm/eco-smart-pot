package com.rainforest.eco.models;

import java.util.ArrayList;
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
	
	public MeasureVariant getLastHumidityInt() {
		int lastIndex = this.humidityInt.size() - 1;
		return this.humidityInt.get(lastIndex);
	}
	
	public MeasureDecimal getLastHumidityExt() {
		int lastIndex = this.humidityExt.size() - 1;
		return this.humidityExt.get(lastIndex);
	}
	
	public MeasureInteger getLastLuminosityExt() {
		int lastIndex = this.luminosityExt.size() - 1;
		return this.luminosityExt.get(lastIndex);
	}
	
	public MeasureDecimal getLastTemperatureExt() {
		int lastIndex = this.temperatureExt.size() - 1;
		return this.temperatureExt.get(lastIndex);
	}
	
	public List<Double> getLastNTemperatureExt(int n) {
		// Store temperature
		List<Double> temperatures = new ArrayList<Double>();
		
		int size = this.temperatureExt.size();
		// Enough elements stored
		if (size >= n) {
			for (int i = 0; i < n; i++) {
				temperatures.add(this.temperatureExt.get(size - 1 - i).getMeasure());
			}
			
			return temperatures;
		} else {
			return null;
		}
	}
	
	public List<Integer> getLastNHumidityInt(int n) {
		// Store temperature
		List<Integer> humidities = new ArrayList<Integer>();
		
		int size = this.humidityInt.size();
		// Enough elements stored
		if (size >= n) {
			for (int i = 0; i < n; i++) {
				humidities.add(this.humidityInt.get(size - 1 - i).getMeasure().getLastValue());
			}
			
			return humidities;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "Measurements [id=" + id + ", plant=" + plant + ", device=" + device + ", date=" + date + ", hour="
				+ hour + ", humidityInt=" + humidityInt + ", humidityExt=" + humidityExt + ", luminosityExt="
				+ luminosityExt + ", temperatureExt=" + temperatureExt + "]";
	}
	
}
