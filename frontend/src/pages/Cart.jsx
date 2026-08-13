import { useEffect, useState } from "react";
import api from "../services/api";

function Cart() {
    const [cart, setCart] = useState(null);
    const [loading, setLoading] = useState(true);

    const fetchCart = async () => {
        try {
            const response = await api.get("/cart");

            setCart(response.data);
        } catch (error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchCart();
    }, []);

    const removeItem = async (id) => {
        try {
            await api.delete(`/cart/items/${id}`);

            fetchCart();
        } catch (error) {
            console.error(error);
        }
    };

    if (loading) {
        return (
            <h2 className="center">
                Loading cart...
            </h2>
        );
    }

    const items = cart?.items || [];

    return (
        <div className="cart-page">

            <div className="page-heading">
                <p>YOUR SHOPPING BAG</p>
                <h1>Your Cart</h1>
            </div>

            {items.length === 0 ? (
                <div className="empty-state">
                    <h2>Your cart is empty.</h2>
                    <p>Add some products to get started.</p>
                </div>
            ) : (
                <>

                    <div className="cart-items">

                        {items.map((item) => (
                            <div
                                className="cart-item"
                                key={item.id}
                            >

                                <div className="cart-product-image">
                                    Product
                                </div>

                                <div className="cart-product-info">

                                    <h3>
                                        {item.product?.name}
                                    </h3>

                                    <p>
                                        ₹{item.product?.price}
                                    </p>

                                    <p>
                                        Quantity: {item.quantity}
                                    </p>

                                </div>

                                <button
                                    className="remove-btn"
                                    onClick={() =>
                                        removeItem(item.id)
                                    }
                                >
                                    Remove
                                </button>

                            </div>
                        ))}

                    </div>

                    <div className="cart-summary">

                        <h2>
                            Total: ₹{cart?.totalAmount || 0}
                        </h2>

                        <button className="checkout-btn">
                            Proceed to Checkout
                        </button>

                    </div>

                </>
            )}

        </div>
    );
}

export default Cart;