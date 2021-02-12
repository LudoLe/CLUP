import React from 'react';
import { useHistory } from "react-router-dom";

const Searchsection = () => {

    const history = useHistory();

    const handleClick = () =>{
        history.push('/Search');
    }
    
    return(
        <div className="searchSectionContainer flexColumnCenter">
            <h3> Where do you want to go? </h3>
            <button onClick={handleClick} className="smallButton">Search a shop</button>
        </div>
    );  
}

export default Searchsection;