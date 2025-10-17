# Warehouse-Inventory-Tracker

# 1 - Project setup

- Spring Boot project generated using Spring Initializr
- Dependencies added: Spring Web, Spring Boot DevTools, Lombok, Validation
- added Product model class
- added DTOs (Data Transfer Objects) implemented to handle product data
- POST /api/v1/products - Add a new product to the inventory
- Request body is validated using: @NotBlank, @Min, @Valid
- Endpoint tested successfully using tools Postman
- GET /api/v1/products - Fetch the list of all products - 200 OK with a list of products

# 2 - Progress
- Implemented Observer pattern for event-driven stock alerts.
- Added receiveShipment(add Qty) and fulfillOrder(decrease Qty) APIs.
- Enhanced GlobalExceptionHandler for invalid product and stock errors.
- Verified workflow with sample POST/PUT requests.
- Added getOrderById API and tested with POSTMAN.

  # Crud Operations
- POST | /api/v1/products | Add new product |
- PUT  | /api/v1/products/shipment | Receive shipment |
- PUT  | /api/v1/products/order | Fulfill order |
- GET  | /api/v1/products | Get all products |
- GET  | /api/v1/products/{id} | Get product by ID |
- PUT  | /api/v1/products/update/{id} | Update product |
- DELETE | /api/v1/products/delete/{id} | Delete product |


  
