import { LoginRegisterForm } from "../../components/LoginRegisterForm/LoginRegisterForm"
import { Navbar } from "../../components/Navbar/Navbar"
import "./Login.css"

export function Login({triggerMessage}) {
    return <div> 
        <div className="login-container">
            <Navbar />
            <main>
                <LoginRegisterForm heading="Login" 
                description="dont you have an account" 
                route="register"
                type="login"
                triggerMessage={triggerMessage}/>
            </main>
        </div>
    </div>
}