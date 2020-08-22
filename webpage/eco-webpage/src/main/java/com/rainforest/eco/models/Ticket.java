package com.rainforest.eco.models;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="ticket")
public class Ticket 
{
	@Id
	private ObjectId id;
	
	private ObjectId owner;
	//private JSONObject item;
	private Date date;
}
