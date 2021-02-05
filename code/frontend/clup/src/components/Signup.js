import React from 'react';
import '../css/Signup.css';
import Signupform from './sub-components/Signupform';
import { Link } from 'react-router-dom';

const Signup = () => {
    return (
        <div className="appContainer">
            <div className="topContainer topContainerSignup"> {/* top container */}
                <Signupform />
            </div>
            <div className="bottomContainer bottomContainerSignup"> {/* bottom container */}
                <h3> Do you own a shop? </h3>
                <Link to="/SignupManager"> <button className="activeButton">Signup as a Manager</button> </Link>
            </div>
        </div>
    );
}

export default Signup;