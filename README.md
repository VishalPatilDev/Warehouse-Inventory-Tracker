# 🚚 Warehouse Inventory Tracker

A full-stack inventory management system for tracking products across multiple warehouses. Built with Spring Boot (Java 17) on the backend and React + Vite on the frontend. Supports CRUD operations, persistent storage, low-stock alerts, and warehouse-specific inventory.

## 🛠️ Tech Stack
### 🔙 Backend — Spring Boot

Java 17+

Spring Boot 3.x

Spring Web, DevTools

Lombok (with @Slf4j for logging)

Jakarta Bean Validation (@Valid, @NotBlank, @Min)

Global Exception Handling

Observer Design Pattern (low-stock alert system)

Multi-Warehouse Support

File-based JSON persistence using Jackson

### 🔜 Frontend — React + Vite

React 18+

Vite for fast dev builds

JavaScript (ES6+)

Fetch API for backend communication

Simple UI to display product inventory

## 📁 Project Structure

Koorier-ProblemStatement/

├── backend/

│   └── warehouse-inventory-tracker/   # Spring Boot backend

├── frontend/

│   └── warehouse-frontend/            # React + Vite frontend

└── README.md

🚀 Backend Setup Instructions
✅ Prerequisites

Java 17+

Maven 3.x

IDE (IntelliJ, VSCode, Eclipse)

🔧 Build & Run
From inside backend/warehouse-inventory-tracker/
mvn clean install
mvn spring-boot:run


The backend will run at: http://localhost:8080

## 🌐 API Endpoints

All endpoints are prefixed with /api/v1/products

### 🟩 Create Product
POST /api/v1/products

{

    "name": "Laptop",
  
    "quantity": 10,
  
    "reorderThreshold": 3,
  
    "warehouseId": "PUNE-411004"
  
}

### 📦 Receive Shipment (increse quantity)
PUT /api/v1/products/shipment

{

    "productId": "SKU-1d28d303",
    "quantity": 10,
    "warehouseId": "PUNE-411004"
}

### 📤 Fulfill Order (decrease quantity)
PUT /api/v1/products/order

{

    "productId": "SKU-1d28d303",
    "quantity": 18,
    "warehouseId": "PUNE-411004"
}

### 🔔 Response (with low-stock alert)
{
     
      "product": {
          "productId": "SKU-1d28d303",
          "name": "Laptop",
          "quantity": 2,
          "reorderThreshold": 3
      },
      "alert": "Restock Alert: Low stock for Laptop (Remaining: 2)"
}

### 🔍 Get All Products
GET /api/v1/products

### 📍 Get Products by Warehouse
GET /api/v1/products/warehouse/{warehouseId}

### 🔎 Get Product by ID
GET /api/v1/products/{id}

### ✏️ Update Product
PUT /api/v1/products/update/{id}

{
  
    "name": "MacBook",
    "quantity": 100,
    "reorderThreshold": 20,
    "warehouseId": "PUNE-411004"
}

### ❌ Delete Product
DELETE /api/v1/products/delete/{id}

🧠 Key Features
### 🔁 Multi-Warehouse Support

Each warehouse manages its own product map.

All APIs are warehouse-aware using warehouseId.

### 💾 Persistent Storage

Products are saved to a local file warehouse_inventory.json.

Automatically loaded on application startup.

Uses Jackson for serialization.

### 📉 Low Stock Alerts

Observer pattern triggers alert when quantity < reorder threshold.

Alerts returned in response (not just logs) via FulfillmentResponse.

### 🧪 Validation & Error Handling

@Valid, @NotBlank, @Min used to validate incoming data.

Custom exceptions:

ProductNotFoundException

InsufficientStockException

### Global exception handler returns structured error responses.

## 👨‍💻 Frontend Overview
Setup Instructions
cd frontend/warehouse-frontend
npm install
npm run dev


Runs at: http://localhost:5173

Communicates with backend (http://localhost:8080)

Shows all products in a simple table

Button to fetch products from backend

## 📝 Author

Developed by Vishal Patil as part of the Koorier Problem Statement challenge.
