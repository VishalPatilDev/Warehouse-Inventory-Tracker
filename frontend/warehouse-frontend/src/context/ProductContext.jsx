import { createContext, useState, useContext } from "react";
import axios from "axios";

const ProductContext = createContext();
const API_BASE = "http://localhost:8080/api/v1/products";

export const ProductProvider = ({ children }) => {
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


    const fetchProducts = () => {
        axios
            .get(API_BASE)
            .then((res) => {
                setProducts(flag ? [] : res.data);
                setFlag(!flag);
            })
            .catch(console.error);
    };

    const addProduct = (e) => {
        e.preventDefault();
        axios
            .post(API_BASE, newProduct)
            .then((res) => {
                alert("✅ Product Added: " + res.data.name);
                setNewProduct({ name: "", quantity: 0, reorderThreshold: 0, warehouseId: "" });
                fetchProducts();
            })
            .catch(console.error);
    };

    const receiveShipment = (e) => {
        e.preventDefault();
        axios
            .put(`${API_BASE}/shipment`, shipment)
            .then((res) => {
                alert("📦 Shipment Received: " + res.data.name + " (Qty: " + res.data.quantity + ")");
                fetchProducts();
            })
            .catch((error) => {
                console.error(error);
                if (error.response && error.response.data && error.response.data.message) {
                    alert("Error: " + error.response.data.message);
                } else {
                    alert("Error receiving shipment. Please check Warehouse ID, Product ID and try again.");
                }
            });
    };

    const fulfillOrder = (e) => {
        e.preventDefault();
        axios
            .put(`${API_BASE}/order`, order)
            .then((res) => {
                if (res.data.alert) alert(res.data.alert);
                else alert("🛒 Order fulfilled for " + res.data.product.name);
                fetchProducts();
            })
            .catch((error) => {
                console.error(error);
                if (error.response && error.response.data && error.response.data.message) {
                    alert("Error: " + error.response.data.message);
                } else {
                    alert("Error receiving shipment. Please check Warehouse ID, Product ID and try again.");
                }
            });
    };

    const updateProduct = (e) => {
        e.preventDefault();
        axios
            .put(`${API_BASE}/update/${updateData.id}`, updateData)
            .then((res) => {
                alert("📝 Updated Product: " + res.data.name);
                fetchProducts();
            })
            .catch((error) => {
                console.error(error);
                if (error.response && error.response.data && error.response.data.message) {
                    alert("Error: " + error.response.data.message);
                } else {
                    alert("Error receiving shipment. Please check Warehouse ID, Product ID and try again.");
                }
            });
    };

    const deleteProduct = (e) => {
        e.preventDefault();
        axios
            .delete(`${API_BASE}/delete/${deleteId}`)
            .then((res) => {
                alert(res.data || "Deleted successfully");
                fetchProducts();
            })
            .catch((error) => {
                console.error(error);
                if (error.response && error.response.data && error.response.data.message) {
                    alert("Error: " + error.response.data.message);
                } else {
                    alert("Product ID Not Found !");
                }
            });
    };

    const fetchByWarehouse = (e) => {
        e.preventDefault();
        axios
            .get(`${API_BASE}/warehouse/${warehouseId}`)
            .then((res) => setProducts(res.data))
            .catch((error) => {
                console.error(error);
                if (error.response && error.response.data && error.response.data.message) {
                    alert("Error: " + error.response.data.message);
                } else {
                    alert("WarehouseID Not Found !");
                }
            });
    };

    return (
        <ProductContext.Provider
            value={{
                products,
                flag,
                fetchProducts,
                newProduct,
                setNewProduct,
                addProduct,
                shipment,
                setShipment,
                receiveShipment,
                order,
                setOrder,
                fulfillOrder,
                updateData,
                setUpdateData,
                updateProduct,
                deleteId,
                setDeleteId,
                deleteProduct,
                warehouseId,
                setWarehouseId,
                fetchByWarehouse,
            }}
        >
            {children}
        </ProductContext.Provider>
    );
};

export const useProductContext = () => useContext(ProductContext);
