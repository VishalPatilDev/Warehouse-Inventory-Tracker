
import { useProductContext } from "../context/ProductContext";

const UpdateProductForm = () => {
  const { updateData, setUpdateData, updateProduct } = useProductContext();

  return (
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
        value={updateData.quantity === 0 ? "" : updateData.quantity}
        onChange={(e) =>
          setUpdateData({ ...updateData, quantity: parseInt(e.target.value) })
        }
      />
      <input
        type="number"
        placeholder="Reorder Threshold"
        value={
          updateData.reorderThreshold === 0 ? "" : updateData.reorderThreshold
        }
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
  );
};

export default UpdateProductForm;
