import React from 'react';
import { useHistory } from "react-router-dom";

const ShopElement = (props) => {

    const history = useHistory();

    const handleOnClick = ()=>{
        history.push('/Shop/' + props.shop.id); //TODO: not sure the id thing is the correct thingy-di boopidi boop
    }

    const style = {
        padding: "4vh 10vw"
    }

    return (
        <div className="flexColumnCenter" style={style} onClick={handleOnClick}>
            {/* TODO:
            display shop infomrations
             */}
        </div>
    );
}

export default ShopElement;