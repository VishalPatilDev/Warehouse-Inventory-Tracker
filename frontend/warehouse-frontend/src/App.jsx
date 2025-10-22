import { useEffect, useState } from "react";
import axios from "axios";
import "./App.css";

const API_BASE = "http://localhost:8080/api/v1/products";

function App() {
  const [products, setProducts] = useState([]);
  const [flag, setFlag] = useState(false);

  const [newProduct, setNewProduct] = useState({
    name: "",
    quantity: 0,
    reorderThreshold: 0,
    warehouseId: "",
  });

  const [shipment, setShipment] = useState({
    productId: "",
    warehouseId: "",
    quantity: 0,
  });

  const [order, setOrder] = useState({
    productId: "",
    warehouseId: "",
    quantity: 0,
  });

  const [updateData, setUpdateData] = useState({
    id: "",
    name: "",
    quantity: 0,
    reorderThreshold: 0,
    warehouseId: "",
  });

  const [deleteId, setDeleteId] = useState("");
  const [warehouseId, setWarehouseId] = useState("");

  // === FETCH ALL PRODUCTS ===
  const fetchProducts = () => {
    axios
      .get(API_BASE)
      .then((res) => {
        setProducts(flag ? [] : res.data);
        setFlag(!flag);
      })
      .catch((err) => console.error(err));
  };

  // === ADD PRODUCT ===
  const addProduct = (e) => {
    e.preventDefault();
    axios
      .post(API_BASE, newProduct)
      .then((res) => {
        alert("✅ Product Added: " + res.data.name);
        setNewProduct({ name: "", quantity: 0, reorderThreshold: 0, warehouseId: "" });
        fetchProducts();
      })
      .catch((err) => console.error(err));
  };

  // === RECEIVE SHIPMENT ===
  const receiveShipment = (e) => {
    e.preventDefault();
    axios
      .put(`${API_BASE}/shipment`, shipment)
      .then((res) => {
        alert("📦 Shipment Received: " + res.data.name + " (Qty: " + res.data.quantity + ")");
        fetchProducts();
      })
      .catch((err) => console.error(err));
  };

  // === FULFILL ORDER ===
  const fulfillOrder = (e) => {
    e.preventDefault();
    axios
      .put(`${API_BASE}/order`, order)
      .then((res) => {
        if (res.data.alert) alert(res.data.alert);
        else alert("🛒 Order fulfilled for " + res.data.product.name);
        fetchProducts();
      })
      .catch((err) => console.error(err));
  };

  // === UPDATE PRODUCT ===
  const updateProduct = (e) => {
    e.preventDefault();
    axios
      .put(`${API_BASE}/update/${updateData.id}`, updateData)
      .then((res) => {
        alert("📝 Updated Product: " + res.data.name);
        fetchProducts();
      })
      .catch((err) => console.error(err));
  };

  // === DELETE PRODUCT ===
  const deleteProduct = (e) => {
    e.preventDefault();
    axios
      .delete(`${API_BASE}/delete/${deleteId}`)
      .then((res) => {
        alert(res.data || "Deleted successfully");
        fetchProducts();
      })
      .catch((err) => console.error(err));
  };

  // === FETCH BY WAREHOUSE ===
  const fetchByWarehouse = (e) => {
    e.preventDefault();
    axios
      .get(`${API_BASE}/warehouse/${warehouseId}`)
      .then((res) => setProducts(res.data))
      .catch((err) => console.error(err));
  };

  return (
    <div className="App">
      <h1>🏭 Warehouse Inventory Dashboard</h1>

      {/* FETCH BUTTON */}
      <button onClick={fetchProducts}>
        {flag ? "Hide Products" : "Fetch All Products"}
      </button>

      {/* ADD PRODUCT */}
      <h3>Add New Product</h3>
      <form onSubmit={addProduct}>
        <input
          type="text"
          placeholder="Product Name"
          value={newProduct.name}
          onChange={(e) => setNewProduct({ ...newProduct, name: e.target.value })}
          required
        />
        <input
          type="number"
          placeholder="Quantity"
          value={newProduct.quantity}
          onChange={(e) =>
            setNewProduct({ ...newProduct, quantity: parseInt(e.target.value) })
          }
          required
        />
        <input
          type="number"
          placeholder="Reorder Threshold"
          value={newProduct.reorderThreshold}
          onChange={(e) =>
            setNewProduct({
              ...newProduct,
              reorderThreshold: parseInt(e.target.value),
            })
          }
          required
        />
        <input
          type="text"
          placeholder="Warehouse ID"
          value={newProduct.warehouseId}
          onChange={(e) =>
            setNewProduct({ ...newProduct, warehouseId: e.target.value })
          }
          required
        />
        <button type="submit">Add Product</button>
      </form>

      {/* RECEIVE SHIPMENT */}
      <h3>Receive Shipment</h3>
      <form onSubmit={receiveShipment}>
        <input
          type="text"
          placeholder="Product ID"
          value={shipment.productId}
          onChange={(e) => setShipment({ ...shipment, productId: e.target.value })}
          required
        />
        <input
          type="text"
          placeholder="Warehouse ID"
          value={shipment.warehouseId}
          onChange={(e) => setShipment({ ...shipment, warehouseId: e.target.value })}
          required
        />
        <input
          type="number"
          placeholder="Quantity"
          value={shipment.quantity}
          onChange={(e) =>
            setShipment({ ...shipment, quantity: parseInt(e.target.value) })
          }
          required
        />
        <button type="submit">Receive Shipment</button>
      </form>

      {/* FULFILL ORDER */}
      <h3>Fulfill Order</h3>
      <form onSubmit={fulfillOrder}>
        <input
          type="text"
          placeholder="Product ID"
          value={order.productId}
          onChange={(e) => setOrder({ ...order, productId: e.target.value })}
          required
        />
        <input
          type="text"
          placeholder="Warehouse ID"
          value={order.warehouseId}
          onChange={(e) => setOrder({ ...order, warehouseId: e.target.value })}
          required
        />
        <input
          type="number"
          placeholder="Quantity"
          value={order.quantity}
          onChange={(e) => setOrder({ ...order, quantity: parseInt(e.target.value) })}
          required
        />
        <button type="submit">Fulfill Order</button>
      </form>

      {/* UPDATE PRODUCT */}
      <h3>Update Product</h3>
      <form onSubmit={updateProduct}>
        <input
          type="text"
          placeholder="Product ID"
          value={updateData.id}
          onChange={(e) => setUpdateData({ ...updateData, id: e.target.value })}
          required
        />
        <input
          type="text"
          placeholder="Product Name"
          value={updateData.name}
          onChange={(e) => setUpdateData({ ...updateData, name: e.target.value })}
        />
        <input
          type="number"
          placeholder="Quantity"
          value={updateData.quantity}
          onChange={(e) =>
            setUpdateData({ ...updateData, quantity: parseInt(e.target.value) })
          }
        />
        <input
          type="number"
          placeholder="Reorder Threshold"
          value={updateData.reorderThreshold}
          onChange={(e) =>
            setUpdateData({
              ...updateData,
              reorderThreshold: parseInt(e.target.value),
            })
          }
        />
        <input
          type="text"
          placeholder="Warehouse ID"
          value={updateData.warehouseId}
          onChange={(e) =>
            setUpdateData({ ...updateData, warehouseId: e.target.value })
          }
          required
        />
        <button type="submit">Update</button>
      </form>

      {/* DELETE PRODUCT */}
      <h3>Delete Product</h3>
      <form onSubmit={deleteProduct}>
        <input
          type="text"
          placeholder="Product ID"
          value={deleteId}
          onChange={(e) => setDeleteId(e.target.value)}
          required
        />
        <button type="submit">Delete</button>
      </form>

      {/* FETCH BY WAREHOUSE */}
      <h3>Get Products by Warehouse</h3>
      <form onSubmit={fetchByWarehouse}>
        <input
          type="text"
          placeholder="Warehouse ID"
          value={warehouseId}
          onChange={(e) => setWarehouseId(e.target.value)}
          required
        />
        <button type="submit">Fetch</button>
      </form>

      {/* DISPLAY TABLE */}
      <h2>📦 Product Inventory</h2>
      <table border={1} cellSpacing={10} cellPadding={5}>
        <thead>
          <tr>
            <th>Product ID</th>
            <th>Name</th>
            <th>Qty</th>
            <th>Threshold</th>
          </tr>
        </thead>
        <tbody>
          {products.map((p) => (
            <tr key={p.productId}>
              <td>{p.productId}</td>
              <td>{p.name}</td>
              <td>{p.quantity}</td>
              <td>{p.reorderThreshold}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default App;
