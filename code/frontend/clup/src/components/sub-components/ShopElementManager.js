import React from 'react';
import { useHistory } from "react-router-dom";
import shopImg from '../../images/shop.png';

const ShopElementManager = (props) => {

    const history = useHistory();

    const handleOnClick = () => {
        history.push('/ShopManager/' + props.shop.id);
    }

    return (
        <div className="flexColumnCenter card">
            <div className="flexRowCenter">
                <div>
                    <img
                        className="shopImg"
                        src={shopImg}
                        alt="Account" />
                </div>
                <div className="flexColumnCenter">
                    <div className="bold"> {props.shop.name} </div>
                    <button className="smallButton" onClick={handleOnClick}>More info</button>
                    <div> {props.shop.position} </div>
                </div>
            </div>
        </div>
    );
}

export default ShopElementManager;