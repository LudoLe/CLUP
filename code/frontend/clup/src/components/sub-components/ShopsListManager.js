import React from 'react';
import ShopElementManager from './ShopElementManager';
import { WaveLoading } from 'react-loadingg';

const ShopsListManager = (props) => {
    if (props.isLoaded) {
        return (
            <div className="flexColumnCenter">
                <div className="bold yourShopsTitle">YOUR SHOPS:</div>
                <div class="tooltip">info
                    <span class="tooltiptext">
                        In this section you can see all your shops <br/>
                        For each shop you can see the following informations: <br/>
                        - shop name <br/>
                        - shop position <br/>
                        - a button to go to the relative shop page
                    </span>
                </div>
                {(props.shops !== null && props.shops.length !== 0) ?
                    (props.shops).map(shop => <ShopElementManager key={shop.id} shop={shop} />)
                    :
                    "There are no shops."
                }
            </div>
        );
    }
    else {
        return <div className="flexColumnCenter"> <WaveLoading style={{position: "relative"}} /></div>
    }
}

export default ShopsListManager;