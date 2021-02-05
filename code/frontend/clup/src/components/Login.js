import React from 'react';
import Loginform from './sub-components/Loginform'
import { Link } from 'react-router-dom';

const Login = () => {

    return (
        <div>
            <h1> LOGIN </h1>
            <Loginform />
            <div>
                <h3> Don't have an account?</h3>
                <Link to="/Signup"> <button>Signup</button> </Link>
            </div>
        </div>
    );
}

export default Login;