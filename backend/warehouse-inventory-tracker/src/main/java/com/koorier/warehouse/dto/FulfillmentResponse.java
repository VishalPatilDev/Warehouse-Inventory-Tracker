package com.koorier.warehouse.dto;
//In case we want to log alert on frontend also (onLowStock)

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FulfillmentResponse {
    private ProductResponseDto product;
    private String alert;
}
