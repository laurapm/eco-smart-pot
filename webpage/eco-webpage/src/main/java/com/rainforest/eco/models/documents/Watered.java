package com.rainforest.eco.models.documents;

import java.util.List;

public class Watered 
{
	private boolean watered;
	private List<Integer> value;
	
	public Watered( ) { }
	
	public Watered(boolean watered, List<Integer> value) {
		super();
		this.watered = watered;
		this.value = value;
	}

	public boolean isWatered() {
		return watered;
	}

	public void setWatered(boolean watered) {
		this.watered = watered;
	}

	public List<Integer> getValue() {
		return value;
	}

	public void setValue(List<Integer> value) {
		this.value = value;
	}	
	
}
