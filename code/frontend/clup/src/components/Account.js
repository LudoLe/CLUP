import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const Account = () => {

    const [accountToggler, setAccountToggler] = useState(false);

    const toggleAccount = () => {
        setAccountToggler(!accountToggler);
    }

    if (accountToggler) {
        return (
            <div>
                <h3 onClick={toggleAccount}> Hide account </h3>
                <Link to="/Login"> <h3> Logout </h3> </Link>
            </div>
        );
    }
    else {
        return (
            <div>
                <h3 onClick={toggleAccount}> Account </h3>
            </div>
        );
    }
}

export default Account;