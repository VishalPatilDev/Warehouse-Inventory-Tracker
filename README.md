# Warehouse-Inventory-Tracker

# Day 1 - Project setup

- Spring Boot project generated using Spring Initializr
- Dependencies added: Spring Web, Spring Boot DevTools, Lombok, Validation
- added Product model class
- added DTOs (Data Transfer Objects) implemented to handle product data
- POST /api/v1/products — Add a new product to the inventory
- Request body is validated using: @NotBlank, @Min, @Valid
- Endpoint tested successfully using tools Postman
