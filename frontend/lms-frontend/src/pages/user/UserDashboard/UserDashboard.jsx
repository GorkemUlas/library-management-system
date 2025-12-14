import "./UserDashboard.css"
import { Navbar } from "../../../components/Navbar/Navbar";
import { Sidebar } from "../../../components/Sidebar/Sidebar";


export function UserDashboard() {
    return <div>

        <div className="userdashboard-container">
            <Navbar />
            <main>
                <Sidebar />
                <div className="content">
                    <h1>User</h1>
                </div>
                </main>
        </div>

    </div>
}