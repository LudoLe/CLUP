import React from 'react';
import history from '../../utils/history.js';

const GoBackButton = () => {
    return (
        <button onClick={() => { history.goBack() }}> Go back </button>
    );
}

export default GoBackButton;