package com.koorier.warehouse.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		Map<String, String> errs = new HashMap<>();
		errs = e.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errs);
	}

}
