import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function Navbar() {
    const { isAuthenticated, user, logout } = useAuth();

    return (
        <nav className="navbar">
            <div className="navbar-container">

                <Link to="/" className="logo">
                    SmartCart
                </Link>

                <div className="nav-links">
                    <Link to="/">Home</Link>
                    <Link to="/products">Products</Link>

                    {isAuthenticated && (
                        <>
                            <Link to="/cart">Cart</Link>
                            <Link to="/orders">Orders</Link>
                        </>
                    )}

                    {!isAuthenticated ? (
                        <>
                            <Link to="/login">Login</Link>
                            <Link to="/register">Register</Link>
                        </>
                    ) : (
                        <>
              <span className="user-name">
                {user?.name || "User"}
              </span>

                            <button
                                className="logout-btn"
                                onClick={logout}
                            >
                                Logout
                            </button>
                        </>
                    )}
                </div>

            </div>
        </nav>
    );
}

export default Navbar;