import { useState } from "react";
import "./UserSummary.css"
import { ActionModal } from "../ActionModal/ActionModal";
import axios from "axios";
import { useNavigate } from "react-router";

export function UserSummary({ activeLoans, holds, history, triggerMessage }) {

    const [modalData, setModalData] = useState(null);
    const navigate = useNavigate()

    const handleLoan = (loan) => {
        setModalData({
            type: "LOAN",
            item: loan,
            title: "Manage Loan",
            description: `What would you like to do with "${loan.bookTitle}"?`,
            primaryText: "Return Book",
            secondaryText: "Cancel",
        });

    }

    const handleHold = (hold) => {
        setModalData({
            type: "HOLD",
            item: hold,
            title: "Manage Reservation",
            description: `Do you want to stop your reservation for "${hold.bookTitle}"?`,
            primaryText: "Cancel Hold",
            secondaryText: "Cancel",
        });
    };

    const closeModal = () => setModalData(null);

    const confirmAction = () => {
        if (modalData.type === "HOLD") {
            console.log("Cancel Hold", modalData.item);
            // axios.put("/holds/cancel")
        }

        if (modalData.type === "LOAN") {
            const url = "http://localhost:8080/loans/return/" + modalData.item.loanId

            axios.post(url).then(res => {
                triggerMessage({ text: "Returned Successfully!", type: "success" })
                navigate("/")

            }).catch(err => {
                console.log(err);

            })
        }

        setModalData(null);
    };

    return (
        <div className="usersummary-container">
            <div className="sections">
                {/* ✅ ACTIVE LOANS */}
                <section>
                    <h3 className="first-title">Your Active Loans</h3>
                    {activeLoans.length === 0 && <p>No active loans</p>}
                    <div className="card-list">
                        {activeLoans.map(loan => (
                            <div onClick={() => handleLoan(loan)} key={loan.loanId} className="card">
                                <img src={loan.bookImage} alt="" />
                                <h4 title={loan.bookTitle}>{loan.bookTitle}</h4>
                                <p>Due: {loan.dueDate}</p>
                            </div>
                        ))}
                    </div>
                </section>

                {/* ✅ HOLDS */}
                <section>
                    <h3>Your Reservations</h3>
                    {holds.length === 0 && <p>No reservations</p>}
                    <div className="card-list">
                        {holds
                            .filter(hold => hold.status !== "COMPLETED") // COMPLETED olanları at
                            .map(hold => (
                                <div onClick={() => handleHold(hold)} key={hold.holdId} className="card">
                                    <img src={hold.bookImage} alt="" />
                                    <h4>{hold.bookTitle}</h4>
                                    <p>Status: {hold.status}</p>
                                    <p>Hold Date: {hold.holdDate}</p>
                                </div>
                            ))}

                    </div>
                </section>

                {/* ✅ LOAN HISTORY */}
                <section>
                    <h3>Your Reading History</h3>
                    {history.length === 0 && <p>No past loans</p>}
                    <div className="card-list">
                        {history.map(loan => (
                            <div key={loan.loanId} className="card">
                                <img src={loan.bookImage} alt="" />
                                <h4>{loan.bookTitle}</h4>
                                <p>Borrowed: {loan.issueDate}</p>
                                <p>Returned: {loan.returnDate}</p>
                            </div>
                        ))}
                    </div>
                </section>
            </div>
            <ActionModal
                open={!!modalData}
                title={modalData?.title}
                description={modalData?.description}
                primaryText={modalData?.primaryText}
                secondaryText={modalData?.secondaryText}
                onPrimary={confirmAction}
                onSecondary={closeModal}
            />
        </div>
    )

}