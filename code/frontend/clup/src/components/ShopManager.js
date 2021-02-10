import React, { useState, useEffect } from 'react';
import { WaveLoading } from 'react-loadingg';
import { axiosGET } from '../utils/httpRequest.js';
import GoBackButton from './sub-components/GoBackButton.js';
const ShopManager = (props) => {

    const [state, setState] = useState({
        isLoadedShop: false,
        shop: null,
        shopId: props.match.params.id
    });

    //TODO: i should fetch shop shift here, or maybe the server should already send me them, i'm talking to you @LudoLe >:|

    //fetch shop info
    useEffect(() => {
        const onOk = (response) => {
            setState(prevState => {
                return {
                    ...prevState,
                    isLoadedShop: true,
                    shop: response.data
                }
            });
        }
        axiosGET("SSW", ("/shopDetail/" + state.shopId), {}, onOk, null, null, true, false, true);
    }, [state.shopId]);

    const handleGoToScannerPage = () => {
        //TODO:
    }

    return (
        <div className="flexColumnCenter">
            <GoBackButton />
            {state.isLoadedShop ?
                <div className="flexColumnCenter">
                    {/* TODO: display shop info */}
                    <div> SHOP NAME: {state.shop.name} </div>
                    <div> description: {state.shop.description} </div>
                    <button onClick={handleGoToScannerPage}> go to scanner page </button>
                </div>
                :
                <WaveLoading />
            }
        </div>
    );
}

export default ShopManager;