package com.turf_booking.user_sapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turf_booking.user_sapi.model.ApiResponse;
import com.turf_booking.user_sapi.model.UserDetails;
import com.turf_booking.user_sapi.service.UserService;

@RestController
@RequestMapping("api/user-sapi")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("live")
	public String getHealth(){
		return "live";
	}
	
	@GetMapping("user/{userId}")
	public ResponseEntity<ApiResponse<UserDetails>> getUserById (@PathVariable Integer userId){ 
		
		return userService.getUserById(userId);
	}
	
	@PostMapping("user")
	public ResponseEntity<ApiResponse<String>> addUser (@RequestBody UserDetails user){
		
		return userService.addUser(user);
	}
	
	@PutMapping("user")
	public ResponseEntity<ApiResponse<String>> updateUser (@RequestBody UserDetails user){
		
		return userService.updateUser(user);
		
	}
		
	@DeleteMapping("user/{userId}")
	public ResponseEntity<ApiResponse<String>> deleteUser (@PathVariable Integer userId){
		
		return userService.deleteUser(userId);
	}
	

}
