import React from 'react';
import { useHistory } from "react-router-dom";
import { dateToMinutes } from '../../utils/dateParser';

const TicketElement = (props) => {

    const history = useHistory();

    const handleOnClickTicket = () => {
        history.push('/Ticket/' + props.ticket.id);
    }

    const handleOnClickShop = () => {
        history.push('/Shop/' + props.ticket.shop.id);
    }

    return (
        <div className="flexColumnCenter card">

            <bold>TICKET (ID: {props.ticket.id})</bold>

            <div className="clickable" onClick={handleOnClickTicket}>

                <div> Status: {props.ticket.status} </div>

                <div> Time To Reach The Shop: {dateToMinutes(props.ticket.timeToReachTheShop)} minutes </div>

                <div> Declared duration of the Permanence: {dateToMinutes(props.ticket.expectedDuration)} minutes </div>

                <div className="flexColumnCenter">

                    {props.ticket.scheduledEnteringTime ? <div> Scheduled Entering Time: {props.ticket.scheduledEnteringTime} </div> : ""}

                    {props.ticket.scheduledExitingTime ? <div> Scheduled Exiting Time: {props.ticket.scheduledExitingTime} </div> : ""}

                </div>

            </div>

            {
                props.showShop ?
                    <div className="flexColumnCenter clickable" onClick={handleOnClickShop}>

                        <div> Shop Name {props.ticket.shop.name} </div>

                        <div> Shop Position {props.ticket.shop.position} </div>

                        <div> Shop Image {props.ticket.shop.image} </div>

                    </div>
                    :
                    ""
            }

        </div>
    );
}

export default TicketElement;