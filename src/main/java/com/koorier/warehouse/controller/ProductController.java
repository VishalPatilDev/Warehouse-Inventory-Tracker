package com.koorier.warehouse.controller;

import com.koorier.warehouse.dto.ProductRequestDto;
import com.koorier.warehouse.dto.ProductResponseDto;
import com.koorier.warehouse.model.Product;
import com.koorier.warehouse.service.ProductService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")

public class ProductController {
	@Autowired
    private ProductService service;
   
    @PostMapping
    public ResponseEntity<ProductResponseDto> addProduct(@Valid @RequestBody ProductRequestDto dto) {
        Product p = service.addProduct(dto);
        ProductResponseDto resp = ProductResponseDto.builder()
                .productId(p.getProductId())
                .name(p.getName())
                .quantity(p.getQuantity())
                .reorderThreshold(p.getReorderThreshold())
                .build();
        return ResponseEntity.status(201).body(resp);
    }
    
    @GetMapping
    public ResponseEntity<?> getAllProducts(){
    	return ResponseEntity.ok(service.getAllProducts());
    }
}
