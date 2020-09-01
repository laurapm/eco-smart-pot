package com.rainforest.eco.models.documents;

public class MinMax 
{
	private String type;
	private double min;
	private double max;
	
	public MinMax( ) { }
	
	public MinMax(String type, double min, double max) {
		super();
		this.type = type;
		this.min = min;
		this.max = max;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}
	
}
