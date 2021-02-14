import React from 'react';

const TicketTracker = (props) => {

    const ticketTracker = props.ticketTracker;

    return (
        <div className="flexColumnCenter cardWhite">
            <div> TICKET (ID: {ticketTracker.Ticket.id}) </div>
            <div> status {ticketTracker.Ticket.status}</div>
            <div> prev: {ticketTracker.previousMatchingTicket}, follow: {ticketTracker.followingMatchingTicket} </div>
        </div>
    );
}

export default TicketTracker;