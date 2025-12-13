import "./Sidebar.css"
import { MdSpaceDashboard } from "react-icons/md";
import { ImBooks } from "react-icons/im";
import { HiUsers } from "react-icons/hi";
import { MdSwapHoriz } from "react-icons/md";
import { MdPauseCircleOutline } from "react-icons/md";
import { IoIosSettings } from "react-icons/io";
import { TiThMenu } from "react-icons/ti";
import { IoReturnUpBack } from "react-icons/io5";
import { useState } from "react";
import clsx from "clsx";

export function Sidebar() {

    const [sidebarBtn, setSidebarBtn] = useState(true)

    const sidebarBtnHandler = () => setSidebarBtn(!sidebarBtn)

    return <div>
        <div className={clsx("sidebar-container", {
            "sidebar-broad": sidebarBtn
        })}>
            <ul>
                <li className="menu-btn">
                    <div className="icon" onClick={sidebarBtnHandler}>{sidebarBtn ? <IoReturnUpBack /> : <TiThMenu />}</div>
                    <div> {sidebarBtn && "Admin"}</div>
                </li>

                <a href="dashboard">
                    <li className="item">
                        <div className="icon"><MdSpaceDashboard /></div>
                        <div>{sidebarBtn && "Dashboard"}</div>
                    </li>
                </a>
                <a href="books">
                    <li className="item">
                        <div className="icon"><ImBooks /></div>
                        <div>{sidebarBtn && "Books"}</div>
                    </li></a>
                <a href="users">
                    <li className="item">
                        <div className="icon"><HiUsers /></div>
                        <div>{sidebarBtn && "Users"}</div>
                    </li></a>
                <a href="loans">
                    <li className="item">
                        <div className="icon"><MdSwapHoriz /></div>
                        <div>{sidebarBtn && "Loans"}</div>
                    </li></a>
                <a href="holds">
                    <li className="item">
                        <div className="icon"><MdPauseCircleOutline /></div>
                        <div>{sidebarBtn && "Holds"}</div>
                    </li></a>
                <a href="settings">
                    <li className="item">
                        <div className="icon"><IoIosSettings /></div>
                        <div>{sidebarBtn && "Settings"}</div>
                    </li></a>

            </ul>
        </div>
    </div>
}