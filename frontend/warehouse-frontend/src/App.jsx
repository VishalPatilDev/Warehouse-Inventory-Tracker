import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
const API_BASE = 'http://localhost:8080/api/v1/products';

function App() {
  const [products, setProducts] = useState([]);
  const [flag, setFlag] = useState(false);
  const [newProduct, setNewProduct] = useState({
    name: '',
    quantity: 0,
    reorderThreshold: 0
  });

  const addProducts = async () => {
    try {
      const res = await fetch(API_BASE, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newProduct)
      })
      setNewProduct({ name: '', quantity: 0, reorderThreshold: 0 });
    } catch (err) {
      console.log(err);
    }
  }

  const fetchProducts = async () => {
    if (!flag) {
      try {
        const res = await fetch(API_BASE);
        const data = await res.json();
        setProducts(data);
        setFlag(true)
      } catch (err) {
        console.log(err);
      }
    }
    else {
      setProducts([]);
      setFlag(false);
    }

  };

  return (
    <>
      <div>
        <button onClick={fetchProducts}>Fetch</button>
        <form action="submit" onSubmit={addProducts}>
          <div>
            <label htmlFor="">Product Name: </label>
            <input type="text" value={newProduct.name}
              onChange={(e) => setNewProduct({ ...newProduct, name: e.target.value })}
            />

          </div>
          <div>
            <label htmlFor="">Qty: </label>
            <input type="number" value={newProduct.quantity}
              onChange={(e) => setNewProduct({ ...newProduct, quantity: parseInt(e.target.value) })}
            />

          </div>
          <div>
            <label htmlFor="">ReorderThreshold: </label>
            <input type="number" value={newProduct.reorderThreshold}
              onChange={(e) => setNewProduct({ ...newProduct, reorderThreshold: parseInt(e.target.value) })}
            />

          </div>

          <div>
            <button type='submit'>Add Product</button>
          </div>
        </form>

        <br />
        <br />
        <table border={1} cellSpacing={10} cellPadding={5}>
          <thead>
            <tr>
              <th>Product ID</th>
              <th>Product Name</th>
              <th>Qty</th>
              <th>Reorder Threshold</th>
            </tr>
          </thead>
          <tbody>
            {products.map((product) => (
              <tr key={product.productId}>
                <td>{product.productId}</td>
                <td>{product.name}</td>
                <td>{product.quantity}</td>
                <td>{product.reorderThreshold}</td>
              </tr>
            ))}
          </tbody>

        </table>
      </div>
    </>
  )
}
export default App
