package com.rainforest.eco.models.documents;

import org.bson.types.ObjectId;

public class Item 
{
	private ObjectId product;
	private int quantity;
	private double price;
	
	public Item( ) { }
	
	public Item(String product, int quantity, double price) {
		super();
		this.product = new ObjectId(product);
		this.quantity = quantity;
		this.price = price;
	}

	public String getProduct() {
		return product.toHexString();
	}

	public void setProduct(String product) {
		this.product = new ObjectId(product);
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}	
	
}
