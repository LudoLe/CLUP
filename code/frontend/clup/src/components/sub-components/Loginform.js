import React from 'react';
import { Link } from 'react-router-dom';

const Loginform = (props) => {
    return (
        <div className="loginFormContainer">
            {
                props.open ?
                    <form /* action=TODO */ method="post" autoComplete="off">
                        <div className="inputContainer">
                            <h3>Login to your account</h3>
                            <div className="flexColumnCenter">
                                <label className="labelSignup">Username</label>
                                <input type="text" placeholder="Username" name="username" required />
                            </div>
                            <div className="flexColumnCenter">
                                <label className="labelSignup">Password</label>
                                <input type="password" placeholder="Password" name="password" required />
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