package com.rainforest.eco.models.documents;

import java.util.List;

public class Action 
{
	private String perform;
	private List<KeyValue> params;
	
	public Action( ) { }
	
	public Action(String perform, List<KeyValue> params) {
		super();
		this.perform = perform;
		this.params = params;
	}

	public String getPerform() {
		return perform;
	}
	
	public void setPerform(String perform) {
		this.perform = perform;
	}
	
	public List<KeyValue> getParams() {
		return params;
	}
	
	public void setParams(List<KeyValue> params) {
		this.params = params;
	}
	
}
