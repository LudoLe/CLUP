import React, { useState, useEffect } from 'react';
import { axiosGET } from '../utils/httpRequest';
import GoBackButton from './sub-components/GoBackButton';
import Searchsection from './sub-components/Searchsection';
import ShopsList from './sub-components/ShopsList';

const Shops = () => {

    const [state, setState] = useState({
        isLoadedShops: false,
        shops: null,
    });

    //TODO: Maybe i should fetch shop shift even here, or maybe the server should already send me them, i'm talking to you @LudoLe >:|

    //fetch shops info
    useEffect(() => {
        const onOk = (response) => {
            setState(prevState => {
                return {
                    ...prevState,
                    isLoadedShops: true,
                    shops: response.data
                }
            });
        }
        axiosGET("SSW", "/AllShops", {}, onOk, null, null, true, false, true);
    }, []);

    return (
        <div className="flexColumnCenter">
            <GoBackButton />
            <Searchsection />
            <ShopsList isLoaded={state.isLoadedShops} shops={state.shops} />
        </div>
    );
}

export default Shops;