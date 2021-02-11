import React from 'react';
import { useHistory } from "react-router-dom";

const GoHomeButton = () => {

    const history = useHistory();

    return (
        <button onClick={() => { localStorage.getItem("isManager") ? history.push("/HomeManager") : history.push("/Home") }}> Home </button>
    );
}

export default GoHomeButton;