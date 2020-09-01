package com.rainforest.eco.models.documents;

public class MeasureDecimal 
{
	private int minute;
	private double measure;
	
	public MeasureDecimal( ) { }
	
	public MeasureDecimal(int minute, double measure) {
		super();
		this.minute = minute;
		this.measure = measure;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public double getMeasure() {
		return measure;
	}

	public void setMeasure(double measure) {
		this.measure = measure;
	}
	
}
