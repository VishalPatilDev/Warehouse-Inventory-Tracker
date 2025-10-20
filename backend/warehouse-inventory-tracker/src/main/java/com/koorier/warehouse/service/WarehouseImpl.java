package com.koorier.warehouse.service;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.koorier.warehouse.custom_exception.InsufficientStockException;
import com.koorier.warehouse.custom_exception.ProductNotFoundException;
import com.koorier.warehouse.dto.OrderDto;
import com.koorier.warehouse.dto.ProductRequestDto;
import com.koorier.warehouse.dto.ProductResponseDto;
import com.koorier.warehouse.dto.ShipmentDto;
import com.koorier.warehouse.model.Product;
import com.koorier.warehouse.observer.AlertService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class WarehouseImpl implements Warehouse{
	private final String INVENTORY_FILE = "warehouse_inventory.json";
//	private final Map<String, Product> store = new ConcurrentHashMap<>();
	//Multiple WArehouses
	private final Map<String,Map<String,Product>> warehouses = new ConcurrentHashMap<>();
	private final List<AlertService> observers = new ArrayList<>();
	
	private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT); // pretty print
	
	private final Object lock = new Object();
	
	@Autowired
	private AlertService alertService;
	
	 @PostConstruct
	    public void init() {
	        // register alert observer
	        observers.add(alertService);
//	        loadInventoryFromFile();
	    }
	 
	 private Map<String,Product> getProductsFromWare(String wareID){
//		 return warehouses.get(wareID); //Null ptr excepn when adding new product
		 //when user addding new product with new warehouse new map should be created
		 return warehouses.computeIfAbsent(wareID, p->new ConcurrentHashMap<>());
	 }

	@Override
	public Product addProduct(ProductRequestDto dto) {
		// TODO Auto-generated method stub
		synchronized(lock) {
			Map<String,Product> store = getProductsFromWare(dto.getWarehouseId());
		 String id = "SKU-" + UUID.randomUUID().toString().substring(0,8); //Stock Keeping Unit
	        Product p = Product.builder()
	                .productId(id)
	                .name(dto.getName())
	                .quantity(dto.getQuantity())
	                .reorderThreshold(dto.getReorderThreshold())
	                .build();
	        store.put(id, p);
//	        System.out.println("Product Added - "+p.getName());
	        log.info("{} -  Product Added !",p.getName());
	        saveInventoryToFile();
	        return p;
		}
	}

	@Override
	public Collection<Product> getAllProducts() {
		// TODO Auto-generated method stub
		synchronized(lock) {
//		loadInventoryFromFile();
//		return store.values();
			List<Product> pdts = new ArrayList<>();
			warehouses.values().forEach(p->pdts.addAll(p.values()));
			return pdts;
		}
	}
	
	public Collection<Product> getProductsByWarehouse(String warehouseId) {
        synchronized (lock) {
            return getProductsFromWare(warehouseId).values();
        }
    }

	@Override
	public Product receiveShipment(ShipmentDto dto) {
		// TODO Auto-generated method stub
		synchronized(lock) {
			Map<String, Product> store = getProductsFromWare(dto.getWarehouseId());
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
			Map<String, Product> store = getProductsFromWare(dto.getWarehouseId());
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
		synchronized(lock) {
//		Product p = store.get(id);
//		if(p == null) {
//			throw new ProductNotFoundException("Product NOt Found with id : "+id);
//		}
//		return ProductResponseDto.builder()
//				.productId(p.getProductId())
//				.name(p.getName())
//				.quantity(p.getQuantity())
//				.reorderThreshold(p.getReorderThreshold())
//				.build();
			for (Map<String, Product> store : warehouses.values()) {
                Product p = store.get(id);
                if (p != null)
                    return ProductResponseDto.builder()
                            .productId(p.getProductId())
                            .name(p.getName())
                            .quantity(p.getQuantity())
                            .reorderThreshold(p.getReorderThreshold())
                            .build();
            }
            throw new ProductNotFoundException("Product Not Found with id: " + id);
		}
	}

	@Override
	public ProductResponseDto updateProduct(String id, ProductRequestDto request) {
		// TODO Auto-generated method stub
		synchronized(lock) {
			Map<String, Product> store = getProductsFromWare(request.getWarehouseId());
		Product p = store.get(id);
		if(p== null) throw new ProductNotFoundException("Product with id : "+id+"Not found in warehouse "+request.getWarehouseId());
		 p.setName(request.getName());
	        p.setQuantity(request.getQuantity());
	        p.setReorderThreshold(request.getReorderThreshold());

	        saveInventoryToFile();
	        
	        return ProductResponseDto.builder()
	                .productId(p.getProductId())
	                .name(p.getName())
	                .quantity(p.getQuantity())
	                .reorderThreshold(p.getReorderThreshold())
	                .build();
		}
	}

	@Override
	public void deleteProduct(String id) {
		// TODO Auto-generated method stub
		synchronized(lock) {
			warehouses.values().forEach(store -> store.remove(id));
			saveInventoryToFile();
		}
	}
	
	private void saveInventoryToFile() {
		synchronized(lock) {
        try {
        	objectMapper.writeValue(new File(INVENTORY_FILE), /*store*/warehouses);
//            System.out.println("Product Saved to Inventory !!");
        	log.info("Products saved {} ",/*store*/warehouses.size());
        } catch (Exception e) {
//        	System.out.println("Error Saving product to Inventory !!");
        	log.error(e.getMessage());
        }
		}
    }
	
	private void loadInventoryFromFile() {
		synchronized(lock) {
        File file = new File(INVENTORY_FILE);
        if (!file.exists()) {
            log.info("No existing inventory file found. Starting fresh.");
            return;
        }
        try {
            Map<String,Map<String, Product>> loaded = objectMapper.readValue(file,
                    objectMapper.getTypeFactory().constructMapType(Map.class, String.class, objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Product.class).getClass()));
            /*store*/warehouses.putAll(loaded);
            log.info("Inventory loaded: {} products", loaded.size());
        } catch (Exception e) {
            log.error("Error loading inventory: {}", e.getMessage());
        }
		}
    }

	
	

}
