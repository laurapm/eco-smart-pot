package com.rainforest.eco.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="plant")
public class Plant 
{
	@Id
	private ObjectId id;
	
	private String common_name;
	private String scientific_name;
	private String family;
	//private Array<JSONObject> lifesafe_range;
}
