import React from 'react';
import Searchsection from './sub-components/Searchsection';
import TicketsList from './sub-components/TicketsList';
import Account from './sub-components/Account';

const Home = () => {

    return (
        <div>
            <Account />
            <Searchsection />
            <TicketsList />
        </div>
    );
}

export default Home;