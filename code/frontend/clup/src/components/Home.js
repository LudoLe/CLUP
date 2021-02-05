import React from 'react';
import Searchsection from './Searchsection';
import Tickets from './Tickets';
import Account from './Account';

const Home = () => {

    return (
        <div>
            <Account />
            <Searchsection />
            <Tickets />
        </div>
    );
}

export default Home;