// LoginRegisterForm.js
import { useState } from "react";
import "./LoginRegisterForm.css";
import axios from "axios";
import { useNavigate } from "react-router";
import { jwtDecode } from "jwt-decode";
import { login } from "../../auth/Auth";

export function LoginRegisterForm({ heading, description, route, type, triggerMessage }) {
    const url = "http://localhost:8080/auth"
    const [message, setMessage] = useState(null)

    const navigate = useNavigate()

    const [formData, setFormData] = useState({
        name: "",
        email: "",
        password: ""
    });

    const myRegister = (data) => {
        axios.post(`${url}/register`, data)
            .then(function (response) {
                console.log(response);
                triggerMessage({ text: "Registered Succesfully!", type: "success" })
                navigate("/login")
            })
            .catch(function (error) {
                setMessage({ text: error, type: "error" });
                console.log(error);
            });
    }

    const myLogin = (data, callback) => {
        let response = ""
        axios.post(`${url}/login`, data)
            .then(function (response) {
                triggerMessage({ text: "Login Successful!", type: "success" })
                navigate("/login")
                callback(response.data)
            })
            .catch(function (error) {
                triggerMessage({text: error.response.data.error, type: "error"})
            });
    }

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit =  (e) => {
        e.preventDefault();
        if (type === 'register') {
            myRegister(formData)
        } else {
            const data = myLogin(formData, async (token) => {
                const {sub, role} = jwtDecode(token)
                console.log(sub, role);
                
                await login({ sub, role, token });
                navigate("/")
            })


        }
    };

    return (
        <div className="loginregisterform-container">
            <h2>{heading}</h2>
            <p>{description} <a href={"/" + route}>{route}</a></p>

            <form onSubmit={handleSubmit}>
                {type === "register" && (
                    <div className="form-group">
                        <label>Name</label>
                        <input
                            type="text"
                            name="name"
                            value={formData.name}
                            onChange={handleChange}
                            required
                            autoComplete="off"
                        />
                    </div>
                )}

                <div className="form-group">
                    <label>Email</label>
                    <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                        autoComplete="off"
                    />
                </div>

                <div className="form-group">
                    <label>Password</label>
                    <input
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                        autoComplete="off"
                    />
                </div>

                <button type="submit" className="form-btn">
                    {heading}
                </button>
            </form>
        </div>
    );
}
