package com.turf_booking.user_sapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Data
@Entity
public class UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer userId;
	@Column(unique = true)
	String username;
	String password;
	String firstName;
	String lastName;
	String phoneNumber;
	String emailId;
	
	public UserDetails(Integer userId, String username, String password, String firstName, String lastName, String phoneNumber,
			String emailId) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
	}

	public UserDetails() {
		super();
	}
	
}
