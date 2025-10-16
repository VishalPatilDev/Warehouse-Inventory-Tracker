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
import org.springframework.web.servlet.NoHandlerFoundException;

import com.koorier.warehouse.custom_exception.InsufficientStockException;
import com.koorier.warehouse.custom_exception.ProductNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		Map<String, String> errs = new HashMap<>();
		errs = e.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errs);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> handleInvalidId(ProductNotFoundException ex) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<String> handleStockError(InsufficientStockException ex) {
	    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNotFound(NoHandlerFoundException ex, HttpServletRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Endpoint not found");
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("path", request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


}
