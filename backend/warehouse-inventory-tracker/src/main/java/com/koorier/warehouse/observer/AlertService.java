package com.koorier.warehouse.observer;

import com.koorier.warehouse.model.Product;

public interface AlertService {
    void onLowStock(Product product);
}
