import { Navigate } from "react-router";

export function ProtectedRoute({ children, roleParam }) {
  const  role  = localStorage.getItem("role")
  if (!role) return <Navigate to="/" />;
  if (role !== roleParam) return <Navigate to="/unauthorized" />;

  return children;
}
