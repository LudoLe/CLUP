import React, { useState } from 'react';
import { WaveLoading } from 'react-loadingg';
import { ACCESS_TOKEN_NAME } from '../../constants/urlsAPI.js';
import history from '../../utils/history.js';
import { axiosGET } from '../../utils/httpRequest.js';

const Account = (props) => {

    const [accountToggler, setAccountToggler] = useState(false);

    const toggleAccount = () => {
        setAccountToggler(!accountToggler);
    }

    const handleLogout = () => {
        const onOk = (response) =>{
            localStorage.removeItem(ACCESS_TOKEN_NAME);
            history.push("/");
        };
        axiosGET("AMW", "/logout", {}, onOk, null, null, false, false, true);
    }

    if (props.isLoaded) {
        return (
            <div className="flexColumnCenter">
                { accountToggler
                    ?
                    <div>
                        <div> Account info: </div>
                        <div> Username: {props.account.username} </div>
                        <div> Email: {props.account.email} </div>
                        <div> Phone Number: {props.account.phoneNumber} </div>
                        <div> isManager: {props.account.isManager ? "true" : "false"} </div>
                        <button onClick={handleLogout}> Logout </button>
                        <button onClick={toggleAccount}> close </button>
                    </div>
                    :
                    <div>
                        <button onClick={toggleAccount}> Account </button>
                    </div>
                }
            </div>
        );
    }
    else {
        return <div className="flexColumnCenter"> <WaveLoading/></div>
    }
}

export default Account;