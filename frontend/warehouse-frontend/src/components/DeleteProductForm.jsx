import { useProductContext } from "../context/ProductContext";

const DeleteProductForm = () => {
  const { deleteId, setDeleteId, deleteProduct } = useProductContext();

  return (
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
  );
};

export default DeleteProductForm;
