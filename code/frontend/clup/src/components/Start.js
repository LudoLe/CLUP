import React, { useState } from 'react';
import '../css/Start.css';
import logo from '../images/LoginPageClupLogo.png';
import Loginform from './sub-components/Loginform';
import Signupform from './sub-components/Signupform';

const Start = () => {

    const [state, setState] = useState("login");

    const renderLogin = () => {
        setState("login");
    }
    const renderSignup = () => {
        setState("signup");
    }

    return (
        <div className="appContainer">
            <div className={`topContainer ${(state === "login") ? 'topContainerOpen' : 'topContainerClosed'}`}>
                <div className="clupLogoContainer">
                    <img src={logo} alt="" />
                </div>
                <Loginform open={(state === "login") ? true : false} renderLogin={renderLogin}/>
            </div>
            <div className={`bottomContainer ${(state === "login") ? 'bottomContainerClosed' : 'bottomContainerOpen'}`}>
                <Signupform open={(state === "login") ? false : true} renderSignup={renderSignup}/>
            </div>
        </div>
    );
}

export default Start;