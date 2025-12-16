import { Navigate } from "react-router";

export default function RootRedirect() {
    const  role  = localStorage.getItem("role")

    // Login değil → Guest
    if (!role) return <Navigate to="/guest" replace />;

    // Role göre
    if (role === "ADMIN") {

        return <Navigate to="/admin/dashboard" replace />;
    }

    if (role === "USER") {
        return <Navigate to="/user/dashboard" replace />;
    }

    return <Navigate to="/guest" replace />;
}
