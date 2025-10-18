package com.koorier.warehouse.custom_exception;

public class InsufficientStockException extends RuntimeException {
	public InsufficientStockException(String msg) {
        super(msg);
    }
}
