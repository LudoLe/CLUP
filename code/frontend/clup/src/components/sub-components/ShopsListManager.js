import React from 'react';
import ShopElementManager from './ShopElementManager';
import { WaveLoading } from 'react-loadingg';

const ShopsListManager = (props) => {
    if (props.isLoaded) {
        return (
            <div className="flexColumnCenter">
                {(props.shops !== null && props.shops.length !== 0) ?
                    (props.shops).map(shop => <ShopElementManager key={shop.id} shop={shop} />)
                    :
                    "There are no shops."
                }
            </div>
        );
    }
    else {
        return <div className="flexColumnCenter"> <WaveLoading /></div>
    }
}

export default ShopsListManager;