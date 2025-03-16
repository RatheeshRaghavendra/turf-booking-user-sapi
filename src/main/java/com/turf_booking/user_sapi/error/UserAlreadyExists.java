package com.turf_booking.user_sapi.error;

public class UserAlreadyExists extends CustomUserException {
    public UserAlreadyExists(String message) {
        super(message);
    }
    public UserAlreadyExists(Exception exception, String message) {
        super(exception,message);
    }
}
