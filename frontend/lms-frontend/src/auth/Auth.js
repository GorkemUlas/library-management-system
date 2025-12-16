import axios from "axios";


export const login = async (userData, navigate) => {
    const url = "http://localhost:8080/users/by-email"
    await axios.get(url, {
        params: {
            email: userData.sub
        }
    })
    .then(res => {
            localStorage.setItem("user", JSON.stringify(res.data));
            localStorage.setItem("role", userData.role);
            localStorage.setItem("token", userData.token);
        })
    .catch(err => console.error(err));
};

export const logout = () => {
    localStorage.clear()
};