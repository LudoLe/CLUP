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
        <div className="flexColumnCenter card">
            <Navigation goBack={true} goHome={true} />
            {state.isLoadedTicket ?
                <div className="flexColumnCenter">

                    <div>

                        <div> Status: {state.ticket.status} </div>

                        <div> Time To Reach The Shop: {dateToMinutes(state.ticket.timeToReachTheShop)} minutes </div>

                        <div> Declared duration of the Permanence: {dateToMinutes(state.ticket.expectedDuration)} minutes </div>

                        <div className="flexColumnCenter">

                            {state.ticket.enterTime ? <div> Entering Time: {state.ticket.enteringTime} </div> : ""}

                            {state.ticket.exitTime ? <div> Exit Time {state.ticket.exitTime} </div> : ""}

                            {state.ticket.scheduledEnteringTime ? <div> Scheduled Entering Time: {state.ticket.scheduledEnteringTime} </div> : ""}

                            {state.ticket.scheduledExitingTime ? <div> Scheduled Exiting Time: {state.ticket.scheduledExitingTime} </div> : ""}

                        </div>

                    </div>

                    <div className="flexColumnCenter clickable" onClick={handleOnClickShop}>

                        <div> Shop Name {state.ticket.shop.name} </div>

                        <div> Shop Position {state.ticket.shop.position} </div>

                        <div> Shop Image {state.ticket.shop.image} </div>

                    </div>

                    <button onClick={handleDequeue}> Dequeue </button>

                </div>
                :
                <WaveLoading style={{position: "relative"}}/>
            }
        </div>
    );

}

export default Ticket;