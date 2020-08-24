package com.rainforest.eco.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="model_version")
public class ModelVersion 
{
	@Id
	private ObjectId id;
	
	private String version;
	private String changes;
	
	public ModelVersion( ) { }
	
	public ModelVersion(String version, String changes) {
		super();
		this.version = version;
		this.changes = changes;
	}

	public ModelVersion(ObjectId id, String version, String changes) {
		super();
		this.id = id;
		this.version = version;
		this.changes = changes;
	}

	public String getId() {
		return id.toHexString();
	}

	public void setId(String id) {
		this.id = new ObjectId(id);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getChanges() {
		return changes;
	}

	public void setChanges(String changes) {
		this.changes = changes;
	}	
	
}
