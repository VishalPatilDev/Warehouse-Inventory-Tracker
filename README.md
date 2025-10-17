# Warehouse-Inventory-Tracker


# TechStack
- Java 17+

- Spring Boot 3.x

- Lombok (including @Slf4j for logging)

- Jakarta Validation

- Custom Exception Handling

- Global Exception Handling

# Project setup

- Spring Boot project generated using Spring Initializr
- Dependencies added: Spring Web, Spring Boot DevTools, Lombok, Validation
- added Product model class
- added DTOs (Data Transfer Objects) implemented to handle product data
- POST /api/v1/products - Add a new product to the inventory
- Request body is validated using: @NotBlank, @Min, @Valid
- Endpoint tested successfully using tools Postman
- GET /api/v1/products - Fetch the list of all products - 200 OK with a list of products

# Progress
- Implemented Observer pattern for event-driven stock alerts.
- Added receiveShipment(add Qty) and fulfillOrder(decrease Qty) APIs.
- Enhanced GlobalExceptionHandler for invalid product and stock errors.
- Verified workflow with sample POST/PUT requests.
- Added getOrderById API and tested with POSTMAN.

  # Crud Operations
- POST | /api/v1/products | Add new product |
{
  "name": "Monitor",
  "quantity": 10,
  "reorderThreshold": 5
}

- PUT  | /api/v1/products/shipment | Receive shipment |
{
  "productId": "SKU-bdb59158",
  "quantity": 5
}

- PUT  | /api/v1/products/order | Fulfill order |
{
  "productId": "SKU-bdb59158",
  "quantity": 3
}

- GET  | /api/v1/products | Get all products |
- GET  | /api/v1/products/{id} | Get product by ID |
- PUT  | /api/v1/products/update/{id} | Update product |
- DELETE | /api/v1/products/delete/{id} | Delete product |


# Working with Persistent storage using file serialization


  
