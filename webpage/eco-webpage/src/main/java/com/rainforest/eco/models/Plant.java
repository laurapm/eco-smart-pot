package com.rainforest.eco.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.rainforest.eco.models.documents.MinMax;

@Document(collection="plant")
public class Plant 
{
	@Id
	private ObjectId id;
	
	private String commonName;
	private String scientificName;
	private String family;
	private List<MinMax> lifesafeRange;
	
	public Plant( ) { }
	
	public Plant(String commonName, String scientificName, String family,
			List<MinMax> lifesafeRange) {
		super();
		this.commonName = commonName;
		this.scientificName = scientificName;
		this.family = family;
		this.lifesafeRange = lifesafeRange;
	}
	
	public Plant(String id, String commonName, String scientificName, String family,
			List<MinMax> lifesafeRange) {
		super();
		this.id = new ObjectId(id);
		this.commonName = commonName;
		this.scientificName = scientificName;
		this.family = family;
		this.lifesafeRange = lifesafeRange;
	}
	
	public void addLifesafeRange(MinMax doc) {
		this.lifesafeRange.add(doc);
	}
	
	public MinMax getDocumentLifesafeRange(int index) {
		return this.lifesafeRange.get(index);
	}

	public String getId() {
		return id.toHexString();
	}

	public void setId(String id) {
		this.id = new ObjectId(id);
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public List<MinMax> getLifesafeRange() {
		return lifesafeRange;
	}

	public void setLifesafeRange(List<MinMax> lifesafeRange) {
		this.lifesafeRange = lifesafeRange;
	}

	@Override
	public String toString() {
		return "Plant [common_name=" + commonName + ", scientific_name=" + scientificName + ", family="
				+ family + ", lifesafe_range=" + lifesafeRange + "]";
	}	
	
}
