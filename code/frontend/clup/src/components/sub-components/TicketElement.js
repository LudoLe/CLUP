import React from 'react';
import history from '../../utils/history'; 

const TicketElement = (props) => {
    const handleOnClick = ()=>{
        history.push('/Ticket/' + props.ticket.id);
    }
    return (
        <div className="flexColumnCenter" onClick={handleOnClick}>
            <div> Status: {props.ticket.status} </div>
            <div> Entering Time: {props.ticket.enteringTime} </div>
            <div> Exit Time {props.ticket.exitTime} </div>
            <div> Scheduled Entering Time: {props.ticket.scheduledEnteringTime} </div>
            <div> Scheduled Exiting Time; {props.ticket.scheduledExitingTime} </div>
            <div> Shop Name {props.ticket.shop.name} </div>
            <div> Shop Position {props.ticket.shop.position} </div>
            <div> Shop Image {props.ticket.shop.image} </div>
            <div> Time To Reach The Shop: {props.ticket.timeToReachTheShop} </div>
            <div> Expected Duration {props.ticket.expectedDuration} </div>
        </div>
    );
}

export default TicketElement;