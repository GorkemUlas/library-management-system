// Guest.js
import { useNavigate } from "react-router";
import { Navbar } from "../../components/Navbar/Navbar";
import "./Guest.css";

export function Guest() {
    const navigate = useNavigate()
    return (
        <div>
            <div className="guest-container">
                <Navbar />

                <main>
                    <div><h1>Welcome to Library Management System</h1>
                        <p>Please login or register to continue</p>

                        <div className="guest-buttons">
                            <button className="guest-btn login-btn" onClick={() => navigate("/login")}>Login</button>
                            <button className="guest-btn register-btn" onClick={() => navigate("/register")}>Register</button>
                        </div></div>
                </main>
            </div>
        </div>
    );
}
