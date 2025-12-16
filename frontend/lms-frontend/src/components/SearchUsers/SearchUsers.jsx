import React, { useEffect, useState } from "react";
import "./SearchUsers.css";
import axios from "axios";
import { ActionModal } from "../ActionModal/ActionModal";
import { useNavigate } from "react-router";

export function SearchUsers({ setForm, setUser, triggerMessage }) {

    const thisUser = JSON.parse(localStorage.getItem("user"))

    const navigate = useNavigate()

    const [query, setQuery] = useState("");
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(false);
    const EMPTY_MODAL = {
        open: false,
        title: "",
        description: "",
        actions: [],
    };
    const [modal, setModal] = useState(EMPTY_MODAL);


    const deleteUser = (id) => {
        const url = "http://localhost:8080/users/" + id
        console.log("delete, ", id, url);
        axios.delete(url).then(res => {
            triggerMessage({ text: "Deleted Succesfully!", type: "success" })
            navigate("/")
        }).catch(
            err => {
                triggerMessage({ text: err.response.data.message ? err.response.data.message : "Database constraint violation", type: "error" })

            }
        )


    }

    const changeUserRole = (id, oldRole) => {
        const newRole = oldRole === "ADMIN" ? "USER" : "ADMIN"
        const url = "http://localhost:8080/users/" + id + "/role"
        console.log("change role, ", id);

        axios.put(url, null, {
            params: {
                role: newRole
            }
        }).then(res => {
            console.log(res);

            triggerMessage({ text: "Changed Role Succesfully!", type: "success" })
            navigate("/")
        }).catch(
            err => {
                triggerMessage({ text: err.response.data.message ? err.response.data.message : "Database constraint violation", type: "error" })

            }
        )
    }

    const openRoleChangeModal = (user) => {
        const isAdmin = user.role === "ADMIN";

        setModal({
            open: true,
            title: isAdmin ? "Remove admin role" : "Make admin",
            description: isAdmin
                ? `${user.name} will lose admin privileges.`
                : `${user.name} will gain admin privileges.`,
            primaryText: isAdmin ? "Remove admin" : "Make admin",
            secondaryText: "Cancel",
            onPrimary: () => {
                changeUserRole(user.userId, user.role);
                closeModal();
            },
        });
    };


    const openDeleteUserModal = (user) => {
        setModal({
            open: true,
            title: "Delete user",
            description: `${user.name} will be permanently deleted. Are you sure?`,
            primaryText: "Delete",
            secondaryText: "Cancel",
            onPrimary: () => {
                deleteUser(user.userId);
                closeModal();
            },
        });
    };

    const closeModal = () => setModal(EMPTY_MODAL);


    const fetchUsers = async (query = "") => {
        try {
            setLoading(true);

            const res = query
                ? await axios.get(`http://localhost:8080/users`)
                : await axios.get(`http://localhost:8080/users`);
            setUsers(res.data);
        } catch (err) {
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    // 🔹 Debounce Search
    useEffect(() => {
        const timer = setTimeout(() => {
            fetchUsers(query);
        }, 500);

        return () => clearTimeout(timer);
    }, [query]);
    return (
        <div className="searchusers-container">
            <div className="frame">
                <input
                    className="input"
                    placeholder="Search users..."
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                />

                <div className="user-list">
                    {loading ? (
                        <div className="loader"></div>
                    ) : (
                        users.map((user, index) => (
                            <div
                                key={index}
                                className="user-row"
                                onClick={() => {
                                    setUser(user);
                                    //   setForm(2);
                                }}
                            >
                                <div className="user-info">
                                    <div className="user-title">{user.name} {user.userId == thisUser.userId && "(you)"}</div>
                                    <div className="user-date">{user.username}
                                    </div>
                                    <div className={`user-role ${user.role?.toLowerCase()}`}>{user.role}
                                    </div>
                                </div>

                                {
                                    user.userId != thisUser.userId &&
                                    <div><button className="btn delete-btn" onClick={() => openDeleteUserModal(user)}>
                                        Delete
                                    </button>
                                        <button className="btn role-btn" onClick={() => openRoleChangeModal(user)}>
                                            {user.role === "ADMIN" ? "Remove Admin" : "Make Admin"}
                                        </button></div>
                                }
                            </div>
                        ))
                    )}

                    {!loading && users.length === 0 && (
                        <div className="empty-text">No users found</div>
                    )}
                </div>
            </div>
            <ActionModal
                open={modal.open}
                title={modal.title}
                description={modal.description}
                primaryText={modal.primaryText}
                secondaryText={modal.secondaryText}
                onPrimary={modal.onPrimary}
                onSecondary={closeModal}
            />
        </div>
    );
}
