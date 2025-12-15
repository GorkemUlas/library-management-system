import axios from "axios";
import "./LoanBook.css";
import { useNavigate } from "react-router";
import { useEffect } from "react";

export function LoanBook({ hold, triggerMessage }) {
    let holdUser, holdBook = null
    const holdUserUrl = "http://localhost:8080/users/by-id/" + hold.userId
    const holdBookUrl = "http://localhost:8080/books/" + hold.bookId
    const navigate = useNavigate()
    if (!hold) return null;

    useEffect(() => {



       axios.get(holdUserUrl).then(res => {
            console.log(res);
            
        }).catch(err => {
            console.log(err);
            
        }) 

        axios.get(holdBookUrl).then(res => {
            console.log(res);
        }).catch(err => {
            console.log(err);
            
        }) 


    }, [])
    

    return (
        // <div className="borrowbook-container">
        //     <div className="book-info">
        //         <img
        //             src={book.imageUrl}
        //             alt={book.title}
        //             className="book-image"
        //         />

        //         <div className="book-details">
        //             <h3>{book.title}</h3>

        //             <p><strong>Author:</strong> {book.author}</p>
        //             <p><strong>Category:</strong> {book.category}</p>
        //             <p><strong>ISBN:</strong> {book.isbn}</p>

        //             <p>
        //                 <strong>Status:</strong>
        //                 <span className={isAvailable ? "available" : "not-available"}>
        //                     {isAvailable ? " AVAILABLE" : " NOT AVAILABLE"}
        //                 </span>
        //             </p>

        //             <p>
        //                 <strong>Available Copies:</strong> {book.availableCopies}
        //             </p>
        //         </div>
        //     </div>

        //     <div className="borrow-action">
        //         <button
        //             className={`hold-btn ${!isAvailable ? "disabled" : ""}`}
        //             disabled={!isAvailable}
        //             onClick={holdRequestHandler}
        //         >
        //             Place Hold Request
        //         </button>
        //     </div>

        // </div>
        <div></div>
    );
}
