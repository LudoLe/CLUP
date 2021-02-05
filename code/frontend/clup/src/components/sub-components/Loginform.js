import React from 'react';
import { Link } from 'react-router-dom';

const Loginform = () => {

    const expandCard = (e) => {
        document.getElementsByClassName("topContainer")[0].style.flexGrow = "10";
        console.log("focus in");
    }
    const shrinkCard = (e) =>{
        document.getElementsByClassName("topContainer")[0].style.flexGrow = "2";
        console.log("focus out");
    }

    return (
        <div className="loginFormContainer">
            <form /* action=TODO */ method="post" autocomplete="off">

                <div onFocus={expandCard} onBlur={shrinkCard} className="inputContainer">
                    <h3>Login to your account</h3>
                    <input  type="text" placeholder="Username" name="username" required />
                    <input  type="password" placeholder="Password" name="password" required />
                    <button className="activeButton"  type="submit">Login</button>
                </div>
                <div className="forgotPasswordContainer">
                    <span>Forgot <Link to="/Login">password?</Link></span>
                </div>
            </form>
        </div>
    );
}

export default Loginform;