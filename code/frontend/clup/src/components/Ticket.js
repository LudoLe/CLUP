import React, { useState, useEffect } from 'react';
import { WaveLoading } from 'react-loadingg';
import history from '../utils/history.js';
import { axiosGET } from '../utils/httpRequest.js';

const Ticket = (props) => {

    const [state, setState] = useState({
        isLoadedTicket: false,
        ticket: null,
        ticketId: props.match.params.id
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
        axiosGET("SSW", ("/ticketDetail/" + state.ticketId), {}, onOk, null, null, true, false, true);
    }, [state.ticketId]);

    const handleBack = () => {
        history.goBack();
    }

    const handleDequeue = () => {
        //TODO:
    }


    return (
        <div className="flexColumnCenter">
            <button onClick={handleBack}> Go back </button>
            {state.isLoadedTicket ?
                <div className="flexColumnCenter">
                    {/* TODO: display shop info */}
                    <h3> Ticket id: {state.ticket.id} </h3>
                    <div> Status: {state.ticket.status} </div>
                    {/* TODO: <div> Entering Time: {state.ticket.enteringTime} </div>
                    <div> Exit Time {state.ticket.exitTime} </div>
                    <div> Scheduled Entering Time: {state.ticket.scheduledEnteringTime} </div>
                    <div> Scheduled Exiting Time; {state.ticket.scheduledExitingTime} </div>
                    <div> Shop Name {state.ticket.shop.name} </div>
                    <div> Shop Position {state.ticket.shop.position} </div>
                    <div> Shop Image {state.ticket.shop.image} </div> */}
                    <div> Time To Reach The Shop: {Math.ceil((Date.parse(state.ticket.timeToReachTheShop) / 1000) / 60)} minutes </div>
                    <div> Expected Duration {Math.ceil((Date.parse(state.ticket.expectedDuration) / 1000) / 60)} minutes </div>
                    <button onClick={handleDequeue}> Dequeue </button>
                </div>
                :
                <WaveLoading />
            }
        </div>
    );

}

export default Ticket;