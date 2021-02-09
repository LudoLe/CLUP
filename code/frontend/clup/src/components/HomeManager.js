import React, {useEffect, useState} from 'react';
import Account from './sub-components/Account';
import { axiosGET } from '../utils/httpRequest.js';


const Home = () => {

    const [state, setState] = useState({
        isLoadedTickets: false,
        isLoadedAccount: false,
        account: null,
        shops: null
    });

    //fetch shops info
     useEffect(()=>{
        const onOk = (response) =>{
            setState(prevState =>{
                return{
                     ...prevState,
                     isLoadedTickets : true,
                     shops : response.data
                }
             });
        }
        axiosGET("SSW", "/shops", {}, onOk, null , null, true, false, true);
    }, []);

    //fetch account info
    useEffect(()=>{
        const onOk = (response) =>{
            setState(prevState =>{
                return{
                     ...prevState,
                     isLoadedAccount : true,
                     account: response.data
                }
             });
        }
        axiosGET("AMW", "/userinfo", {}, onOk, null, null, true, false, true);
    }, []); 

    return (
        <div>
            <Account isLoaded={state.isLoadedAccount} account={state.account} />
        </div>
    );
}

export default Home;