import "./UserBooks.css"
import { Navbar } from "../../../components/Navbar/Navbar"
import { Sidebar } from "../../../components/Sidebar/Sidebar"
import { useState } from "react"
import { AddBook } from "../../../components/AddBook/AddBook"
import { SearchBook } from "../../../components/SearchBook/SearchBook"
import { EditBook } from "../../../components/EditBook/EditBook"
import { BorrowBook } from "../../../components/BorrowBook/BorrowBook"

export function UserBooks({ triggerMessage }) {

    const [form, setForm] = useState(1)
    const [book, setBook] = useState(null)

    function renderForm(form) {
        switch (form) {
            case 1:
                return <SearchBook setForm={setForm} setBook={setBook}/>;
            case 3:
                return <BorrowBook book={book}/>;
            default:
                return null;
        }
    }


    return <div>
        <div className="userbooks-container">
            <Navbar />
            <main>
                <Sidebar />
                <div className="content">
                    <h2 className="page-tag">Books</h2>
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