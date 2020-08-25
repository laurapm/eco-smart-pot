package com.rainforest.eco.models.documents;

public class MeasureInteger 
{
	private int minute;
	private int measure;
	
	public MeasureInteger( ) { }
	
	public MeasureInteger(int minute, int measure) {
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

	public int getMeasure() {
		return measure;
	}

	public void setMeasure(int measure) {
		this.measure = measure;
	}
}
