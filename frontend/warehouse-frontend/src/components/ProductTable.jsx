import { useProductContext } from "../context/ProductContext";

const ProductTable = () => {
  const { products } = useProductContext();

  return (
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
  );
};

export default ProductTable;
