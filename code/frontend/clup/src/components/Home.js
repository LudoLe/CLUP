import React, {useEffect, useState} from 'react';
import Searchsection from './sub-components/Searchsection';
import TicketsList from './sub-components/TicketsList';
import Account from './sub-components/Account';
import { axiosGET } from '../utils/httpRequest.js';


const Home = () => {

    const [state, setState] = useState({
        isLoadedTickets: false,
        isLoadedAccount: false,
        tickets: null,
        error: null,
        account: null
    });

    //fetch tickets info
    useEffect(()=>{
        const onOk = (response) =>{
            setState(prevState =>{
                return{
                     ...prevState,
                     isLoadedTickets : true,
                     tickets : response.data
                }
             });
        }
        axiosGET("SSW", "/tickets", {}, onOk, null , null, true, false, true);
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
        axiosGET("AMW", "/userinfo", {}, onOk, onOk, null, true, false, true);
    }, []); 

    return (
        <div>
            <Account isLoaded={state.isLoadedAccount} account={state.account} />
            <Searchsection />
            <TicketsList isLoaded={state.isLoadedTickets} tickets={state.tickets} />
        </div>
    );
}

export default Home;