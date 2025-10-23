import { useProductContext } from "../context/ProductContext";

const OrderForm = () => {
    const { order, setOrder, fulfillOrder } = useProductContext();
    return  (
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
          value={order.quantity === 0 ? '' : order.quantity}
          onChange={(e) => setOrder({ ...order, quantity: parseInt(e.target.value) })}
          required
        />
        <button type="submit">Fulfill Order</button>
      </form>
    )
}
export default OrderForm;