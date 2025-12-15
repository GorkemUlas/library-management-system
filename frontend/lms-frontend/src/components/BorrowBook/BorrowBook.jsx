import axios from "axios";
import "./BorrowBook.css";
import { useNavigate } from "react-router";

export function BorrowBook({ book, triggerMessage }) {
    const navigate = useNavigate()
    const user = JSON.parse(localStorage.getItem("user"))
    if (!book) return null;

    const isAvailable = book.availableCopies > 0;
    const holdRequestHandler = () => {
        const url = "http://localhost:8080/holds"
        console.log(user.userId, book.bookId);

        axios.post(url, {userId: user.userId, bookId: book.bookId})
        .then(res => {
            console.log(res);
            triggerMessage({text: "Requested Successfully!", type:"success"})
            navigate("/")
        })
        .catch((err) => {
            console.log(err);
            triggerMessage({text: err.response.data, type:"error"})
        })
    }

    return (
        <div className="borrowbook-container">

            <div className="book-info">
                <img
                    src={book.imageUrl}
                    alt={book.title}
                    className="book-image"
                />

                <div className="book-details">
                    <h3>{book.title}</h3>

                    <p><strong>Author:</strong> {book.author}</p>
                    <p><strong>Category:</strong> {book.category}</p>
                    <p><strong>ISBN:</strong> {book.isbn}</p>

                    <p>
                        <strong>Status:</strong>
                        <span className={isAvailable ? "available" : "not-available"}>
                            {isAvailable ? " AVAILABLE" : " NOT AVAILABLE"}
                        </span>
                    </p>

                    <p>
                        <strong>Available Copies:</strong> {book.availableCopies}
                    </p>
                </div>
            </div>

            <div className="borrow-action">
                <button
                    className={`hold-btn ${!isAvailable ? "disabled" : ""}`}
                    disabled={!isAvailable}
                    onClick={holdRequestHandler}
                >
                    Place Hold Request
                </button>
            </div>

        </div>
    );
}
