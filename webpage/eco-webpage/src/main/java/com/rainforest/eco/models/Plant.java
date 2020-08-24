package com.rainforest.eco.models;

import java.util.List;

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
	private List<Document> lifesafe_range;
	
	public Plant( ) { }
	
	public Plant(String common_name, String scientific_name, String family,
			List<Document> lifesafe_range) {
		super();
		this.common_name = common_name;
		this.scientific_name = scientific_name;
		this.family = family;
		this.lifesafe_range = lifesafe_range;
	}
	
	public Plant(String id, String common_name, String scientific_name, String family,
			List<Document> lifesafe_range) {
		super();
		this.id = new ObjectId(id);
		this.common_name = common_name;
		this.scientific_name = scientific_name;
		this.family = family;
		this.lifesafe_range = lifesafe_range;
	}
	
	public void addLifesafeRange(Document doc) {
		this.lifesafe_range.add(doc);
	}
	
	public Document getDocumentLifesafeRange(int index) {
		return this.lifesafe_range.get(index);
	}

	public String getId() {
		return id.toHexString();
	}

	public void setId(String id) {
		this.id = new ObjectId(id);
	}

	public String getCommonName() {
		return common_name;
	}

	public void setCommonName(String commonName) {
		this.common_name = commonName;
	}

	public String getScientificName() {
		return scientific_name;
	}

	public void setScientificName(String scientificName) {
		this.scientific_name = scientificName;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public List<Document> getLifesafeRange() {
		return lifesafe_range;
	}

	public void setLifesafeRange(List<Document> lifesafe_range) {
		this.lifesafe_range = lifesafe_range;
	}	
	
}
