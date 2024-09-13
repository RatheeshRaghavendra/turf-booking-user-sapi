package com.turf_booking.user_sapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turf_booking.user_sapi.model.UserDetails;

@Repository
public interface UserDao extends JpaRepository<UserDetails, Integer>{
	
	boolean existsByUsername(String username);

}
