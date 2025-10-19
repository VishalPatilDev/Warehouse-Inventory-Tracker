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
public class OrderDto {
    @NotBlank(message = "productId is required")
    private String productId;

    @Min(value = 1, message = "quantity must be > 0")
    private int quantity;
    @NotBlank(message = "Warehouse Id is required")
    private String warehouseId;
}
