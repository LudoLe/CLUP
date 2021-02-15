import React, { useState, useEffect } from 'react';
import { WaveLoading } from 'react-loadingg';
import { useHistory } from "react-router-dom";
import { dateToMinutes } from '../utils/dateParser';
import { axiosGET } from '../utils/httpRequest.js';
import Navigation from './sub-components/Navigation';

const Ticket = (props) => {

    const [state, setState] = useState({
        isLoadedTicket: false,
        ticket: null,
        ticketId: props.match.params.id,
    });

    const history = useHistory();

    //fetch ticket info
    useEffect(() => {
        const onOk = (response) => {
            setState(prevState => {
                return {
                    ...prevState,
                    isLoadedTicket: true,
                    ticket: response.data
                }
            });
        }
        axiosGET("SSW", ("/ticketDetail/" + state.ticketId), {}, onOk, null, null, true, false, true);
    }, [state.ticketId]);

    const handleDequeue = () => {
        const onOk = () => {
            history.push("/Home");
        }
        axiosGET("QSW", ("/dequeue/" + state.ticketId), {}, onOk, null, null, true, false, true);
    }

    const handleOnClickShop = () => {
        history.push('/Shop/' + state.ticket.shop.id);
    }

    return (
        <div className="flexColumnCenter">
            <Navigation goBack={true} goHome={true} />
            {state.isLoadedTicket ?
                <div className="flexColumnCenter">
                    <div class="tooltip">Info
                        <span class="tooltiptext">
                            This is the page of a ticket. <br />
                            This page contains: <br />
                            - status: ticket status (also the color of the border is based on the status) <br/>
                            - the time needed to reach the shop<br/>
                            - the duration of the permanence inside the shop<br/>
                            - enter time: if available the actual time the ticket was scanned to enter the shop<br/>
                            - exit time: if available the actual time the ticket was scanned to exit the shop<br/>
                            - start the starting time to scan the ticket to enter the shop<br/>
                            - expected end: the expected time you'll scan the ticket to exit the shop<br/>
                            - a button to check the shop this ticket is enqueued in<br/>
                            - a button to delete the ticket
                        </span>
                    </div>

                    <div className="bold yourTicketTitle">TICKET (ID: {state.ticket.id})</div>
                   
                    <div className={"flexColumnCenter card " + state.ticket.status}>

                        <div> <div className="bold">Status </div> {state.ticket.status} </div>

                        <div> <div className="bold">Time To Reach The Shop </div> {dateToMinutes(state.ticket.timeToReachTheShop)} minutes </div>

                        <div> <div className="bold">Declared duration of the Permanence </div> {dateToMinutes(state.ticket.expectedDuration)} minutes </div>

                        <div className="flexColumnCenter">

                            {state.ticket.enterTime ? <div> <div className="bold">Enter Time </div> {state.ticket.enterTime} </div> : ""}

                            {state.ticket.exitTime ? <div> <div className="bold">Exit Time </div> {state.ticket.exitTime} </div> : ""}

                            {state.ticket.scheduledEnteringTime ? <div> <div className="bold">start </div> {state.ticket.scheduledEnteringTime} </div> : ""}

                            {state.ticket.scheduledExitingTime ? <div> <div className="bold">expected end </div> {state.ticket.scheduledExitingTime} </div> : ""}

                        </div>

                    </div>

                    <div className="flexRowCenter">
                        <button className="smallButton" onClick={handleOnClickShop}>Check shop</button> 
                        or
                        <button className="smallButton" onClick={handleDequeue}> Dequeue </button>
                    </ div>

                </div>
                :
                <WaveLoading style={{ position: "relative" }} />
            }
        </div>
    );

}

export default Ticket;