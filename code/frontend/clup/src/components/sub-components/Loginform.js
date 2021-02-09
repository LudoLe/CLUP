import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { axiosPOST, setUsernameLocal } from '../../utils/httpRequest.js';
import history from "../../utils/history";

const Loginform = (props) => {
    const [state, setState] = useState({
        username: "",
        password: "",
    });

    const handleChange = (e) => {
        const { id, value } = e.target
        setState(prevState => ({
            ...prevState,
            [id]: value
        }))
    }

    const handleLoginSubmit = (e) => {
        e.preventDefault();
        if (e.target.reportValidity()) {
            sendLoginToServer();
        }
    }

    const sendLoginToServer = () => {
        if ((state.username.length !== 0) && (state.password.length !== 0)) {
            const payload = {
                "username": state.username,
                "password": state.password
            }
            const onOk = (response) => {
                setUsernameLocal(response.data.username);
                console.log("username setted in local storage: " + response.data.username);
                if (response.data.isManager) {
                    redirectToManagerHome();
                }
                else {
                    redirectToHome();
                }
            }
            axiosPOST("AMW", "/login", payload, {}, onOk, null, null, false, true, false);
        }
    }

    const redirectToHome = () => {
        history.push("/Home");
    }
    const redirectToManagerHome = () => {
        history.push("/HomeManager");
    }

    return (
        <div className="loginFormContainer">
            {
                props.open ?
                    <form onSubmit={handleLoginSubmit} autoComplete="off">
                        <div className="inputContainer">
                            <h3>Login to your account</h3>
                            <div className="flexColumnCenter">
                                <label className="labelSignup">Username</label>
                                <input onChange={handleChange}
                                    value={state.username}
                                    id="username"
                                    type="text" placeholder="Username" name="username" required />
                            </div>
                            <div className="flexColumnCenter">
                                <label className="labelSignup">Password</label>
                                <input onChange={handleChange}
                                    id="password"
                                    value={state.password}
                                    type="password" placeholder="Password" name="password" required />
                            </div>
                            <button className="activeButton" type="submit">Confirm</button>
                        </div>
                        <div className="forgotPasswordContainer">
                            <span>Forgot <Link to="/">password?</Link></span>
                        </div>
                    </form>
                    :
                    <form method="post" autoComplete="off">
                        <div className="inputContainer">
                            <h3> Already have an account?</h3>
                            <button className="activeButton" onClick={props.renderLogin}>Login</button>
                        </div>
                    </form>
            }
        </div>
    );
}

export default Loginform;