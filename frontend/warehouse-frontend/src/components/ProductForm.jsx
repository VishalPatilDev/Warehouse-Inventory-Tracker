import { useProductContext } from "../context/ProductContext";

const ProductForm = () => {
  const { newProduct, setNewProduct, addProduct } = useProductContext();

  return (
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
        value={newProduct.quantity === 0 ? '' : newProduct.quantity}
        min={0}
        onChange={(e) => setNewProduct({ ...newProduct, quantity: parseInt(e.target.value) })}
        required
      />
      <input
        type="number"
        placeholder="Reorder Threshold"
        value={newProduct.reorderThreshold === 0 ? '' : newProduct.reorderThreshold}
        onChange={(e) => setNewProduct({ ...newProduct, reorderThreshold: parseInt(e.target.value) })}
        required
      />
      <input
        type="text"
        placeholder="Warehouse ID"
        value={newProduct.warehouseId}
        onChange={(e) => setNewProduct({ ...newProduct, warehouseId: e.target.value })}
        required
      />
      <button type="submit">Add Product</button>
    </form>
  );
};

export default ProductForm;
