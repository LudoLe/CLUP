import React from 'react';
import { useHistory } from "react-router-dom";

const ShopElementManager = (props) => {

    const history = useHistory();

    const handleOnClick = () => {
        history.push('/ShopManager/' + props.shop.id); //TODO: not sure the id thing is the correct thingy-di boopidi boop
    }

    const style = {
        padding: "4vh 10vw"
    }

    return (
        <div className="flexColumnCenter" style={style} onClick={handleOnClick}>
            <div> SHOP NAME: {props.shop.name} </div>
            <div> description: {props.shop.description} </div>
        </div>
    );
}

export default ShopElementManager;