import React from 'react';
import { useHistory } from "react-router-dom";

const Searchsection = () => {

    const history = useHistory();

    const handleClick = () =>{
        history.push('/Search');
    }
    
    return(
        <div>
            <h3 onClick={handleClick}> Where do you want to go? </h3>
        </div>
    );  
}

export default Searchsection;