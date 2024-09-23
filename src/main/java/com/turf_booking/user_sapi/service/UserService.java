package com.turf_booking.user_sapi.service;

import com.turf_booking.user_sapi.error.InvalidUser;
import com.turf_booking.user_sapi.error.UserAlreadyExists;
import com.turf_booking.user_sapi.error.UserNotFound;
import com.turf_booking.user_sapi.error.UserUnexpectedException;
import com.turf_booking.user_sapi.logger.GlobalLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.turf_booking.user_sapi.dao.UserDao;
import com.turf_booking.user_sapi.model.ApiError;
import com.turf_booking.user_sapi.model.ApiResponse;
import com.turf_booking.user_sapi.model.UserDetails;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Log4j2
public class UserService {
	
	@Autowired
	UserDao userDao;

	private final String prefix = GlobalLog.prefix + getClass().getSimpleName() + "::"; // appName + "::" +

	public ResponseEntity<ApiResponse<UserDetails>> getUserById(Integer userId) {
		
		ApiResponse<UserDetails> apiResponse = new ApiResponse<>();
		try {
			UserDetails user = userDao.findById(userId).orElseThrow();
			apiResponse.setPayload(user);
		} catch (NoSuchElementException e) {
			log.error(prefix + "getUserById::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new UserNotFound(e,"No Turf with the Turf ID: "+ userId +", Present in the DB");
		} catch (Exception e) {
			log.error(prefix + "getUserById::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new UserUnexpectedException(e,"Error while getting the User with the ID: " + userId);
		}
		
		return new ResponseEntity<>(apiResponse, apiResponse.getStatusMessage());
	}

	public ResponseEntity<ApiResponse<List<UserDetails>>> getAllUsers() {

		ApiResponse<List<UserDetails>> apiResponse = new ApiResponse<>();
		try{
			List<UserDetails> allUsers = userDao.findAll();
			apiResponse.setPayload(allUsers);
		} catch (Exception e) {
			log.error(prefix + "getAllUsers::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new UserUnexpectedException(e,"Unexpected Error while getting all the Users");
		}

		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}

	public ResponseEntity<ApiResponse<String>> addUser(UserDetails user) {
		
		ApiResponse<String> apiResponse = new ApiResponse<>();
		try {
			if(userDao.existsByUsername(user.getUsername())) {
				throw new UserAlreadyExists("The User already exists");
			}
			log.debug("Before Insert");
			UserDetails userResponse = userDao.save(user);
			log.debug("After Insert");
			apiResponse.setPayload("The User was successfully added. Here is the User ID: " + userResponse.getUserId());
		} catch (UserAlreadyExists e) {
			log.error(prefix + "addUser::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(prefix + "addUser::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new UserUnexpectedException(e,"Error while adding the User");
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}
	
	public ResponseEntity<ApiResponse<String>> updateUser(UserDetails user) {
		
		ApiResponse<String> apiResponse = new ApiResponse<>();
		try {
			if(user.getUserId() == null){
				throw new InvalidUser("User ID cannot be null");
			}
			else if(!userDao.existsById(user.getUserId())) {
				throw new UserNotFound("The User doesn't exist");
			}
			UserDetails userResponse = userDao.save(user);
			apiResponse.setPayload("The User was successfully added. Here is the User ID: " + userResponse.getUserId());
		} catch (InvalidUser e) {
			log.error(prefix + "updateUser::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw e;
		} catch (UserNotFound e) {
			log.error(prefix + "updateUser::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new UserNotFound(e,"User with ID: " + user.getUserId() + " doesn't exist");
		} catch (Exception e) {
			log.error(prefix + "updateUser::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new UserUnexpectedException(e,"Unexpected Error while updating the User");
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}

	public ResponseEntity<ApiResponse<String>> deleteUser(Integer userId) {
		
		ApiResponse<String> apiResponse = new ApiResponse<>();
		try {
			if(!userDao.existsById(userId)) {
				throw new UserNotFound("The User doesn't exist");
			}
			userDao.deleteById(userId);
			apiResponse.setPayload("The User with Id: " + userId + " was successfully deleted");
		} catch (UserNotFound e) {
			log.error(prefix + "deleteUser::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new UserNotFound(e,"User with ID: " + userId + " doesn't exist");
		} catch (Exception e) {
			log.error(prefix + "deleteUser::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new UserUnexpectedException(e,"Unexpected Error while deleting the User");
		}
		
		return new ResponseEntity<>(apiResponse, apiResponse.getStatusMessage());
	}
}
