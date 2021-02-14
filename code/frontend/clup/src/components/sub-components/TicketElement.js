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
            alert("please reload page to see the changes !")
        }
        axiosGET("QSW", "/scanTicket/" + props.ticket.id, {}, onOk, null, null, true, false, true);
    }

    console.log(props.ticket);

    return (
        <div className={"flexColumnCenter card " + props.ticket.status}>

            <div>TICKET (ID {props.ticket.id}) {props.ticket.status}</div>

            {props.showShop ? <div className="bold"> {props.ticket.shop.name} </div> : ""}

            <div>

                <div className="flexColumnCenter">

                    {props.ticket.scheduledEnteringTime ? <div className="flexRowCenter"> <div className="bold spaceLeft">start</div> {props.ticket.scheduledEnteringTime} </div> : ""}

                    {props.ticket.scheduledExitingTime ? <div className="flexRowCenter"><div className="bold spaceLeft">expected end</div> {props.ticket.scheduledExitingTime} </div> : ""}

                    {props.ticket.enterTime ? <div> <br /> actual enter time: {props.ticket.enterTime} </div> : ""}

                    {props.ticket.exitTime ? <div> actual exit time: {props.ticket.exitTime} </div> : ""}

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