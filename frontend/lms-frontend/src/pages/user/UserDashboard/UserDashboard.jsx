import "./UserDashboard.css";
import { Navbar } from "../../../components/Navbar/Navbar";
import { Sidebar } from "../../../components/Sidebar/Sidebar";
import { useEffect, useState } from "react";
import axios from "axios";

export function UserDashboard() {
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user.userId;

    const [summary, setSummary] = useState(null);
    const [activeLoans, setActiveLoans] = useState([]);
    const [holds, setHolds] = useState([]);
    const [history, setHistory] = useState([]);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        async function fetchData() {
            try {
                const [summaryRes, activeRes, holdsRes, historyRes] = await Promise.all([
                    axios.get(`http://localhost:8080/users/summary/${userId}`),
                    axios.get(`http://localhost:8080/loans/active?userId=${userId}`),
                    axios.get(`http://localhost:8080/holds/user?userId=${userId}`),
                    axios.get(`http://localhost:8080/loans/history?userId=${userId}`)
                ]);

                setSummary(summaryRes.data);
                setActiveLoans(activeRes.data);
                setHolds(holdsRes.data);
                setHistory(historyRes.data);

            } catch (err) {
                console.error("Dashboard fetch error:", err);
                setError("Failed to load dashboard data");
            } finally {
                setLoading(false);
            }
        }

        fetchData();
    }, [userId]);

    if (loading) return <div className="loading">Loading...</div>;
    if (error) return <div className="error">{error}</div>;

    return (
        <div className="userdashboard-container">
            <Navbar />
            <main>
                <Sidebar />

                <div className="content">
                    <h2 className="page-tag">Your Dashboard</h2>

                    {/* ✅ USER SUMMARY */}
                    <section className="summary-section">
                        <h3>Welcome, {summary?.name}</h3>
                        <p>Email: {summary?.email}</p>
                        <p>Total Books Read: {summary?.totalLoans}</p>
                        <p>Active Loans: {summary?.activeLoans}</p>
                    </section>

                    {/* ✅ ACTIVE LOANS */}
                    <section>
                        <h3>Your Active Loans</h3>
                        {activeLoans.length === 0 && <p>No active loans</p>}
                        <div className="card-list">
                            {activeLoans.map(loan => (
                                <div key={loan.loanId} className="card">
                                    <img src={loan.bookImage} alt="" />
                                    <h4>{loan.bookTitle}</h4>
                                    <p>Due: {loan.dueDate}</p>
                                </div>
                            ))}
                        </div>
                    </section>

                    {/* ✅ HOLDS */}
                    <section>
                        <h3>Your Reservations</h3>
                        {holds.length === 0 && <p>No reservations</p>}
                        <div className="card-list">
                            {holds.map(hold => (
                                <div key={hold.holdId} className="card">
                                    <img src={hold.bookImage} alt="" />
                                    <h4>{hold.bookTitle}</h4>
                                    <p>Status: {hold.status}</p>
                                    <p>Hold Date: {hold.holdDate}</p>
                                </div>
                            ))}
                        </div>
                    </section>

                    {/* ✅ LOAN HISTORY */}
                    <section>
                        <h3>Your Reading History</h3>
                        {history.length === 0 && <p>No past loans</p>}
                        <div className="card-list">
                            {history.map(loan => (
                                <div key={loan.loanId} className="card">
                                    <img src={loan.bookImage} alt="" />
                                    <h4>{loan.bookTitle}</h4>
                                    <p>Borrowed: {loan.issueDate}</p>
                                    <p>Returned: {loan.returnDate}</p>
                                </div>
                            ))}
                        </div>
                    </section>

                </div>
            </main>
        </div>
    );
}
