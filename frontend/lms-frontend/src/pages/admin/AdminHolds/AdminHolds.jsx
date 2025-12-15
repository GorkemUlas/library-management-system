import "./AdminHolds.css"
import { Navbar } from "../../../components/Navbar/Navbar"
import { Sidebar } from "../../../components/Sidebar/Sidebar"
import { useState } from "react"
import { SearchHolds } from "../../../components/SearchHolds/SearchHolds"
import { LoanBook } from "../../../components/LoanBook/LoanBook"

export function AdminHolds({ triggerMessage }) {
    const [form, setForm] = useState(1)
    const [hold, setHold] = useState(null)

    function renderForm(form) {
        switch (form) {
            case 1:
                return <SearchHolds setForm={setForm} setHold={setHold} />;
            case 2:
                return <LoanBook hold={hold} triggerMessage={triggerMessage} />;
            // case 3:
            //     return <EditBook book={book} triggerMessage={triggerMessage} />
            default:
                return null;
        }
    }
    return <div>
        <div className="adminholds-container">
            <Navbar />
            <main>
                <Sidebar />
                <div className="content">
                    <h2 className="page-tag">Holds</h2>
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