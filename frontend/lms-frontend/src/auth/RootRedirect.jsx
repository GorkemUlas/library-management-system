import { Navigate } from "react-router";
import { useAuth } from "./AuthContext";

export default function RootRedirect() {
    const  role  = JSON.parse(localStorage.getItem("role"))
    console.log(role);
    
    // Login değil → Guest
    if (!role) return <Navigate to="/guest" replace />;

    // Role göre
    if (role === "ADMIN") {
        console.log("asd");
        
        return <Navigate to="/admin/dashboard" replace />;
    }

    if (role === "USER") {
        return <Navigate to="/user/dashboard" replace />;
    }

    return <Navigate to="/guest" replace />;
}
