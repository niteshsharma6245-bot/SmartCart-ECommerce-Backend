import { useEffect, useState } from "react";
import api from "../services/api";

function Orders() {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const response = await api.get("/orders");

                const data = response.data;

                setOrders(
                    Array.isArray(data)
                        ? data
                        : data.content || []
                );
            } catch (error) {
                console.error(error);
            } finally {
                setLoading(false);
            }
        };

        fetchOrders();
    }, []);

    if (loading) {
        return (
            <h2 className="center">
                Loading orders...
            </h2>
        );
    }

    return (
        <div className="orders-page">

            <div className="page-heading">
                <p>ORDER HISTORY</p>
                <h1>My Orders</h1>
            </div>

            {orders.length === 0 ? (
                <div className="empty-state">
                    <h2>No orders found.</h2>
                </div>
            ) : (
                <div className="orders-list">

                    {orders.map((order) => (
                        <div
                            className="order-card"
                            key={order.id}
                        >

                            <div>
                                <h3>
                                    Order #{order.id}
                                </h3>

                                <p>
                                    Status: {order.status}
                                </p>
                            </div>

                            <strong>
                                ₹{order.totalAmount}
                            </strong>

                        </div>
                    ))}

                </div>
            )}

        </div>
    );
}

export default Orders;