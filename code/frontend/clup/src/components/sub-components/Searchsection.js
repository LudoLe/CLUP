import React from 'react';
import history from '../../utils/history'; 

const Searchsection = () => {
    const handleClick = () =>{
        history.push('/Search');
        //window.location.href="/Search";
    }
    
    return(
        <div>
            <h3 onClick={handleClick}> Where do you want to go? </h3>
        </div>
    );  
}

export default Searchsection;