import React from 'react';
import TicketElement from './TicketElement';

const Queue = (props) => {

    if(props.tickets.lenght === 0){
        return(
            <div className="flexColumnCenter">
                No tickets 
            </div>
        );
    }
    else{
        return(
            <div className="flexColumnCenter">
                {(props.tickets).map(ticket => (
                    <div key={ticket.id} className="flexColumnCenter">
                        <TicketElement key={ticket.id} ticket={ticket} showShop={false} />
                    </div>
                ))}
            </div>
        );
    }
}

export default Queue;