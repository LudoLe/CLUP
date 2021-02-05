import React from 'react';

const Signupform = () => {
    return(
        <div>
            <h3>Signup</h3>
            <form /* action=TODO */ method="post">
                <div>
                    <input type="text" placeholder="Username" name="username" required />
                    <input type="password" placeholder="Password" name="password" required />
                    <button type="submit">Login</button>
                </div>
                <div>
                    <span>Forgot <a href="/" /*TODO*/>password?</a></span>
                </div>
            </form>
        </div>
    );
}

export default Signupform;