package com.rainforest.eco.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="product")
public class Product 
{
	private ObjectId id;
	private String name;
	private double price;
	private String description;
	
	public Product( ) { }

	public Product(String name, double price, String description) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
	}

	public Product(String id, String name, double price, String description) {
		super();
		this.id = new ObjectId(id);
		this.name = name;
		this.price = price;
		this.description = description;
	}

	public String getId() {
		return id.toHexString();
	}

	public void setId(String id) {
		this.id = new ObjectId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
