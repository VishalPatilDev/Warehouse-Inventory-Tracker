package com.koorier.warehouse.service;


import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
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
public class WarehouseImpl implements Warehouse{
	private final String INVENTORY_FILE = "warehouse_inventory.dat";
	private final Map<String, Product> store = new ConcurrentHashMap<>();
	private final List<AlertService> observers = new ArrayList<>();
	
	private final Object lock = new Object();
	
	@Autowired
	private AlertService alertService;
	
	 @PostConstruct
	    public void init() {
	        // register alert observer
	        observers.add(alertService);
	    }

	@Override
	public Product addProduct(ProductRequestDto dto) {
		// TODO Auto-generated method stub
		synchronized(lock) {
		 String id = "SKU-" + UUID.randomUUID().toString().substring(0,8); //Stock Keeping Unit
	        Product p = Product.builder()
	                .productId(id)
	                .name(dto.getName())
	                .quantity(dto.getQuantity())
	                .reorderThreshold(dto.getReorderThreshold())
	                .build();
	        store.put(id, p);
	        System.out.println("Product Added - "+p.getName());
	        saveInventoryToFile();
	        return p;
		}
	}

	@Override
	public Collection<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return store.values();
	}

	@Override
	public Product receiveShipment(ShipmentDto dto) {
		// TODO Auto-generated method stub
		synchronized(lock) {
		Product p = store.get(dto.getProductId());
        if (p == null) throw new ProductNotFoundException("Invalid productId: " + dto.getProductId());
        p.setQuantity(p.getQuantity() + dto.getQuantity());
        return p;
		}
	}

	@Override
	public Product fulfillOrder(OrderDto dto) {
		// TODO Auto-generated method stub
		synchronized(lock) {
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
	}
	
	@Override
	public ProductResponseDto getProductById(String id) {
		// TODO Auto-generated method stub
		Product p = store.get(id);
		if(p == null) {
			throw new ProductNotFoundException("Product NOt Found with id : "+id);
		}
		return ProductResponseDto.builder()
				.productId(p.getProductId())
				.name(p.getName())
				.quantity(p.getQuantity())
				.reorderThreshold(p.getReorderThreshold())
				.build();
		
	}

	@Override
	public ProductResponseDto updateProduct(String id, ProductRequestDto request) {
		// TODO Auto-generated method stub
		Product p = store.get(id);
		if(p== null) throw new ProductNotFoundException("Product with id : "+id+"Not found !");
		return ProductResponseDto.builder().name(request.getName()).quantity(request.getQuantity()).reorderThreshold(request.getReorderThreshold()).build();
	}

	@Override
	public void deleteProduct(String id) {
		// TODO Auto-generated method stub
		Product p = store.get(id);
		if(p == null) throw new ProductNotFoundException("Product with id : "+id+"not found !!");
		store.remove(id);
	}
	
	private void saveInventoryToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(INVENTORY_FILE))) {
            oos.writeObject(store);
            System.out.println("Product Saved to Inventory !!");
        } catch (Exception e) {
        	System.out.println("Error Saving product to Inventory !!");
        }
    }
	

}
