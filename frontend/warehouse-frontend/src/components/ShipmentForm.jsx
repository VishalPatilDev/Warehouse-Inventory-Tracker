import { useProductContext } from "../context/ProductContext";

const ShipmentForm = ()=>{
    const { shipment, setShipment, receiveShipment } = useProductContext();
    return (
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
          value={shipment.quantity === 0 ? '' : shipment.quantity}
          onChange={(e) =>
            setShipment({ ...shipment, quantity: parseInt(e.target.value) })
          }
          required
        />
        <button type="submit">Receive Shipment</button>
      </form>
    );
};
export default ShipmentForm