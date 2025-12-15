import { useNavigate } from "react-router";
import "./EditBook.css"
import { useState } from "react";
import axios from "axios";

export function EditBook({ book, triggerMessage }) {
    const navigate = useNavigate()

    const [form, setForm] = useState({
        title: book.title,
        author: book.author,
        category: book.category,
        totalCopies: book.totalCopies,
        isbn: book.isbn,
        imageUrl: book.imageUrl
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        console.log(book);

        const url = "http://localhost:8080/books/" + book.bookId
        e.preventDefault();

        axios.put(url, form)
            .then(res => {
                navigate("/")
                triggerMessage({ text: "Saved Succesfully!", type: "success" })
            })
            .catch(err => {
                triggerMessage({ text: err.response.data.error, type: "error" })
            })

    };


    const deleteHandler = () => {
        const url = "http://localhost:8080/books/" + book.bookId
        axios.delete(url)
        .then((res) => {
            triggerMessage({text: "Deleted Succesfully!", type: "success"})
            navigate("/")
        })
        .catch(err => {
            triggerMessage({ text: err.response.data.error, type: "error" })
        })
    }

    return (
        <div className="editbook-container">
            <h2>Edit Book</h2>

            <form className="editbook-form" onSubmit={handleSubmit}>
                <input
                    name="title"
                    placeholder="Title"
                    value={form.title}
                    onChange={handleChange}
                    required
                />

                <input
                    name="author"
                    placeholder="Author"
                    value={form.author}
                    onChange={handleChange}
                    required
                />

                <input
                    name="category"
                    placeholder="Category"
                    value={form.category}
                    onChange={handleChange}
                />

                <input
                    type="number"
                    name="totalCopies"
                    placeholder="Total Copies"
                    value={form.totalCopies}
                    onChange={handleChange}
                    min="1"
                    required
                />

                <input
                    name="isbn"
                    placeholder="ISBN"
                    value={form.isbn}
                    onChange={handleChange}
                />

                <input
                    name="imageUrl"
                    placeholder="Image URL"
                    value={form.imageUrl}
                    onChange={handleChange}
                />

                <button type="submit" className="savebook-btn">Save Book</button>
                <button onClick={deleteHandler} className="deletebook-btn">Delete Book</button>
            </form>
        </div>
    );
}