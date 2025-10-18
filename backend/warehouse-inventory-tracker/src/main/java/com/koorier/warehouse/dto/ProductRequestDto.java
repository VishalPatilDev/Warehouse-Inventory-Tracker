package com.koorier.warehouse.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
@NotBlank(message = "name is required")
private String name;
@Min(value = 0, message = "quantity must be positive")
private int quantity = 0;
@Min(value = 0, message = "reorderThreshold must be >= 0")
private int reorderThreshold = 0;
}

