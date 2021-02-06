import React from 'react';

const Signupform = () => {
    return (
        <div className="loginFormContainer">
            <form /* action=TODO */ method="post" autocomplete="off">
                <div className="loginFormContainer">
                    <form /* action=TODO */ method="post" autocomplete="off">
                        <div className="inputContainer">
                            <h3>Create a new account</h3>
                            <div className="flexColumnCenter">
                                <label className="labelSignup" for="male">Username</label>
                                <input type="text" placeholder="Username" name="username" required />
                            </div>
                            <div className="flexColumnCenter">
                                <label className="labelSignup" for="male">Email</label>
                                <input type="email" placeholder="email@example.com" name="email" required />
                            </div>
                            <div className="flexColumnCenter">
                                <label className="labelSignup" for="male">Phone number</label>
                                <input type='number' placeholder="+39 0123456789" name="phoneNumber" required />
                            </div>
                            <div className="flexColumnCenter">
                                <label className="labelSignup" for="male">Password</label>
                                <input type="password" placeholder="Password" name="password" required />
                            </div>
                            <div className="flexColumnCenter">
                                <label className="labelSignup" for="male">Repeat password</label>
                                <input type="password" placeholder="Repeat password" name="repeatPassword" required />
                            </div>
                            <button className="activeButton" type="submit">Signup</button>
                        </div>
                    </form>
                </div>
            </form>
        </div>
    );
}

export default Signupform;