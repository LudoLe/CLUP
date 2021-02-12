import React, { useState } from 'react';
import { WaveLoading } from 'react-loadingg';
import { ACCESS_TOKEN_NAME } from '../../constants/urlsAPI.js';
import { useHistory } from "react-router-dom";
import { axiosGET } from '../../utils/httpRequest.js';
import accountSVG from '../../images/account.svg';

const Account = (props) => {

    const history = useHistory();

    const [accountToggler, setAccountToggler] = useState(false);

    const toggleAccount = () => {
        setAccountToggler(!accountToggler);
    }

    const handleLogout = () => {
        const onOk = () => {
            localStorage.removeItem(ACCESS_TOKEN_NAME);
            history.push("/");
        };
        axiosGET("AMW", "/logout", {}, onOk, null, null, false, false, true);
    }

    return (
        <div className="flexColumnCenter">
            { accountToggler
                ?
                <div className="accountInfoContainer flexColumnCenter card">
                    <div className="bold"> Account info: </div>
                    {props.isLoaded ?
                        <div>
                            <div> Username: {props.account.username} </div>
                            <div> Email: {props.account.email} </div>
                            {props.account.isManager ? <div>You are a manager</div> : ""}
                        </div>
                        :
                        <div className="flexColumnCenter"> <WaveLoading style={{ position: "relative" }} /></div>
                    }
                    <div className="flexRowCenter">
                        <button className="smallButton" onClick={handleLogout}> Logout </button>
                        <button className="smallButton" onClick={toggleAccount}> close </button>
                    </div>
                </div>
                :
                <div>
                    <button className="accountButton" onClick={toggleAccount}>
                        <img
                            className="accountIcon"
                            src={accountSVG}
                            alt="Account" />
                    </button>
                </div>
            }
        </div >
    );
}

export default Account;