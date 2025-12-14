import { Navbar } from "../../components/Navbar/Navbar";
import "./Unauthorized.css";

export function Unauthorized() {
    return (
        <div className="unauthorized-container">
            <Navbar />
            <main>
                <div className="unauthorized-card"><img
                    className="unauthorized-image"
                    src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQhyZTGBviTZEF1SzCd4-H2XcrfEWYDL5ucJg&s"
                    alt="Unauthorized meme"
                />

                    <h1 className="unauthorized-title">403 - Unauthorized</h1>
                    <p className="unauthorized-text">
                        You don’t have permission to access this page.
                    </p></div>
            </main>
        </div>
    );
}
