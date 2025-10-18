package com.koorier.warehouse.dto;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ProductResponseDto {
private String productId;
private String name;
private int quantity;
private int reorderThreshold;
}
