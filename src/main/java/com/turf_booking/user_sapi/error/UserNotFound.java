package com.turf_booking.user_sapi.error;

public class UserNotFound extends CustomUserException {

    public UserNotFound(String message) {
        super(message);
    }
    public UserNotFound(Exception e, String message) {
        super(e,message);
    }

}
