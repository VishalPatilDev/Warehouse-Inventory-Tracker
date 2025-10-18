import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
const API_BASE = 'http://localhost:8080/api/v1/products';

function App() {
  const [products, setProducts] = useState([]);

  const addProducts = async () =>{
    try{
      
    }catch(err){
      console.log(err);
    }
  }

  const fetchProducts = async () => {
    try {
      const res = await fetch(API_BASE);
      const data = await res.json();
      setProducts(data);
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <>
      <div>
        <button onClick={fetchProducts}>Fetch</button>
        <button >Add Product</button>
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
