import React from 'react';
import ShopElement from './ShopElement';
import { WaveLoading } from 'react-loadingg';

const ShopsList = (props) => {
    if (props.isLoaded) {
        return (
            <div className="flexColumnCenter">
                <div className="bold yourTicketTitle">ALL SHOPS:</div>
                {(props.shops !== null && props.shops.length !== 0) ?
                    (props.shops).map(shop => <ShopElement key={shop.id} shop={shop} />)
                    :
                    "There are no shops."
                }
            </div>
        );
    }
    else {
        return <div className="flexColumnCenter"> <WaveLoading style={{ position: "relative" }} /></div>
    }
}

export default ShopsList;