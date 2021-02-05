import React from 'react';
import Signupform from './Signupform';
import { Link } from 'react-router-dom';

const Signup = () => {
    return (
        <div>
            <h1> SIGNUP </h1>
            <Link to="/Login"> <h3>go back </h3> </Link>
            <Signupform />
            <div>
                <h3> Do you own a shop? </h3>
                <Link to="/SignupManager"> <button>Signup as a Manager</button> </Link>
            </div>
        </div>
    );
}

export default Signup;