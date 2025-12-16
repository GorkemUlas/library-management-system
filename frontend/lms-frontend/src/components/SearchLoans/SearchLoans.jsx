import React, { useEffect, useState } from "react";
import "./SearchLoans.css";
import axios from "axios";

export function SearchLoans({ setForm, setLoan }) {
  const [query, setQuery] = useState("");
  const [loans, setloans] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchloans = async (query = "") => {
    try {
      setLoading(true);

      const res = query
        ? await axios.get(`http://localhost:8080/loans/search?query=${query}`)
        : await axios.get(`http://localhost:8080/loans`);

    // loans BİLİNİYO 
    console.log(res.data);
    

      setloans(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  // 🔹 Debounce Search
  useEffect(() => {
    const timer = setTimeout(() => {
      fetchloans(query);
    }, 500);

    return () => clearTimeout(timer);
  }, [query]);

  return (
    <div className="searchloans-container">
      <div className="frame">
        <input
          className="input"
          placeholder="Search loans..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />

        <div className="loan-list">
          {loading ? (
            <div className="loader"></div>
          ) : (
            loans.map((loan, index) => (
              <div
                key={index}
                className="loan-row"
                onClick={() => {
                  setLoan(loan);
                  // setForm(2);
                }}
              ><img
                  src={loan.bookImage}
                  alt={loan.title}
                  className="book-image"
                />
                <div className="loan-info">
                  <div className="loan-title">{loan.bookTitle}</div>
                  <div className="loan-date">
                    Requested: {loan.issueDate}
                  </div>
                  <div className={`loan-status ${loan.status?.toLowerCase()}`}>
                    {loan.status}
                  </div>
                </div>
              </div>
            ))
          )}

          {!loading && loans.length === 0 && (
            <div className="empty-text">No loans found</div>
          )}
        </div>
      </div>
    </div>
  );
}
