package com.koorier.warehouse.observer;

import com.koorier.warehouse.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlertServiceImpl implements AlertService{

	@Override
	public void onLowStock(Product product) {
		// TODO Auto-generated method stub
		log.warn("Restock Alert: Low stock for "+product.getName()+" (Remaining: "+product.getQuantity()+")");
		
	}

}
