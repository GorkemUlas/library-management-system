import "./AdminLoans.css"
import { Navbar } from "../../../components/Navbar/Navbar"
import { Sidebar } from "../../../components/Sidebar/Sidebar"
import { useState } from "react"
import { SearchHolds } from "../../../components/SearchHolds/SearchHolds"
import { LoanBook } from "../../../components/LoanBook/LoanBook"
import { SearchLoans } from "../../../components/SearchLoans/SearchLoans"

export function AdminLoans({ triggerMessage }) {
    const [form, setForm] = useState(1)
    const [loan, setLoan] = useState(null)

    function renderForm(form) {
        switch (form) {
            case 1:
                return <SearchLoans setForm={setForm} setLoan={setLoan} />;
            // case 2:
            //     return <LoanBook hold={hold} triggerMessage={triggerMessage} />;
            default:
                return null;
        }
    }
    return <div>
        <div className="adminloans-container">
            <Navbar />
            <main>
                <Sidebar />
                <div className="content">
                    <h2 className="page-tag">Loans</h2>
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