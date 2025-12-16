import "./AdminDashboard.css";
import { Navbar } from "../../../components/Navbar/Navbar";
import { Sidebar } from "../../../components/Sidebar/Sidebar";
import { useEffect, useState } from "react";
import axios from "axios";

export function AdminDashboard() {

    const [summary, setSummary] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        async function fetchSummary() {
            try {
                const res = await axios.get("http://localhost:8080/admin/summary");
                setSummary(res.data);
            } catch (err) {
                console.error("Admin summary fetch error:", err);
                setError("Failed to load admin dashboard data");
            } finally {
                setLoading(false);
            }
        }

        fetchSummary();
    }, []);

    if (loading) return <div className="loading">Loading...</div>;
    if (error) return <div className="error">{error}</div>;

    return (
        <div className="admindashboard-container">
            <Navbar />
            <main>
                <Sidebar />

                <div className="content">
                    <h2 className="page-tag">Admin Dashboard</h2>

                    {/* ✅ TOP 4 CARDS */}
                    <div className="top-grid">
                        <div className="card blue">
                            <h3>Total Users</h3>
                            <p>{summary.totalUsers}</p>
                        </div>

                        <div className="card blue">
                            <h3>Total Books</h3>
                            <p>{summary.totalBooks}</p>
                        </div>

                        <div className="card blue">
                            <h3>Total Loans</h3>
                            <p>{summary.totalLoans}</p>
                        </div>

                        <div className="card blue">
                            <h3>Total Holds</h3>
                            <p>{summary.totalHolds}</p>
                        </div>
                    </div>

                    {/* ✅ MIDDLE 3 CARDS */}
                    <div className="middle-grid">
                        <div className="card yellow">
                            <h3>Active Loans</h3>
                            <p>{summary.activeLoans}</p>
                        </div>

                        <div className="card red">
                            <h3>Overdue Loans</h3>
                            <p>{summary.overdueLoans}</p>
                        </div>

                        <div className="card green">
                            <h3>Stock Status</h3>
                            <p><strong>Available Copies:</strong> {summary.availableCopies}</p>
                            <p><strong>Out of Stock Books:</strong> {summary.outOfStockBooks}</p>
                            <p><strong>Low Stock Books:</strong> {summary.lowStockBooks}</p>
                        </div>
                    </div>

                    {/* ✅ BOTTOM CARD */}
                    <div className="bottom-grid">
                        <div className="card gray">
                            <h3>Most Borrowed Category</h3>
                            <p>{summary.mostBorrowedCategory}</p>
                        </div>
                    </div>

                </div>
            </main>
        </div>
    );
}
