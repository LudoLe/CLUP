import React from 'react';
import SignupformManager from './SignupformManager.js';
import { Link } from 'react-router-dom';


const SignupManager = () => {
    return (
        <div>
            <h1> SIGNUP AS MANAGER </h1>
            <Link to="/Signup"> <h3>go back </h3> </Link>
            <SignupformManager />
        </div>
    );
}

export default SignupManager;