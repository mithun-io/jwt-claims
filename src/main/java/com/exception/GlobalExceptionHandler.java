package com.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ApiResponse<String>> handle(UserAlreadyExistException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("user already exists", exception.getMessage()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException exception) {
		Map<String, String> errors = new HashMap<>();
		exception.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("validation failed", errors));
	}

	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiResponse<String>> handle(NoResourceFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("no resource found", exception.getMessage()));
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse<String>> handle(HttpRequestMethodNotSupportedException exception) {
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ApiResponse<>("http request method not supported", exception.getMessage()));
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ApiResponse<String>> handle(NoSuchElementException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("no such element found", exception.getMessage()));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<String>> handle(Exception exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("something went wrong", exception.getMessage()));
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse<String>> handle(BadCredentialsException exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("wrong credentials", exception.getMessage()));
	}
}
