package com.koorier.warehouse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warehouse")
public class DummyController {
@GetMapping("/welcome")
public ResponseEntity<?> welcome(){
	return ResponseEntity.ok("Welcome to warehouse-inventory");
}
}
