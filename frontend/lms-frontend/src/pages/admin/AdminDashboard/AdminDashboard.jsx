import "./AdminDashboard.css"
import { Navbar } from "../../../components/Navbar/Navbar";
import { Sidebar } from "../../../components/Sidebar/Sidebar";


export function AdminDashboard() {
    return <div>

        <div className="admindasboard-container">
            <Navbar />
            <main>
                <Sidebar />
                <div className="content">
                    <h1>Admin</h1>
                </div>
                </main>
        </div>

    </div>
}