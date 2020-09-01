package com.rainforest.eco.models.documents;

public class MeasureVariant 
{
	private int minute;
	private Watered measure;
	
	public MeasureVariant( ) { }
	
	public MeasureVariant(int minute, Watered measure) {
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

	public Watered getMeasure() {
		return measure;
	}

	public void setMeasure(Watered measure) {
		this.measure = measure;
	}
	
}
