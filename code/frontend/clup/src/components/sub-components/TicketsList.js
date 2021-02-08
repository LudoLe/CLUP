import React from 'react';
import TicketElement from './TicketElement';
import { WaveLoading } from 'react-loadingg';

const TicketsList = (props) => {

    const styleLoad = {
        color: 'black',
        position: 'relative'
    };

    if(props.isLoaded){
        return <div className="flexColumnCenter"> {(props.tickets!==null) ? (props.tickets).map(ticket => <TicketElement ticket={ticket} />) : "" } </div>
    }
    else{
        return <div className="flexColumnCenter"> <WaveLoading style={styleLoad}/></div>
    }
}

export default TicketsList;