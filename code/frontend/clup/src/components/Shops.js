import React, { useState, useEffect } from 'react';
import { axiosGET } from '../utils/httpRequest';
import Searchsection from './sub-components/Searchsection';
import ShopsList from './sub-components/ShopsList';
import Navigation from './sub-components/Navigation';
import Account from './sub-components/Account';

const Shops = () => {

    const [state, setState] = useState({
        isLoadedShops: false,
        isLoadedAccount: false,
        shops: null,
        account: null
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

    //fetch account info
    useEffect(() => {
        const onOk = (response) => {
            setState(prevState => {
                return {
                    ...prevState,
                    isLoadedAccount: true,
                    account: response.data
                }
            });
        }
        axiosGET("AMW", "/userinfo", {}, onOk, null, null, true, false, true);
    }, []);

    return (
        <div>
            <Account isLoaded={state.isLoadedAccount} account={state.account}/>
            <div className="flexRowCenter">
            <Navigation goBack={true} goHome={true} />
            </div>
            <Searchsection />
            <ShopsList isLoaded={state.isLoadedShops} shops={state.shops} />
        </div>
    );
}

export default Shops;