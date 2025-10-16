package com.koorier.warehouse.controller;

import com.koorier.warehouse.dto.FulfillmentResponse;
import com.koorier.warehouse.dto.OrderDto;
import com.koorier.warehouse.dto.ProductRequestDto;
import com.koorier.warehouse.dto.ProductResponseDto;
import com.koorier.warehouse.dto.ShipmentDto;
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
    
    @PutMapping("/shipment")
    public ResponseEntity<ProductResponseDto> receiveShipment(@Valid @RequestBody ShipmentDto dto) {
        Product p = service.receiveShipment(dto);
        return ResponseEntity.ok(ProductResponseDto.builder()
                .productId(p.getProductId())
                .name(p.getName())
                .quantity(p.getQuantity())
                .reorderThreshold(p.getReorderThreshold())
                .build());
    }

    @PutMapping("/order")
    public ResponseEntity<ProductResponseDto> fulfillOrder(@Valid @RequestBody OrderDto dto) {
        Product p = service.fulfillOrder(dto);
        return ResponseEntity.ok(ProductResponseDto.builder()
                .productId(p.getProductId())
                .name(p.getName())
                .quantity(p.getQuantity())
                .reorderThreshold(p.getReorderThreshold())
                .build());
    }
    
    //log only logs to the spring boot side not on frontend or pstman so created FulfillmentDto to show alert on postman
    @PutMapping("/v/order")
    public ResponseEntity<FulfillmentResponse> fulfill(@Valid @RequestBody OrderDto dto) {
        Product p = service.fulfillOrder(dto);
        
        ProductResponseDto responseDto = ProductResponseDto.builder()
            .productId(p.getProductId())
            .name(p.getName())
            .quantity(p.getQuantity())
            .reorderThreshold(p.getReorderThreshold())
            .build();

        FulfillmentResponse response = new FulfillmentResponse();
        response.setProduct(responseDto);

        if (p.getQuantity() < p.getReorderThreshold()) {
            response.setAlert("⚠️ Restock Alert: Low stock for " + p.getName() +
                              " (Remaining: " + p.getQuantity() + ")");
        }

        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<?> getAllProducts(){
    	return ResponseEntity.ok(service.getAllProducts());
    }
}
