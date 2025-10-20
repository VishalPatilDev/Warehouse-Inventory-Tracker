package com.koorier.warehouse.controller;

import com.koorier.warehouse.dto.FulfillmentResponse;
import com.koorier.warehouse.dto.OrderDto;
import com.koorier.warehouse.dto.ProductRequestDto;
import com.koorier.warehouse.dto.ProductResponseDto;
import com.koorier.warehouse.dto.ShipmentDto;
import com.koorier.warehouse.model.Product;
import com.koorier.warehouse.service.Warehouse;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/products")

public class WarehouseController {
	@Autowired
    private Warehouse service;
   
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
    
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<?> getProductsByWarehouse(@PathVariable String warehouseId) {
        return ResponseEntity.ok(service.getProductsByWarehouse(warehouseId));
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

    @PutMapping("/v/order")
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
    @PutMapping("/order")
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
            response.setAlert("Restock Alert: Low stock for " + p.getName() +
                              " (Remaining: " + p.getQuantity() + ")");
        }
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<?> getAllProducts(){
    	return ResponseEntity.ok(service.getAllProducts());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id){
    	return ResponseEntity.ok(service.getProductById(id.toString()));
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable String id){
    	service.deleteProduct(id);
    	return ResponseEntity.ok("deleted product with id "+id+"!");
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable String id,@Valid @RequestBody ProductRequestDto req){
    	return ResponseEntity.ok(service.updateProduct(id, req));
    }
}
