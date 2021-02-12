import React from 'react';
import GoBackButton from './GoBackButton';
import GoHomeButton from './GoHomeButton';

const Navigation = (props) => {

    return (
        <div className="flexRowCenter">
            {props.goBack ? <GoBackButton /> : ""}
            {props.goHome ? <GoHomeButton /> : ""} 
        </div>
    );
}

export default Navigation;