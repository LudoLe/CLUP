import React from 'react';
import TicketElement from './TicketElement'

const TicketsList = () => {
    return (
        <div>
            <h3> TODO: list of tickets. </h3>
            {for (let i = 0; i < 3; i++) {
                <TicketElement/>
            }}
        </div>
    );
}

export default TicketsList;