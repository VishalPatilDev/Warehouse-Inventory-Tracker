import "./App.css";
import { ProductProvider, useProductContext } from "./context/ProductContext";
import ShipmentForm from "./components/ShipmentForm";
import OrderForm from "./components/OrderForm";
import UpdateProductForm from "./components/UpdateProductForm";
import DeleteProductForm from "./components/DeleteProductForm";
import WarehouseFilterForm from "./components/WarehouseFilterForm";
import ProductTable from "./components/ProductTable";
import ProductForm from "./components/ProductForm";


function Dashboard() {
  const { fetchProducts, flag } = useProductContext();

  return (
    <>
    <div className="App">
      <h1>🏭 Warehouse Inventory Dashboard</h1>
      <button onClick={fetchProducts}>
        {flag ? "Hide Products" : "Fetch All Products"}
      </button>

      <h3>Add New Product</h3>
      <ProductForm />

      <h3>Receive Shipment</h3>
      <ShipmentForm />

      <h3>Fulfill Order</h3>
      <OrderForm />

      <h3>Update Product</h3>
      <UpdateProductForm />

      <h3>Delete Product</h3>
      <DeleteProductForm />

      <h3>Get Products by Warehouse</h3>
      <WarehouseFilterForm />

      <h2>📦 Product Inventory</h2>
      <ProductTable />
    </div>
    </>
  );
}

export default function App() {
  return (
    <ProductProvider>
      <Dashboard />
    </ProductProvider>
  );
}

