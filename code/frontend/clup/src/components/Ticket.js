import React, { useState, useEffect } from 'react';
import { WaveLoading } from 'react-loadingg';
import { axiosGET } from '../utils/httpRequest.js';

const Ticket = (props) => {

    const [state, setState] = useState({
        isLoadedTicket: false,
        ticket: null,
        param: props.match.id
    });

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
        axiosGET("SSW", ("/TicketDetail/" + this.props.match.id), {}, onOk, onOk, null, true, false, true);
    }, []);

    const handleBack = () =>{

    }

    const handleEnqueue = () =>{
        
    }

    if (state.isLoaded) {
        return (
            <div className="flexColumnCenter">
                <button onClick={handleBack}> Go back </button>
                <h3> Ticket id: {state.ticket.id} </h3>
                <div> Status: {state.ticket.status} </div>
                <div> Entering Time: {state.ticket.enteringTime} </div>
                <div> Exit Time {state.ticket.exitTime} </div>
                <div> Scheduled Entering Time: {state.ticket.scheduledEnteringTime} </div>
                <div> Scheduled Exiting Time; {state.ticket.scheduledExitingTime} </div>
                <div> Shop Name {state.ticket.shop.name} </div>
                <div> Shop Position {state.ticket.shop.position} </div>
                <div> Shop Image {state.ticket.shop.image} </div>
                <div> Time To Reach The Shop: {state.ticket.timeToReachTheShop} </div>
                <div> Expected Duration {state.ticket.expectedDuration} </div>
                <button onClick={handleEnqueue}> enqueue </button>
            </div>
        );
    }
    else {
        return <div className="flexColumnCenter">
            <WaveLoading />
        </div>
    }
}

export default Ticket;