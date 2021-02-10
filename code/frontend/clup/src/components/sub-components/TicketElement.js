import React from 'react';
import { useHistory } from "react-router-dom";

const TicketElement = (props) => {

    const history = useHistory();

    const handleOnClick = ()=>{
        history.push('/Ticket/' + props.ticket.id);
    }

    const style = {
        padding: "4vh 10vw"
    }

    return (
        <div className="flexColumnCenter" style={style} onClick={handleOnClick}>
            <h3> Ticket (id: {props.ticket.id}) </h3>
            <div> Status: {props.ticket.status} </div>
            {props.ticket.enteringTime ? <div> Entering Time: {props.ticket.enteringTime} </div> : "" }
            {props.ticket.exitTime ? <div> Exit Time {props.ticket.exitTime} </div>: "" }
            {props.ticket.scheduledEnteringTime ? <div> Scheduled Entering Time: {props.ticket.scheduledEnteringTime} </div>: "" }
            {props.ticket.scheduledExitingTime ? <div> Scheduled Exiting Time; {props.ticket.scheduledExitingTime} </div>: "" }
            <div> Time To Reach The Shop: {Math.ceil((Date.parse(props.ticket.timeToReachTheShop)/1000)/60)} minutes </div>
            <div> Expected Duration {Math.ceil((Date.parse(props.ticket.expectedDuration)/1000)/60)} minutes </div>
            {/* TODO: <div> Shop Name {props.ticket.shop.name} </div>
            <div> Shop Position {props.ticket.shop.position} </div>
            <div> Shop Image {props.ticket.shop.image} </div> */}
        </div>
    );
}

export default TicketElement;