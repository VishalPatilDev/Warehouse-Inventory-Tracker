package com.koorier.warehouse.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koorier.warehouse.custom_exception.InsufficientStockException;
import com.koorier.warehouse.custom_exception.ProductNotFoundException;
import com.koorier.warehouse.dto.OrderDto;
import com.koorier.warehouse.dto.ProductRequestDto;
import com.koorier.warehouse.dto.ProductResponseDto;
import com.koorier.warehouse.dto.ShipmentDto;
import com.koorier.warehouse.model.Product;
import com.koorier.warehouse.observer.AlertService;

import jakarta.annotation.PostConstruct;

@Service
public class ProductServiceImpl implements ProductService{
	private final Map<String, Product> store = new ConcurrentHashMap<>();
	private final List<AlertService> observers = new ArrayList<>();
	
	@Autowired
	private AlertService alertService;
	
	 
	@Override
	public Product addProduct(ProductRequestDto dto) {
		// TODO Auto-generated method stub
		 String id = "SKU-" + UUID.randomUUID().toString().substring(0,8); //Stock Keeping Unit
	        Product p = Product.builder()
	                .productId(id)
	                .name(dto.getName())
	                .quantity(dto.getQuantity())
	                .reorderThreshold(dto.getReorderThreshold())
	                .build();
	        store.put(id, p);
	        return p;
	}

	@Override
	public Collection<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return store.values();
	}

	@Override
	public Product receiveShipment(ShipmentDto dto) {
		// TODO Auto-generated method stub
		Product p = store.get(dto.getProductId());
        if (p == null) throw new ProductNotFoundException("Invalid productId: " + dto.getProductId());
        p.setQuantity(p.getQuantity() + dto.getQuantity());
        return p;
	}

	@Override
	public Product fulfillOrder(OrderDto dto) {
		// TODO Auto-generated method stub
		Product p = store.get(dto.getProductId());
        if (p == null) throw new ProductNotFoundException("Invalid productId: " + dto.getProductId());

        if (p.getQuantity() < dto.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock for " + p.getName());
        }
        
        p.setQuantity(p.getQuantity() - dto.getQuantity());
        
        if (p.getQuantity() < p.getReorderThreshold()) {
        	observers.forEach(obs -> obs.onLowStock(p));
        }
        return p;
	}
	
	@Override
	public ProductResponseDto getProductById(String id) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public ProductResponseDto updateProduct(String id, ProductRequestDto request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProduct(String id) {
		// TODO Auto-generated method stub
		
	}
	
	

}
