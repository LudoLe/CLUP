import React from 'react';
import TicketElement from './TicketElement';

const Queue = (props) => {

    console.log("queue:");
    console.log(props.tickets);

    const handleEnter = () => {

    }

    const handleExit = () => {

    }

    return (
        <div className="flexColumnCenter">
            {(props.tickets).map(ticket => (
                <div key={ticket.id} className="flexColumnCenter">
                    <TicketElement key={ticket.id} ticket={ticket} showShop={false} />
                    <div className="flexRowCenter">
                        <button onClick={handleEnter}>Enter</button>
                        <button onClick={handleExit}>Exit</button>
                    </div>
                </div>
            ))}
        </div>
    );
}

export default Queue;