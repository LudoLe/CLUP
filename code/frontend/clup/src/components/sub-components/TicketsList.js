import React from 'react';
import TicketElement from './TicketElement';
import { WaveLoading } from 'react-loadingg';

const TicketsList = (props) => {

    if (props.isLoaded) {
        return (
            <div className="flexColumnCenter">
                <div className="bold yourTicketTitle">YOUR TICKETS:</div>
                <div class="tooltip">info
                    <span class="tooltiptext">
                        In this section you can see all your tickets <br />
                        Each ticket contains the following informations: <br />
                        - name of the shop the ticket is enqueued to <br />
                        - ticket id and status (also the color of the border is based on the status) <br/>
                        - start the starting time to scan the ticket to enter the shop<br />
                        - expected end: the expected time you'll scan the ticket to exit the shop<br />
                        - if available the time you scanned the ticket to enter the shop <br />
                        - if available the time you scanned the ticket to exit the shop <br />
                        - two buttons to check more informations about the ticket or about the shop <br />
                    </span>
                </div>
                {(props.tickets !== null && props.tickets.length !== 0) ?
                    (props.tickets).map(ticket => <TicketElement key={ticket.id} ticket={ticket} showShop={true} />)
                    :
                    <div>You have no tickets.</div>
                }
            </div>
        );
    }
    else {
        return <div className="flexColumnCenter"> <WaveLoading style={{ position: "relative" }} /></div>
    }
}

export default TicketsList;