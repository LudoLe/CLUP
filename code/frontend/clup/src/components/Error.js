import React from 'react';
import history from '../utils/history';

const Error = () => {
    
    const goToLogin = () =>{
        history.push("/");
    }

    const goBack = () =>{
        history.goBack();
    }

    const error = JSON.parse(sessionStorage.getItem("error"));

    console.log("error is");
    console.log(error);

    return (
        <div className="flexColumnCenter">
            <h3> Error! </h3>
            <h3> STATUS CODE: {error.status} </h3>
            <h3> ERROR MESSAGE: {error.data} </h3> {/*TODO:*/}
            <button onClick={goToLogin}>go to Login</button>
            <button onClick={goBack}>go back</button>
        </div>
    );
}

export default Error;