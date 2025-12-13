import "./AdminBooks.css"
import { Navbar } from "../../../components/Navbar/Navbar"
import { Sidebar } from "../../../components/Sidebar/Sidebar"
import { useState } from "react"
import { AddBook } from "../../../components/AddBook/AddBook"
import { SearchBook } from "../../../components/SearchBook/SearchBook"

export function AdminBooks() {

    const [forms, setForms] = useState(0)
    const [message, setMessage] = useState(null)

    function renderForm(forms) {
        switch (forms) {
            case 1:
                return <SearchBook />;
            case 2:
                return <AddBook />;
            default:
                return null;
        }
    }


    return <div>
        <div className="adminbooks-container">
            <Navbar />
            <main>
                <Sidebar />
                <div className="content">
                    <h2>Books</h2>
                    <div className="options">
                        <button className="opt" onClick={() => setForms(1)}>🔍</button>
                        <button className="opt" onClick={() => setForms(2)}>➕</button>
                        <button className="opt">🖋️</button>
                        <button className="opt">🗑️</button>
                    </div>
                    <div className="forms">
                        {renderForm(forms)}
                    </div>
                </div>
            </main>
        </div>
    </div>
}