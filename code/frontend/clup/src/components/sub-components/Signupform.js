import React from 'react';

const Signupform = () => {
    return (
        <div>
            <h3>Signup</h3>
            <form /* action=TODO */ method="post">
                <div className="loginFormContainer">
                    <form /* action=TODO */ method="post" autocomplete="off">
                        <div className="inputContainer">
                            <h3>Login to your account</h3>
                            <input type="text" placeholder="Username" name="username" required />
                            <input type="password" placeholder="Password" name="password" required />
                            <button className="activeButton" type="submit">Login</button>
                        </div>
                    </form>
                </div>
            </form>
        </div>
    );
}

export default Signupform;