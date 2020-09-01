package com.rainforest.eco.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User 
{
	@Id
	private ObjectId id;
	
	private String username;
	private String name;
	private String surname;
	private String phone;
	private String email;
	private String password;
	
	public User() {
		
	}
	
	public User(String username, String name, String surname, String phone,
			String email, String password) {
		super();
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.email = email;
		this.password = password;
	}
	
	public User(ObjectId id, String username, String name, String surname, String phone,
			String email, String password) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.email = email;
		this.password = password;
	}

	public String getId() {
		return id.toHexString();
	}
	
	public void setId(String id) {
		this.id = new ObjectId(id);
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", name=" + name + ", surname=" + surname
				+ ", phone=" + phone + ", email=" + email + ", password="
				+ password + "]";
	}
	
}
