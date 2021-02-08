import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import Error from './ErrorMessage';
import { axiosPOST, setUsernameLocal } from '../../utils/httpRequest.js';

const Loginform = (props) => {
    const [state, setState] = useState({
        username: "",
        password: "",
        successMessage: null,
        error: null
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
                redirectToHome();
            }
            axiosPOST("AMW", "/login", payload, {}, onOk, null, null, false, true, false);
        } else {
            //TODO:
            //should show error of invalid credentials
        }
    }

    const redirectToHome = () => {
        window.location.href = "/Home";
    }

    return (
        <div className="loginFormContainer">
            {(state.error !== null) ? <Error error={state.error} /> : ""}
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
                    <form /* action=TODO */ method="post" autoComplete="off">
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