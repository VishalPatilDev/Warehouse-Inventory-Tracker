package com.koorier.warehouse.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.koorier.warehouse.dto.ProductRequestDto;
import com.koorier.warehouse.model.Product;

@Service
public class ProductServiceImpl implements ProductService{
	private final Map<String, Product> store = new ConcurrentHashMap<>();

	@Override
	public Product addProduct(ProductRequestDto dto) {
		 String id = "SKU-" + UUID.randomUUID().toString().substring(0,8);
	        Product p = Product.builder()
	                .productId(id)
	                .name(dto.getName())
	                .quantity(dto.getQuantity())
	                .reorderThreshold(dto.getReorderThreshold())
	                .build();
	        store.put(id, p);
	        return p;
	}

}
