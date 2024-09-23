package com.turf_booking.user_sapi.error;

public class InvalidUser extends CustomUserException {
    public InvalidUser(String message) {
        super(message);
    }
    public InvalidUser(Exception exception, String message) {
      super(exception, message);
    }
}
