package com.rainforesteco.spring.data.mongodb.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "owner")
public class User 
{
	@Id
	private String id;
	private String username;
	private String name;
	private String surname;
	private String courtesy_title;
	private String phone;
	private String email;
	private String password;
	
	public User() {
		
	}
	
	public User(String username, String name, String surname, String courtesy_title, String phone,
			String email, String password) {
		super();
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.courtesy_title = courtesy_title;
		this.phone = phone;
		this.email = email;
		this.password = password;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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
	
	public String getCourtesy_title() {
		return courtesy_title;
	}
	
	public void setCourtesy_title(String courtesy_title) {
		this.courtesy_title = courtesy_title;
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
	
}
