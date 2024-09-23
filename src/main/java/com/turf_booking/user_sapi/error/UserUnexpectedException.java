package com.turf_booking.user_sapi.error;

public class UserUnexpectedException extends CustomUserException {
    public UserUnexpectedException(String message) {
        super(message);
    }
    public UserUnexpectedException(Exception e, String message) {
        super(e,message);
    }
}
