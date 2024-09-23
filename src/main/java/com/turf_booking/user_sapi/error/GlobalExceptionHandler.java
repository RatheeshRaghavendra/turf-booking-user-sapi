package com.turf_booking.user_sapi.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.turf_booking.user_sapi.model.ApiError;
import com.turf_booking.user_sapi.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({HandlerMethodValidationException.class})
    public ResponseEntity<ApiResponse<String>> handleValidationException(HandlerMethodValidationException e) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse<>();
        ApiError apiError = new ApiError();
        String customError = "Error in field validation";
        List<String> errorMsg= Arrays.stream(Arrays.stream(e.getDetailMessageArguments()).toList().get(0).toString().split(", and ")).toList();
        String errorStr = String.join(" | ",errorMsg);
        apiError.setErrorMessage(e.getMessage());
        apiError.setErrorDescription(errorStr);
        apiError.setCustomError(customError);
        apiResponse.setApiError(apiError);
        apiResponse.setStatusCode(400);

        return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponse<String>> methodArgumentNotValidException(MethodArgumentNotValidException e) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse<>();
        ApiError apiError = new ApiError();
        String customError = "Error in field validation";
        List<String> errorMsg= Arrays.stream(Arrays.stream(e.getDetailMessageArguments()).toList().get(1).toString().split(", and ")).toList();
        String errorStr = String.join(" | ",errorMsg);
        apiError.setErrorMessage(errorStr);
        apiError.setErrorDescription(e.getMessage());
        apiError.setCustomError(customError);
        apiResponse.setApiError(apiError);
        apiResponse.setStatusCode(400);

        return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
    }

    @ExceptionHandler({UserNotFound.class})//, SlotNotFound.class})
    public ResponseEntity<ApiResponse<String>> handleUserNotException(CustomUserException e){

        ApiResponse apiResponse = new ApiResponse<>();
        ApiError apiError = new ApiError();
        String customError = e.getMessage();
        apiError.setApiErrorDetails(e.getException(), customError);
        apiResponse.setStatusCode(404);
        apiResponse.setApiError(apiError);

        return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
    }

    @ExceptionHandler({InvalidUser.class})
    public ResponseEntity<ApiResponse<String>> handleInvalidUserException(CustomUserException e){

        ApiResponse apiResponse = new ApiResponse<>();
        ApiError apiError = new ApiError();
        String customError = e.getMessage();
        apiError.setApiErrorDetails(e, customError);
        apiResponse.setStatusCode(400);
        apiResponse.setApiError(apiError);

        return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
    }

    @ExceptionHandler({UserAlreadyExists.class})
    public ResponseEntity<ApiResponse<String>> handleAlreadyExistsException(CustomUserException e){

        ApiResponse apiResponse = new ApiResponse<>();
        ApiError apiError = new ApiError();
        String customError = e.getMessage();
        apiError.setApiErrorDetails(e, customError);
        apiResponse.setStatusCode(500);
        apiResponse.setApiError(apiError);

        return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
    }

    @ExceptionHandler({UserUnexpectedException.class})//, SlotAlreadyExists.class, AlreadyExists.class,})
    public ResponseEntity<ApiResponse<String>> handleException(CustomUserException e){

        ApiResponse apiResponse = new ApiResponse<>();
        ApiError apiError = new ApiError();
        String customError = e.getMessage();
        apiError.setApiErrorDetails(e.getException(), customError);
        apiResponse.setStatusCode(500);
        apiResponse.setApiError(apiError);

        return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
    }
}
