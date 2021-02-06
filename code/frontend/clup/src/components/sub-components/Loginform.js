import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import Error from './ErrorMessage';
import {AMW_URL_API, ACCESS_TOKEN_NAME} from '../../constants/urlsAPI';

const Loginform = (props) => {

    const [data, setData] = useState(null);
    const [error, setError] = useState(null);
    const [state, setState] = useState({
        username: "",
        password: "",
        successMessage: null,
        error: null
    });

    const handleChange = (e) => {
        const {id , value} = e.target   
        setState(prevState => ({
            ...prevState,
            [id] : value
        }))
    }

    const handleLoginSubmit = (e) =>{
        e.preventDefault();
        if(e.target.reportValidity()) {
            sendLoginToServer(); 
        }
    }

    const sendLoginToServer = () => {
        if((state.username.length!=0) && (state.password.length!=0)) {
            const payload={
                "username":state.username,
                "password":state.password
            }
            axios.post(AMW_URL_API+'/login', payload)
                .then(function (response) {
                    if(response.status === 200){
                        setState(prevState => ({
                            ...prevState,
                            'successMessage' : 'Registration successful. Redirecting to home page..'
                        }))
                        console.log(response); //JUSTO TO VISUALIZE IT
                        localStorage.setItem(ACCESS_TOKEN_NAME, response.data.token); //SAVE LOGIN TOKEN
                        redirectToHome();
                    } else{
                        console.log("HERE");
                        setState(prevState => ({
                            ...prevState,
                            'error' : {
                                status: response.status,
                                message: response.message
                            }
                        }))
                    }
                })
                .catch(function (error) {
                    setState(prevState => ({
                        ...prevState,
                        'error' : error
                    }))
                    console.log(error);
                });    
        } else {
            props.showError('Please enter valid username and password')    
        }
    }

    const redirectToHome = () => {
        props.history.push('/home');
    }

    return (
        <div className="loginFormContainer">
            {(state.error !== null) ? <Error error={state.error} /> : ""}
            {
                props.open ?
                    <form onSubmit={handleLoginSubmit} autoComplete="off">
                        <div className="inputContainer">
                            <h3>Login to your account</h3>
                            <div className="flexColumnCenter">
                                <label className="labelSignup">Username</label>
                                <input onChange={handleChange} 
                                    value={state.username}
                                    id="username"
                                    type="text" placeholder="Username" name="username" required />
                            </div>
                            <div className="flexColumnCenter">
                                <label className="labelSignup">Password</label>
                                <input onChange={handleChange}
                                    id="password"
                                    value={state.password} 
                                    type="password" placeholder="Password" name="password" required />
                            </div>
                            <button className="activeButton" type="submit">Confirm</button>
                        </div>
                        <div className="forgotPasswordContainer">
                            <span>Forgot <Link to="/">password?</Link></span>
                        </div>
                    </form>
                    :
                    <form /* action=TODO */ method="post" autoComplete="off">
                        <div className="inputContainer">
                            <h3> Already have an account?</h3>
                            <button className="activeButton" onClick={props.renderLogin}>Login</button>
                        </div>
                    </form>
            }
        </div>
    );
}

export default Loginform;