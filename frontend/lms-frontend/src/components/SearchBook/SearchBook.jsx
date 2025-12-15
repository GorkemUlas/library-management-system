import React, { useEffect, useState } from "react";
import "./SearchBook.css";
import axios from "axios";

export function SearchBook({ setForm, setBook }) {
  const defaultImage = "https://www.pngarts.com/files/8/Hardcover-Book-Cover-Transparent-Images.png";

  const [query, setQuery] = useState("");
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(false);

  async function fetchBooks(query = "") {
    try {
      let res = ""
      setLoading(true);
      if (query) {
        res = await axios.get(`http://localhost:8080/books/search/title?${query}`);
      } else {
        res = await axios.get(`http://localhost:8080/books`);
      }
      setBooks(res.data);
      console.log(res.data);

    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  }
  // 🔹 Debounce + Search
  useEffect(() => {
    const timer = setTimeout(() => {
      setLoading(true);

      fetchBooks(query)

      const filtered = books.filter((book) =>
        book.title.toLowerCase().includes(query.toLowerCase())
      );

      setBooks(filtered);
      setLoading(false);
    }, 500);

    return () => clearTimeout(timer);
  }, [query]);

  return (
    <div className="searchbook-container">
      <div className="searchbook-frame">
        <input
          className="searchbook-input"
          placeholder="Search books..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />
        <div className="book-list">
          {loading ? (
            <div className="loader"></div>
          ) : (
            books.map((book, index) => (
              <div onClick={() => {
                setBook(book)
                setForm(3)
              }} className="book-row" key={index}>
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
                    {book.availableCopies}/{book.totalCopies} {book.status}
                  </div>
                </div>
              </div>
            ))
          )}

          {!loading && books.length === 0 && (
            <div className="empty-text">No books found</div>
          )}
        </div>
      </div>
    </div>
  );
}
