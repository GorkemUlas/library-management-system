import { useState } from "react";
import "./AddBook.css";
import { Message } from "../Message/Message";

export function AddBook() {
    const [message, setMessage] = useState(null)

    const [form, setForm] = useState({
        title: "",
        author: "",
        category: "",
        totalCopies: "",
        isbn: "",
        imageUrl: ""
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        // şimdilik sadece log
        console.log(form);

        setMessage({ text: "Book added successfully!", type: "success" });

        // ileride:
        // axios.post("/admin/books", form)
    };

    return (
        <div className="addbook-container">
            {message && (
                <Message
                    text={message.text}
                    type={message.type}
                    onClose={() => setMessage(null)}
                    duration={2000}
                />
            )}
            <h2>Add Book</h2>

            <form className="addbook-form" onSubmit={handleSubmit}>
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

                <button type="submit">Add Book</button>
            </form>
        </div>
    );
}
