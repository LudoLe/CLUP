import React from 'react';
import { useHistory } from "react-router-dom";

const GoToButton = (props) => {

    const history = useHistory();

    return (
        <button onClick={() => { history.push(props.to) }}> {props.content} </button>
    );
}

export default GoToButton;