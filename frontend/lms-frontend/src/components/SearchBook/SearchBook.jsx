import React from "react";
import "./SearchBook.css";

export function SearchBook({ books }) {
  // Default resim URL'si
  const defaultImage = "https://via.placeholder.com/50x75?text=No+Image";

  return (
    <div className="searchbook-container">
      <div className="book-list">
        {books.map((book, index) => (
          <div className="book-row" key={index}>
            <img
              src={book.imageUrl || defaultImage}
              alt={book.title}
              className="book-image"
            />
            <div className="book-info">
              <div className="book-title">{book.title}</div>
              <div className="book-author">{book.author}</div>
              <div className="book-category">{book.category}</div>
              <div className="book-isbn">ISBN: {book.isbn}</div>
              <div className="book-availability">
                {book.available}/{book.total} available
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
