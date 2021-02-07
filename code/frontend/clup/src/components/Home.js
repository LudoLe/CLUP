import React, {useEffect, useState} from 'react';
import Searchsection from './sub-components/Searchsection';
import TicketsList from './sub-components/TicketsList';
import Account from './sub-components/Account';
import { axiosGET, getUsernameLocal } from '../utils/httpRequest.js';


const Home = () => {

    const [state, setState] = useState({
        isLoadedTickets: false,
        isLoadedAccount: false,
        tickets: [],
        error: [],
        account: []
    });

    //fetch ticket info
    useEffect(()=>{
        const headers = {
            username: getUsernameLocal()
        }
        const onOk = (response) =>{
            setState(prevState =>{
                return{
                     ...prevState,
                     isLoadedTickets : true
                }
             });
            console.log("OK TICKETS")
        }
        axiosGET("SSW", "/tickets", headers, onOk, null, null, true, true);
    }, []); 

    //fetch account info
    useEffect(()=>{
        /* const headers = {
            username: getUsernameLocal()
        }
        const onOk = (response) =>{
            setState(prevState =>{
                return{
                     ...prevState,
                     isLoadedAccount : true
                }
             });
            console.log("OK ACCOUNT INFO")
        }
        axiosGET("AMW", "/userinfo", headers, onOk, null, null, true, true); */
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