import React, { useEffect, useState } from "react";
import "./SearchHolds.css";
import axios from "axios";

export function SearchHolds({ setForm, setHold }) {
  const [query, setQuery] = useState("");
  const [holds, setHolds] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchHolds = async (query = "") => {
    try {
      setLoading(true);

      const res = query
        ? await axios.get(`http://localhost:8080/holds/search?query=${query}`)
        : await axios.get(`http://localhost:8080/holds`);

   
    console.log(res.data);
    

      setHolds(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  // 🔹 Debounce Search
  useEffect(() => {
    const timer = setTimeout(() => {
      fetchHolds(query);
    }, 500);

    return () => clearTimeout(timer);
  }, [query]);

  return (
    <div className="searchholds-container">
      <div className="searchholds-frame">
        <input
          className="searchholds-input"
          placeholder="Search holds..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />

        <div className="hold-list">
          {loading ? (
            <div className="loader"></div>
          ) : (
            holds.map((hold, index) => (
              <div
                key={index}
                className="hold-row"
                onClick={() => {
                  setHold(hold);
                  setForm(2);
                }}
              >
                <div className="hold-info">
                  <div className="hold-title">{hold.bookTitle}</div>
                  <div className="hold-date">
                    Requested: {hold.holdDate}
                  </div>
                  <div className={`hold-status ${hold.status?.toLowerCase()}`}>
                    {hold.status}
                  </div>
                </div>
              </div>
            ))
          )}

          {!loading && holds.length === 0 && (
            <div className="empty-text">No holds found</div>
          )}
        </div>
      </div>
    </div>
  );
}
