package com.rainforest.eco.models.documents;

public class KeyValue 
{
	private String k;
	private String v;
	
	public KeyValue( ) { }
	
	public KeyValue(String key, String value) {
		super();
		this.k = key;
		this.v = value;
	}

	public String getKey() {
		return k;
	}
	
	public void setKey(String key) {
		this.k = key;
	}
	
	public String getValue() {
		return v;
	}
	
	public void setValue(String value) {
		this.v = value;
	}
	
}
