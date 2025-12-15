import "./AdminDashboard.css"
import { Navbar } from "../../../components/Navbar/Navbar";
import { Sidebar } from "../../../components/Sidebar/Sidebar";

export function AdminDashboard() {
    return (
        <div className="admindasboard-container">
            <Navbar />
            <main>
                <Sidebar />
                <div className="content">
                    <h2 className="page-tag">Dashboard</h2>

                    <div className="boxes">
                        <div className="stat-box users">
                            <p className="stat-title">Total Number of Users</p>
                            <p className="stat-value">--</p>
                        </div>

                        <div className="stat-box books">
                            <p className="stat-title">Total Number of Books</p>
                            <p className="stat-value">--</p>
                        </div>

                        <div className="stat-box loans">
                            <p className="stat-title">Total Number Of Loans</p>
                            <p className="stat-value">--</p>
                        </div>

                        <div className="stat-box holds">
                            <p className="stat-title">Total Number Of Holds</p>
                            <p className="stat-value">--</p>
                        </div>
                    </div>

                </div>
            </main>
        </div>
    );
}
