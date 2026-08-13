import { Link } from "react-router-dom";

function Home() {
    return (
        <div>

            <section className="hero-section">

                <div className="hero-content">

                    <p className="hero-label">
                        WELCOME TO SMARTCART
                    </p>

                    <h1>
                        Everything You Need,
                        <span> In One Cart.</span>
                    </h1>

                    <p>
                        Discover products, manage your cart,
                        and place orders with ease.
                    </p>

                    <Link
                        to="/products"
                        className="hero-btn"
                    >
                        Shop Now
                    </Link>

                </div>

            </section>

            <section className="features">

                <div className="feature">
                    <h3>Wide Selection</h3>
                    <p>
                        Explore a variety of products in one place.
                    </p>
                </div>

                <div className="feature">
                    <h3>Secure Shopping</h3>
                    <p>
                        Secure authentication keeps your account protected.
                    </p>
                </div>

                <div className="feature">
                    <h3>Easy Checkout</h3>
                    <p>
                        Manage your cart and place orders easily.
                    </p>
                </div>

            </section>

        </div>
    );
}

export default Home;