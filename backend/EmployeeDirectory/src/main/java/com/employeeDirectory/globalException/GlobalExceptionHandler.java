package com.employeeDirectory.globalException;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.employeeDirectory.customException.ResourceNotFoundException;
import com.employeeDirectory.dto.ErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// RESOURCE NOT FOUND EXCEPTION
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(

			ResourceNotFoundException ex) {

		ErrorResponse error = ErrorResponse.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value()).message(ex.getMessage()).build();

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	// GENERAL EXCEPTION
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {

		ErrorResponse error = ErrorResponse.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(ex.getMessage()).build();

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
