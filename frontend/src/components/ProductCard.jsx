import { Link } from "react-router-dom";

function ProductCard({ product }) {
    return (
        <div className="product-card">

            <div className="product-image">
                <span>Product</span>
            </div>

            <div className="product-info">

                <h3>{product.name}</h3>

                <p className="product-description">
                    {product.description}
                </p>

                <p className="product-price">
                    ₹{product.price}
                </p>

                <Link
                    to={`/products/${product.id}`}
                    className="view-btn"
                >
                    View Product
                </Link>

            </div>

        </div>
    );
}

export default ProductCard;