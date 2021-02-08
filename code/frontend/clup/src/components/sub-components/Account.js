import React, { useState } from 'react';
import { WaveLoading } from 'react-loadingg';

const Account = (props) => {

    const [accountToggler, setAccountToggler] = useState(false);

    const toggleAccount = () => {
        setAccountToggler(!accountToggler);
    }

    const handleLogout = () => {

    }

    const styleLoad = {
        color: 'black',
        position: 'relative'
    };

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
                        <button onClick={handleLogout}> Logout </button>
                        <div onClick={toggleAccount}> close </div>
                    </div>
                    :
                    <div>
                        <h3 onClick={toggleAccount}> Account </h3>
                    </div>
                }
            </div>
        );
    }
    else {
        return <div className="flexColumnCenter"> <WaveLoading style={styleLoad}/></div>
    }
}

export default Account;