import "./AdminUsers.css"
import { Navbar } from "../../../components/Navbar/Navbar"
import { Sidebar } from "../../../components/Sidebar/Sidebar"
import { useState } from "react"
import { SearchUsers } from "../../../components/SearchUsers/SearchUsers"

export function AdminUsers({ triggerMessage }) {
    const [form, setForm] = useState(1)
    const [user, setUser] = useState(null)

    function renderForm(form) {
        switch (form) {
            case 1:
                return <SearchUsers setForm={setForm} setUser={setUser} triggerMessage={triggerMessage}/>;
            // case 2:
            //     return <LoanBook hold={hold} triggerMessage={triggerMessage} />;
            // case 3:
            //     return <EditBook book={book} triggerMessage={triggerMessage} />
            default:
                return null;
        }
    }
    return <div>
        <div className="adminusers-container">
            <Navbar />
            <main>
                <Sidebar />
                <div className="content">
                    <h2 className="page-tag">Users</h2>
                    <div className="options">
                        <button className="opt" onClick={() => setForm(1)}>🔍</button>
                    </div>
                    <div className="forms">
                        {renderForm(form)}
                    </div>
                </div>
            </main>
        </div>
    </div>
}