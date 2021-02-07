import React from 'react';
import TicketElement from './TicketElement';
import { WaveLoading } from 'react-loadingg';

const TicketsList = (props) => {
    if(props.isLoadedTickets){
        return <div className="flexColumnCenter"> {props.tickets.map(ticket => <TicketElement ticket={ticket} />)} </div>
    }
    else{
        return <div className="flexColumnCenter"> <WaveLoading /></div>
    }
}

export default TicketsList;