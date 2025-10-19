package com.koorier.warehouse.service;

import com.koorier.warehouse.dto.OrderDto;
import com.koorier.warehouse.dto.ProductRequestDto;
import com.koorier.warehouse.dto.ProductResponseDto;
import com.koorier.warehouse.dto.ShipmentDto;
import com.koorier.warehouse.model.Product;

import java.util.Collection;

import org.springframework.stereotype.Service;
@Service
public interface Warehouse {
    public Product addProduct(ProductRequestDto dto);
    
//    public Collection<Product> getAllProducts();
    
//    public Product receiveShipment(ShipmentDto dto);
    
//    public Product fulfillOrder(OrderDto dto);
    
//    public ProductResponseDto getProductById(String id);
    
//    public ProductResponseDto updateProduct(String id, ProductRequestDto request);
    
//    public void deleteProduct(String id);
    
    
    
    
}
