import React, {useState} from 'react';
import '../../css/Error.css';

const Start = (props) => {

    const [state, setState] = useState(props.error);

    const reloadPage = () =>{
        window.location.reload();
    }
    const closeError = () =>{
        setState(null);
    }

    if(state === null){
        return (
            <div className="displayNone"> no Errors </div >
        )
    }

    return (
        <div className="flexColumnCenter errorContainer">
            <h3> ERROR </h3>
            <div className="flexColumnCenter">
                <h4> Status code: </h4>
                {props.error.status}
            </div>
            <div className="flexColumnCenter">
                <h4> Message: </h4>
                {props.error.message}
            </div>
            <br />
            <button className="activeButton" onClick={reloadPage}> Reload Page </button>
            <button className="activeButton" onClick={closeError}> Close Error </button>
        </div>
    );
}

export default Start;