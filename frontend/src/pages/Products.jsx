import { useEffect, useState } from "react";
import api from "../services/api";
import ProductCard from "../components/ProductCard";

function Products() {
    const [products, setProducts] = useState([]);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await api.get("/products");

                const data = response.data;

                setProducts(
                    Array.isArray(data)
                        ? data
                        : data.content || []
                );
            } catch (error) {
                setError("Unable to load products.");
            } finally {
                setLoading(false);
            }
        };

        fetchProducts();
    }, []);

    if (loading) {
        return (
            <h2 className="center">
                Loading products...
            </h2>
        );
    }

    if (error) {
        return (
            <h2 className="center error-message">
                {error}
            </h2>
        );
    }

    return (
        <div className="products-page">

            <div className="page-heading">
                <p>EXPLORE OUR COLLECTION</p>
                <h1>Our Products</h1>
            </div>

            <div className="products-grid">

                {products.length === 0 ? (
                    <p>No products available.</p>
                ) : (
                    products.map((product) => (
                        <ProductCard
                            key={product.id}
                            product={product}
                        />
                    ))
                )}

            </div>

        </div>
    );
}

export default Products;