import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../services/api";

function ProductDetails() {
    const { id } = useParams();

    const [product, setProduct] = useState(null);
    const [quantity, setQuantity] = useState(1);

    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchProduct = async () => {
            try {
                const response = await api.get(`/products/${id}`);

                setProduct(response.data);
            } catch (error) {
                setError("Unable to load product.");
            }
        };

        fetchProduct();
    }, [id]);

    const addToCart = async () => {
        try {
            setMessage("");
            setError("");

            await api.post("/cart/items", {
                productId: id,
                quantity,
            });

            setMessage("Product added to cart.");
        } catch (error) {
            setError(
                error.response?.data?.message ||
                "Unable to add product to cart."
            );
        }
    };

    if (error && !product) {
        return (
            <h2 className="center error-message">
                {error}
            </h2>
        );
    }

    if (!product) {
        return (
            <h2 className="center">
                Loading...
            </h2>
        );
    }

    return (
        <div className="product-details">

            <div className="details-image">
                Product Image
            </div>

            <div className="details-content">

                <p className="product-category">
                    SMARTCART PRODUCT
                </p>

                <h1>{product.name}</h1>

                <p className="details-description">
                    {product.description}
                </p>

                <h2>
                    ₹{product.price}
                </h2>

                <div className="quantity-section">

                    <label>Quantity</label>

                    <input
                        type="number"
                        min="1"
                        value={quantity}
                        onChange={(event) =>
                            setQuantity(Number(event.target.value))
                        }
                    />

                </div>

                <button
                    className="add-cart-btn"
                    onClick={addToCart}
                >
                    Add to Cart
                </button>

                {message && (
                    <p className="success-message">
                        {message}
                    </p>
                )}

                {error && (
                    <p className="error-message">
                        {error}
                    </p>
                )}

            </div>

        </div>
    );
}

export default ProductDetails;