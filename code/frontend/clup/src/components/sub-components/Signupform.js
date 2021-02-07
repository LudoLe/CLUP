import React, { useState } from 'react';
import { axiosPOST, setUsernameLocal } from '../../utils/httpRequest';

const Signupform = (props) => {

    const [state, setState] = useState({
        nextBool: false,
        username: "",
        password: "",
        repeatPassword: "",
        email: "",
        phonenumber: "",
        shopOwner: false
    });

    const handleChange = (e) => {
        const { id, value } = e.target
        setState(prevState => ({
            ...prevState,
            [id]: value
        }))
    }

    const checkValidity = (e) => {
        if (document.getElementById("signupForm").reportValidity()) {
            setState(prevState => ({
                ...prevState,
                nextBool: true
            }))
        }
    }

    const handleSignupSubmit = (e) => {
        e.preventDefault();
        if (state.nextBool !== true) {
            return;
        }
        if (document.getElementById("signupForm").reportValidity()) {
            if (state.password === state.repeatPassword) {
                //document.getElementById("repeatPassword").setCustomValidity('');
                sendSignupToServer();
            }
            else {
                //TODO: 
                //this doesn't work
                //document.getElementById("repeatPassword").setCustomValidity("passwords don't match!");
            }
        }
    }

    const sendSignupToServer = () => {
        const payload = {
            "username": state.username,
            "password": state.password,
            "password2": state.repeatPassword,
            "email": state.email,
            "phoneNumber": state.phonenumber,
            "isManager": state.shopOwner
        }
        const headers = {

        }
        const onOk = (response) => {
            setUsernameLocal(response.data.username);
            console.log("username setted in local storage: " + response.data.username);
            redirectToHome();
        }
        axiosPOST("AMW", "/registration", payload, headers, onOk, null, null, false, true);
    }

    const redirectToHome = () => {
        window.location.href = "/Home";
    }

    return (
        <div className="loginFormContainer">
            {props.open ?
                <form onSubmit={handleSignupSubmit} id="signupForm" autoComplete="off">
                    {!state.nextBool ?
                        <div className="inputContainer">
                            <h3>Create a new account</h3>
                            <div className="flexColumnCenter">
                                <label className="labelSignup">Username</label>
                                <input onChange={handleChange}
                                    value={state.username}
                                    id="username"
                                    type="text" placeholder="Username" name="username" required />
                            </div>
                            <div className="flexColumnCenter">
                                <label className="labelSignup">Email</label>
                                <input onChange={handleChange}
                                    value={state.email}
                                    id="email"
                                    type="email" placeholder="email@example.com" name="email" required />
                            </div>
                            <div className="flexColumnCenter">
                                <label className="labelSignup">Phone number</label>
                                <input onChange={handleChange}
                                    value={state.phonenumber}
                                    id="phonenumber"
                                    type='number' placeholder="+39 0123456789" name="phoneNumber" required />
                            </div>
                            <div className="flexColumnCenter">
                                <label className="labelSignup">Are you a shop owner?</label>
                                <input onChange={handleChange}
                                    value={state.shopOwner}
                                    id="shopOwner"
                                    type="checkbox" name="shopOwner" />
                            </div>
                            <button className="activeButton" onClick={checkValidity}>Next</button>
                        </div>
                        :
                        <div className="inputContainer">
                            <h3>Create a password</h3>
                            <div className="flexColumnCenter">
                                <label className="labelSignup">Password</label>
                                <input onChange={handleChange}
                                    value={state.password}
                                    id="password"
                                    type="password" placeholder="Password" name="password" required />
                            </div>
                            <div className="flexColumnCenter">
                                <label className="labelSignup">Repeat password</label>
                                <input onChange={handleChange}
                                    value={state.repeatPassword}
                                    id="repeatPassword" type="password" placeholder="Repeat password" name="repeatPassword" required />
                            </div>
                            <div className="flexRowCenter">
                                <button className="activeButton" type="submit" onClick={() => {
                                    setState(prevState => ({
                                        ...prevState,
                                        'nextBool': false
                                    }));
                                }}> Back </button>
                                <button className="activeButton">Confirm</button>
                            </div>
                        </div>
                    }
                </form>
                :
                <form id="signupForm" autoComplete="off">
                    <h3> Don't have an account?</h3>
                    <button className="activeButton" onClick={props.renderSignup}>Signup</button>
                </form>
            }
        </div>
    );
}

export default Signupform;