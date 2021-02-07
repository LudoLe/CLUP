import React from 'react';
import TicketElement from './TicketElement';
import Loading from '@bit/webnovel.read-ui.loading';

const TicketsList = (props) => {
    if(props.isLoadedTickets){
        return <div className="flexColumnCenter"> {props.tickets.map(ticket => <TicketElement ticket={ticket} />)} </div>
    }
    else{
        return <div className="flexColumnCenter"> <Loading/> </div>
    }
}

export default TicketsList;