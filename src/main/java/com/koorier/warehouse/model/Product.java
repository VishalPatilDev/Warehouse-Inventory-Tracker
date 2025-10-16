package com.koorier.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Product {
private String productId;
private String name;
private int quantity;
private int reorderThreshold;
}

