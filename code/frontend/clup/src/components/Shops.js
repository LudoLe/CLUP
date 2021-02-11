import React, { useState, useEffect } from 'react';
import { axiosGET } from '../utils/httpRequest';
import Searchsection from './sub-components/Searchsection';
import ShopsList from './sub-components/ShopsList';
import Navigation from './sub-components/Navigation';

const Shops = () => {

    const [state, setState] = useState({
        isLoadedShops: false,
        shops: null,
    });

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
            <Navigation goBack={true} goHome={true} />
            <Searchsection />
            <ShopsList isLoaded={state.isLoadedShops} shops={state.shops} />
        </div>
    );
}

export default Shops;