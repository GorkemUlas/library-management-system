import "./BorrowBook.css";

export function BorrowBook({ book }) {
    if (!book) return null;

    const isAvailable = book.availableCopies > 0;
    const holdRequestHandler = () => {
        
        
        
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
