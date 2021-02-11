import React from 'react';
import TicketTracker from './TicketTracker';

const TimeSlot = (props) => {

    const timeslot = props.timeSlot;

    return (
        <div className="flexColumnCenter card">
            <div className="simpleTitle"> TIME SLOT (ID: {timeslot.id}) </div>
            <div> from:  {timeslot.startingTime} <br /> to: {timeslot.endingTime} </div>
            <div className="flexRowStart">
                <div className="flexColumnCenter">
                    <div className="simpleTitle"> EXPECTED ENTERING TICKETS </div>
                    {timeslot.expectedEnteringTickets ? (timeslot.expectedEnteringTickets).map(ticketTracker => <TicketTracker ticketTracker={ticketTracker}/>) : ""}
                </div>
                <div className="flexColumnCenter">
                    <div className="simpleTitle"> EXPECTED EXITING TICKETS </div>
                    {timeslot.expectedExitingTickets ? (timeslot.expectedExitingTickets).map(ticketTracker => <TicketTracker ticketTracker={ticketTracker}/>) : ""}
                </div>
            </div>
        </div>
    );
}

export default TimeSlot;