package com.rainforest.eco.models;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.rainforest.eco.models.documents.Item;

@Document(collection="ticket")
public class Ticket 
{
	@Id
	private ObjectId id;
	
	private ObjectId owner;
	private List<Item> item;
	private Date date;
	
	public Ticket( ) { }
	
	public Ticket(String owner, List<Item> item, Date date) {
		super();
		this.owner = new ObjectId(owner);
		this.item = item;
		this.date = date;
	}
	
	public Ticket(String id, String owner, List<Item> item, Date date) {
		super();
		this.id = new ObjectId(id);
		this.owner = new ObjectId(owner);
		this.item = item;
		this.date = date;
	}

	public String getId() {
		return id.toHexString();
	}

	public void setId(String id) {
		this.id = new ObjectId(id);
	}

	public String getOwner() {
		return owner.toHexString();
	}

	public void setOwner(String owner) {
		this.owner = new ObjectId(owner);
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
