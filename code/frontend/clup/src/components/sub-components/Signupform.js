import React, { useState } from 'react';

const Signupform = (props) => {

    const [nextBool, setNextBool] = useState(false);
    const [username, setUsername] = useState(null);
    const [email, setEmail] = useState(null);
    const [phonenumber, setPhonenumber] = useState(null);
    const [password, setPassword] = useState(null);
    const [repeatPassword, setRepeatPassword] = useState(null);

    const checkValidity = (event) => {
        if (document.getElementById("signupForm").reportValidity()) {
            setNextBool(true);
            setUsername(document.getElementById("usernameInput").value);
            setEmail(document.getElementById("emailInput").value);
            setPhonenumber(document.getElementById("phonenumberInput").value);
        }
    }

    const submit = () => {
        var password = document.getElementById("passwordInput");
        var confirm_password = document.getElementById("repeatPasswordInput");

        if (password.value !== confirm_password.value) {
            confirm_password.setCustomValidity("Passwords Don't Match");
        } else {
            confirm_password.setCustomValidity('');
            console.log("TODO, submit form..");
        }
    }

    const renderCorrectInputContainer = () => {
        if (!nextBool) {
            return (
                <div className="inputContainer">
                    <h3>Create a new account</h3>
                    <div className="flexColumnCenter">
                        <label className="labelSignup">Username</label>
                        <input id="usernameInput" type="text" placeholder="Username" name="username" required />
                    </div>
                    <div className="flexColumnCenter">
                        <label className="labelSignup">Email</label>
                        <input id="emailInput" type="email" placeholder="email@example.com" name="email" required />
                    </div>
                    <div className="flexColumnCenter">
                        <label className="labelSignup">Phone number</label>
                        <input id="phonenumberInput" type='number' placeholder="+39 0123456789" name="phoneNumber" required />
                    </div>
                    <button className="activeButton" type="submit" onClick={checkValidity}>Next</button>
                </div>
            );
        }
        else {
            return (
                <div className="inputContainer">
                    <h3>Create a password</h3>
                    <div className="flexColumnCenter">
                        <label className="labelSignup">Password</label>
                        <input id="passwordInput" type="password" placeholder="Password" name="password" required />
                    </div>
                    <div className="flexColumnCenter">
                        <label className="labelSignup">Repeat password</label>
                        <input id="repeatPasswordInput" type="password" placeholder="Repeat password" name="repeatPassword" required />
                    </div>
                    <button className="activeButton" onClick={submit}>Signup</button>
                </div>
            );
        }
    }

    if (props.open) {
        return (
            <div className="loginFormContainer">
                <form id="signupForm" /* action=TODO  method="post" */ autoComplete="off">
                    {renderCorrectInputContainer()}
                </form>
            </div>
        );
    }
    else {
        return (
            <div className="loginFormContainer">
                <form id="signupForm" /* action=TODO  method="post" */ autoComplete="off">
                    <h3> Don't have an account?</h3>
                    <button className="activeButton" onClick={props.renderSignup}>Signup</button>
                </form>
            </div>
        );
    }
}

export default Signupform;