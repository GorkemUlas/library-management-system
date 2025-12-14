import { LoginRegisterForm } from "../../components/LoginRegisterForm/LoginRegisterForm"
import { Navbar } from "../../components/Navbar/Navbar"
import "./Register.css"

export function Register({triggerMessage}) {
    return <div> 
        <div className="register-container">
            <Navbar />
            <main>
                <LoginRegisterForm heading="Register" 
                description="already have an account" 
                route="login"
                type="register"
                triggerMessage={triggerMessage}/>
            </main>
        </div>
    </div>
}