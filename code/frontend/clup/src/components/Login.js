import React from 'react';
import '../css/Login.css';
import logo from '../images/LoginPageClupLogo.png';
import Loginform from './sub-components/Loginform';
import { Link } from 'react-router-dom';

const Login = () => {

    return (
        <div className="appContainer"> {/* app container */}
            <div className="topContainer"> {/* top container */}
                <div className="clupLogoContainer"> {/* logo image */}
                    <img src={logo} alt="" />
                </div>
                <Loginform /> {/* Login form */}
            </div>
            <div className="bottomContainer"> {/* bottom container */}
                <h3> Don't have an account?</h3>
                <Link to="/Signup"> <button className="activeButton">Signup</button> </Link>
            </div>
        </div>
    );
}

export default Login;