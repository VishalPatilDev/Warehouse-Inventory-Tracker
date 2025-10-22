# 🚚 Warehouse Inventory Tracker

A full-stack inventory management system for tracking products across multiple warehouses. Built with Spring Boot (Java 17) on the backend and React + Vite on the frontend. Supports CRUD operations, persistent storage, low-stock alerts, and warehouse-specific inventory.

## 🛠️ Tech Stack
## 🔙 Backend — Spring Boot

Java 17+

Spring Boot 3.x

Spring Web, DevTools

Lombok (with @Slf4j for logging)

Jakarta Bean Validation (@Valid, @NotBlank, @Min)

Global Exception Handling

Observer Design Pattern (low-stock alert system)

Multi-Warehouse Support

File-based JSON persistence using Jackson

## 🔜 Frontend — React + Vite

React 18+

Vite for fast dev builds

JavaScript (ES6+)

Axios for backend communication

Simple UI for inventory operations

Dynamic table view with live updates

## 🐳 Dockerized Setup

Separate Dockerfiles for backend and frontend

docker-compose.yml for one-command multi-container setup

Exposes:

    - Backend → http://localhost:8080

    - Frontend → http://localhost:5173

## 📁 Project Structure


    Koorier-ProblemStatement/
    ├── backend/
    │   └── warehouse-inventory-tracker/
    │       ├── Dockerfile             # Backend Docker image
    │       ├── src/
    │       └── pom.xml
    │
    ├── frontend/
    │   └── warehouse-frontend/
    │       ├── Dockerfile             # Frontend Docker image
    │       ├── src/
    │       ├── package.json
    │       └── vite.config.js
    │
    ├── docker-compose.yml             # Multi-container orchestration
    └── README.md

## 🚀 Running the Project
### 🧰 Option 1 — Manual Setup (Without Docker)
✅ Prerequisites

Java 17+

Maven 3.x

Node.js 18+

IDE (IntelliJ / VS Code)

🔧 Backend Setup
cd backend/warehouse-inventory-tracker

    mvn clean install
    mvn spring-boot:run


Backend runs on 👉 http://localhost:8080

💻 Frontend Setup
cd frontend/warehouse-frontend

    npm install
    npm run dev


Frontend runs on 👉 http://localhost:5173

### 🐳 Option 2 — Dockerized Setup (Recommended)
⚙️ Prerequisites

Docker Desktop

Docker Compose

▶️ Run with a single command

From project root:

    docker-compose up --build

💥 That’s it!

Backend: http://localhost:8080

Frontend: http://localhost:5173

🧹 Stop containers

    docker-compose down

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

- ProductNotFoundException

- InsufficientStockException

### Global exception handler returns structured error responses.

## 💻 Frontend Overview
Core Functions:

- Fetch All Products

- Add Product

- Receive Shipment

- Fulfill Order (with alerts)

- Update Product

- Delete Product

- Filter by Warehouse

React Hooks Used:

- useState — for form and data management

- useEffect — for dynamic updates

- Axios — for API calls

## Sample Frontend Screens
- <img width="1920" height="1080" alt="Screenshot (120)" src="https://github.com/user-attachments/assets/db97d3d3-3c12-4acb-a022-6c740d772264" />
- <img width="1920" height="1080" alt="Screenshot (121)" src="https://github.com/user-attachments/assets/3de8904e-85c4-42f0-b522-c06b0b0f74d7" />
- <img width="1920" height="1080" alt="Screenshot (122)" src="https://github.com/user-attachments/assets/eee3170d-7b19-4064-be30-7fd410cf0bee" />
- <img width="1920" height="1080" alt="Screenshot (123)" src="https://github.com/user-attachments/assets/c1ac1252-bbcc-4341-9596-374680eb8673" />
- <img width="1920" height="1080" alt="Screenshot (124)" src="https://github.com/user-attachments/assets/7514b153-0856-4a7e-a060-1dea0d5f1718" />
- <img width="1920" height="1080" alt="Screenshot (125)" src="https://github.com/user-attachments/assets/1a0b70b4-955c-46f6-b49e-a7c8389307c5" />
- <img width="1920" height="1080" alt="Screenshot (126)" src="https://github.com/user-attachments/assets/6aa60f99-d85c-4aa2-86c6-ca47dc4d79c9" />
- <img width="1920" height="1080" alt="Screenshot (127)" src="https://github.com/user-attachments/assets/4795597b-0c83-4c2a-b8d4-64741915940c" />
- <img width="1920" height="1080" alt="Screenshot (128)" src="https://github.com/user-attachments/assets/6a825451-9e13-436f-9375-0e348377b454" />










## 📝 Author

Developed by Vishal Patil as part of the Koorier Problem Statement challenge.
