import { useProductContext } from "../context/ProductContext";

const WarehouseFilterForm = () => {
  const { warehouseId, setWarehouseId, fetchByWarehouse } = useProductContext();

  return (
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
  );
};

export default WarehouseFilterForm;
