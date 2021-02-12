import React from 'react';
import { useHistory } from "react-router-dom";

const GoHomeButton = () => {

    const history = useHistory();

    const goHome = () => {
        if (localStorage.getItem("isManager") == true) {
            history.push("/HomeManager")
        }
        else {
            history.push("/Home")
        }
    }

    return (
        <button onClick={goHome}> Home </button>
    );
}

export default GoHomeButton;