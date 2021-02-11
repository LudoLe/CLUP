import React from 'react';
import { useHistory } from "react-router-dom";

const ShopElement = (props) => {

    const history = useHistory();

    const handleOnClick = ()=>{
        history.push('/Shop/' + props.shop.id);
    }

    return (
        <div className="flexColumnCenter card clickable" onClick={handleOnClick}>
            
            <div> {props.shop.name} </div>
            
            <div> {props.shop.image} </div>
            
            <div> {props.shop.description} </div>
            
            <div> {props.shop.position} </div>

        </div>
    );
}

export default ShopElement;