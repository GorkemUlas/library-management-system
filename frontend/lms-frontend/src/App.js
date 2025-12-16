import { BrowserRouter, Routes, Route } from "react-router"
import { AdminBooks } from "./pages/admin/AdminBooks/AdminBooks";
import { AdminDashboard } from "./pages/admin/AdminDashboard/AdminDashboard";
import { Guest } from "./pages/Guest/Guest";
import { Register } from "./pages/Register/Register";
import { Login } from "./pages/Login/Login";
import { Message } from "./components/Message/Message";
import { useState } from "react";
import RootRedirect from "./auth/RootRedirect";
import { UserDashboard } from "./pages/user/UserDashboard/UserDashboard";
import { ProtectedRoute } from "./auth/ProtectedRoute";
import { Unauthorized } from "./pages/Unauthorized/Unauthorized";
import { UserBooks } from "./pages/user/UserBooks/UserBooks";
import { AdminHolds } from "./pages/admin/AdminHolds/AdminHolds";

function App() {

    const triggerMessage = (message) => {
        setMessage(message)
    }
    const [message, setMessage] = useState(null)

    return <div>

        {message && (
            <Message
                text={message.text}
                type={message.type}
                onClose={() => setMessage(null)}
                duration={2000}
            />
        )}

            <BrowserRouter>
                <Routes>

                    <Route path="/" element={<RootRedirect />} />

                    {/* Guest */}
                    <Route path="/guest" element={<Guest />} />

                    {/* Auth */}
                    <Route path="/login" element={<Login triggerMessage={triggerMessage} />} />
                    <Route path="/register" element={<Register triggerMessage={triggerMessage} />} />

                    {/* Admin */}
                    <Route path="/admin/dashboard" element={<ProtectedRoute roleParam="ADMIN"><AdminDashboard /></ProtectedRoute>} />
                    <Route path="/admin/books" element={<ProtectedRoute roleParam="ADMIN"><AdminBooks triggerMessage={triggerMessage} /></ProtectedRoute>} />
                    <Route path="/admin/holds" element={<ProtectedRoute roleParam="ADMIN"><AdminHolds triggerMessage={triggerMessage} /></ProtectedRoute>} />

                    {/* User */}
                    <Route path="/user/dashboard" element={<ProtectedRoute roleParam="USER"><UserDashboard triggerMessage={triggerMessage}/></ProtectedRoute>} />
                    <Route path="/user/books" element={<ProtectedRoute roleParam="USER"><UserBooks triggerMessage={triggerMessage}/></ProtectedRoute>} />

                    <Route path="/unauthorized" element={<Unauthorized />} />

                </Routes>
            </BrowserRouter>

    </div>
}

export default App;