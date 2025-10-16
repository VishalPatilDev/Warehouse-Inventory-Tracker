package com.koorier.warehouse.service;

import com.koorier.warehouse.dto.ProductRequestDto;
import com.koorier.warehouse.model.Product;
import org.springframework.stereotype.Service;
@Service
public interface ProductService {
    public Product addProduct(ProductRequestDto dto);
}
