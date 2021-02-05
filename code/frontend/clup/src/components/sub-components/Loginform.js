import React from 'react';
import {Link} from 'react-router-dom';

const Loginform = () => {
    return(
        <div>
            <h3>Login to your account</h3>
            <form /* action=TODO */ method="post">
                <div>
                    <input type="text" placeholder="Username" name="username" required />
                    <input type="password" placeholder="Password" name="password" required />
                    <button type="submit">Login</button>
                </div>
                <div>
                    <span>Forgot <Link to="/">password?</Link></span>
                </div>
            </form>
        </div>
    );
}

export default Loginform;