import React from 'react';
import TicketElement from './TicketElement';
import { WaveLoading } from 'react-loadingg';

const TicketsList = (props) => {
    if (props.isLoaded) { 
        return (
            <div className="flexColumnCenter">
                {(props.tickets !== null && props.tickets.length !== 0) ?
                    (props.tickets).map(ticket => <TicketElement key={ticket.id} ticket={ticket} />)
                    :
                    "You have no tickets."
                }
            </div>
        );
    }
    else {
        return <div className="flexColumnCenter"> <WaveLoading /></div>
    }
}

export default TicketsList;