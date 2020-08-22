package com.rainforest.eco.models;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.BasicDBList;

@Document(collection="treatment")
public class Treatment 
{
	@Id
	private ObjectId id;
	
	private ObjectId plant;
	private String type;
	private String title;
	private BasicDBList action; // It contains the fields:
								// String perform
								// BSON params
	private Date request_time;
	private Date action_time;
	private String comment;
}
