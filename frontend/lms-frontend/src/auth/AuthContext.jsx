import { createContext, useContext, useState } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    const storedUser = localStorage.getItem("user");
    return storedUser ? JSON.parse(storedUser) : null;
  }); // { email, role, token }

  const login = (userData) => {
    setUser(userData);
    localStorage.setItem("user", JSON.stringify(userData.sub));
    localStorage.setItem("role", JSON.stringify(userData.role));
    localStorage.setItem("token", JSON.stringify(userData.token));
  };
  const logout = () => setUser(null);

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
