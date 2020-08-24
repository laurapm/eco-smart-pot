package com.rainforest.eco.models.documents;

import org.bson.types.ObjectId;

public class Item 
{
	private ObjectId product;
	private int quantity;
	
	public Item( ) { }
	
	public Item(String product, int quantity) {
		super();
		this.product = new ObjectId(product);
		this.quantity = quantity;
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
	
}
