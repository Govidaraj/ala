package com.GroDel.App.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;

@Document(collection="Staff")
public class User1 {
	
	@Id
	private String id;
	
	private String name;
	@Email
	private String email;
	@Max(value = 10, message = "The mobile number must be less than or equal to {value}")
	private String mobNo;
	private String password;
//	private UserType userType;
//	
//	public enum UserType{
//		Admin,User,Store
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public UserType getUserType() {
//		return userType;
//	}
//
//	public void setUserType(UserType userType) {
//		this.userType = userType;
//	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	public User1(String id, String name, String email, String mobNo, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.setMobNo(mobNo);
		this.password = password;
//		this.userType = userType;
	}

	public void update(User1 staff)
	{
		this.setName(staff.getName());
		this.setEmail(staff.getEmail());
//		this.setUserType(staff.getUserType());
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}
	

}
