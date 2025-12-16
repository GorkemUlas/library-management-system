import "./UserDashboard.css";
import { Navbar } from "../../../components/Navbar/Navbar";
import { Sidebar } from "../../../components/Sidebar/Sidebar";
import { useEffect, useState } from "react";
import axios from "axios";
import { UserSummary } from "../../../components/UserSummary/UserSummary";

export function UserDashboard({triggerMessage}) {
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
                    <div className="summary-user">
                        <h3>Welcome, {summary?.name}</h3>
                        <p>Email: {summary?.email}</p>
                        <p>Total Books Read: {summary?.totalLoans}</p>
                        <p>Active Loans: {summary?.activeLoans}</p>
                    </div>

                    <div className="components">
                        <UserSummary
                            activeLoans={activeLoans}
                            holds={holds}
                            history={history}
                            triggerMessage={triggerMessage}
                        />
                    </div>



                </div>
            </main>
        </div>
    );
}
