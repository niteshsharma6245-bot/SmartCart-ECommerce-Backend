import { createContext, useContext, useState } from "react";
import api from "../services/api";

const AuthContext = createContext();

export function AuthProvider({ children }) {
    const [token, setToken] = useState(localStorage.getItem("token"));
    const [user, setUser] = useState(
        JSON.parse(localStorage.getItem("user")) || null
    );

    const login = async (email, password) => {
        const response = await api.post("/auth/login", {
            email,
            password,
        });

        const receivedToken = response.data.token;

        localStorage.setItem("token", receivedToken);

        if (response.data.user) {
            localStorage.setItem("user", JSON.stringify(response.data.user));
            setUser(response.data.user);
        }

        setToken(receivedToken);

        return response.data;
    };

    const logout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("user");

        setToken(null);
        setUser(null);
    };

    return (
        <AuthContext.Provider
            value={{
                token,
                user,
                login,
                logout,
                isAuthenticated: !!token,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    return useContext(AuthContext);
}