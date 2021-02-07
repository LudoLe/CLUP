import React, { useState } from 'react';

const Signupform = (props) => {

    const [state, setState] = useState({
        nextBool: false,
        username: null,
        password: null,
        repeatPassword: null,
        email: null,
        phonenumber: null,
        shopOwner: false
    });

    const handleChange = (e) => {
        const {id , value} = e.target   
        setState(prevState => ({
            ...prevState,
            [id] : value
        }))
    }

    const checkValidity = (e) => {
        if (document.getElementById("signupForm").reportValidity()) {
            setState(prevState => ({
                ...prevState,
                nextBool : true
            }))
        }
    }

    const handleSignupSubmit = (e) =>{
        e.preventDefault();
        if(e.target.reportValidity()) {
            sendSignupToServer(); 
        }
    }

    const sendSignupToServer = () => {
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
        window.location.href = "/Home";
    }

    const renderCorrectInputContainer = () => {
        if (!state.nextBool) {
            return (
                <div className="inputContainer">
                    <h3>Create a new account</h3>
                    <div className="flexColumnCenter">
                        <label className="labelSignup">Username</label>
                        <input onChange={handleChange}
                            value={state.username}
                            id="username" 
                            type="text" placeholder="Username" name="username" required />
                    </div>
                    <div className="flexColumnCenter">
                        <label className="labelSignup">Email</label>
                        <input onChange={handleChange}
                            value={state.email}
                            id="email" 
                            type="email" placeholder="email@example.com" name="email" required />
                    </div>
                    <div className="flexColumnCenter">
                        <label className="labelSignup">Phone number</label>
                        <input onChange={handleChange}
                            value={state.phonenumber}
                            id="phonenumber" 
                            type='number' placeholder="+39 0123456789" name="phoneNumber" required />
                    </div>
                    <div className="flexColumnCenter">
                        <label className="labelSignup">Are you a shop owner?</label>
                        <input onChange={handleChange}
                            value={state.shopOwner}
                            id="shopOwner"
                            type="checkbox" name="shopOwner" />
                    </div>
                    <button className="activeButton" onClick={checkValidity}>Next</button>
                </div>
            );
        }
        else {
            return (
                <div className="inputContainer">
                    <h3>Create a password</h3>
                    <div className="flexColumnCenter">
                        <label className="labelSignup">Password</label>
                        <input onChange={handleChange}
                            value={state.password}
                            id="password"
                            type="password" placeholder="Password" name="password" required />
                    </div>
                    <div className="flexColumnCenter">
                        <label className="labelSignup">Repeat password</label>
                        <input onChange={handleChange}
                            value={state.repeatPassword}
                            id="repeatPassword" type="password" placeholder="Repeat password" name="repeatPassword" required />
                    </div>
                    <div className="flexRowCenter">
                        <button className="activeButton" type="submit" onClick={() => { setState({ nextBool: false }}> Back </button>
                        <button className="activeButton">Confirm</button>
                    </div>
                </div>
            );
        }
    }

    if (props.open) {
        return (
            <div className="loginFormContainer">
                <form onSubmit={(state.nextBool === true) ? {handleSignupSubmit} : ""} id="signupForm" autoComplete="off">
                    {renderCorrectInputContainer()}
                </form>
            </div>
        );
    }
    else {
        return (
            <div className="loginFormContainer">
                <form id="signupForm" autoComplete="off">
                    <h3> Don't have an account?</h3>
                    <button className="activeButton" onClick={props.renderSignup}>Signup</button>
                </form>
            </div>
        );
    }
}

export default Signupform;