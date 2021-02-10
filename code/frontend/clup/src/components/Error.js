import React from 'react';
import history from '../utils/history';
import GoBackButton from './sub-components/GoBackButton';

const Error = () => {
    
    const goToLogin = () =>{
        history.push("/");
    }

    const error = JSON.parse(sessionStorage.getItem("error"));

    return (
        <div className="flexColumnCenter">
            <h3> Error! </h3>
            <h3> STATUS CODE: {error.status} </h3>
            <h3> ERROR MESSAGE: {error.data} </h3> {/*TODO:*/}
            <button onClick={goToLogin}>go to Login</button>
            <GoBackButton/>
        </div>
    );
}

export default Error;