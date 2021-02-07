import React, {useEffect, useState} from 'react';
import Searchsection from './sub-components/Searchsection';
import TicketsList from './sub-components/TicketsList';
import Account from './sub-components/Account';
import { AMW_URL_API, SSW_URL_API, ACCESS_TOKEN_NAME } from '../constants/urlsAPI';

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
        fetch(SSW_URL_API + "/tickets")
        .then(res => res.json())
        .then(
            (result) => {
                setState(prevState => ({
                    ...prevState,
                    isLoadedTickets: true,
                    tickets: result
                }));
            },
            (error) => {
                setState(prevState => ({
                    ...prevState,
                    isLoadedTickets: true,
                    error: error
                }));
            }
        )
    }, []); 

    //fetch account info
    useEffect(()=>{
        fetch(AMW_URL_API + "/userinfo") //TODO
        .then(res => res.json())
        .then(
            (result) => {
                setState(prevState => ({
                    ...prevState,
                    isLoadedAccount: true,
                    account: result
                }));
            },
            (error) => {
                setState(prevState => ({
                    ...prevState,
                    isLoadedAccount: true,
                    error: error
                }));
            }
        )
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