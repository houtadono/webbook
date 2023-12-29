package com.example.webbook.entity;

public class Account {
	private int id;
	private String username;
	private String password;
	private String email;
	private int duty;
	public Account(int id, String username, String password, String email, int duty) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.duty = duty;
	}
	
	
	public Account(String username, String password, String email, int duty) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.duty = duty;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getDuty() {
		return duty;
	}
	public void setDuty(int duty) {
		this.duty = duty;
	}


	@Override
	public String toString() {
		return "Account [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", duty=" + duty + "]";
	}
	
}
