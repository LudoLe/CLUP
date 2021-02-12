import React from 'react';
import { useHistory } from "react-router-dom";
import { axiosGET } from '../../utils/httpRequest';


const TicketElement = (props) => {

    const history = useHistory();

    const handleOnClickTicket = () => {
        history.push('/Ticket/' + props.ticket.id);
    }

    const handleOnClickShop = () => {
        history.push('/Shop/' + props.ticket.shop.id);
    }

    const handleScan = () => {
        const onOk = (response) => {
            console.log(response);
        }
        axiosGET("QSW", "/scanTicket/" + props.ticket.id, {}, onOk, null, null, true, false, true);
    }

    return (
        <div className={"flexColumnCenter card " + props.ticket.status}>

            <div>TICKET (ID {props.ticket.id}) {props.ticket.status}</div>

            {props.showShop ? <div className="bold"> {props.ticket.shop.name} </div> : ""}

            <div>

                <div className="flexColumnCenter">

                    {props.ticket.scheduledEnteringTime ? <div className="flexRowCenter"> <div className="bold spaceLeft">start</div> {props.ticket.scheduledEnteringTime} </div> : ""}

                    {props.ticket.scheduledExitingTime ? <div className="flexRowCenter"><div className="bold spaceLeft">end</div> {props.ticket.scheduledExitingTime} </div> : ""}

                    {props.ticket.enterTime ? <div> Entering Time: {props.ticket.enteringTime} </div> : ""}

                    {props.ticket.exitTime ? <div> Exit Time {props.ticket.exitTime} </div> : ""}

                </div>

            </div>

            {
                props.showShop ?
                    <div className="flexRowCenter">
                        <button onClick={handleOnClickTicket} className="smallButton">Ticket info</button>
                        <div>or</div>
                        <button onClick={handleOnClickShop} className="smallButton">Shop info</button>
                    </div>
                    :
                    <div className="flexRowCenter">
                        <button onClick={handleScan} className="smallButton" > Scan ticket </button>
                    </div>
            }


        </div>
    );
}

export default TicketElement;