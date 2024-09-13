package com.turf_booking.user_sapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.turf_booking.user_sapi.dao.UserDao;
import com.turf_booking.user_sapi.model.ApiError;
import com.turf_booking.user_sapi.model.ApiResponse;
import com.turf_booking.user_sapi.model.UserDetails;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;

	public ResponseEntity<ApiResponse<UserDetails>> getUserById(Integer userId) {
		
		ApiResponse<UserDetails> apiResponse = new ApiResponse<>();
		ApiError apiError = new ApiError();
		String customError = "";
		
		try {
			
			UserDetails user = userDao.findById(userId).get();
			apiResponse.setPayload(user);
			
		} catch (Exception e) {

			customError = "Error while getting the User with the ID: " + userId;
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(500);
		}
		
		return new ResponseEntity<>(apiResponse, apiResponse.getStatusMessage());
	}

	public ResponseEntity<ApiResponse<String>> addUser(UserDetails user) {
		
		ApiResponse<String> apiResponse = new ApiResponse<>();
		ApiError apiError = new ApiError();
		String customError = "";
		
		try {
			if(userDao.existsByUsername(user.getUsername())) {
				throw new RuntimeException("The User already exists");
			}
			System.out.println("Before Insert");
			UserDetails userResponse = userDao.save(user);
			System.out.println("After Insert");
			apiResponse.setPayload("The User was successfully added. Here is the User ID: " + userResponse.getUserId());
			
		} catch (RuntimeException e) {
			customError = "Error thrown while adding the User as the User already exists";
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(500);
		} catch (Exception e) {
			customError = "Error while adding the User";
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(500);
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}
	
	public ResponseEntity<ApiResponse<String>> updateUser(UserDetails user) {
		
		ApiResponse<String> apiResponse = new ApiResponse<>();
		ApiError apiError = new ApiError();
		String customError = "";
		
		try {
			
			if(!userDao.existsById(user.getUserId())) {
				throw new RuntimeException("The User doesn't exist");
			}
			UserDetails userResponse = userDao.save(user);
			apiResponse.setPayload("The User was successfully added. Here is the User ID: " + userResponse.getUserId());
			
		} catch (RuntimeException e) {
			customError = "Error thrown while updating the User as the User wasn't found";
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(404);
		} catch (Exception e) {
			customError = "Error while updating the User";
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(500);
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}

	public ResponseEntity<ApiResponse<String>> deleteUser(Integer userId) {
		
		ApiResponse<String> apiResponse = new ApiResponse<>();
		ApiError apiError = new ApiError();
		String customError = "";
		
		try {
			
			userDao.deleteById(userId);
			apiResponse.setPayload("The User with Id: " + userId + " was successfully deleted");
			
		} catch (Exception e) {
			
			customError = "Error while deleting the User";
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(500);
			
		}
		
		return new ResponseEntity<>(apiResponse, apiResponse.getStatusMessage());
	}

}
