import React, { useState } from 'react';
import GoBackButton from './sub-components/GoBackButton';
import TimeSlot from './sub-components/TimeSlot';

const ParseTimeLine = (props) => {

    const [state, setState] = useState(null);

    const getJSON = (e) => {
        setState(JSON.parse(e.target.value));
    }

    return (
        <div className="flexColumnCenter">
            <GoBackButton />
            <div class="tooltip">info
                    <span class="tooltiptext">
                    This page will not be available in any official release.<br/>
                    This page is useful for debugging, in fact, it allows you to paste the terminal output printed in JSON format from the "QSW" module. <br/>
                    The JSON data represents the time line of the tickets in the queue and by pasting it in the textbox of this page, you'll be able to graphically visualize the time line and the arrangement of the tickets.
                </span>
            </div>
            <div className="flexColumnCenter card">
                <label className="bold littleMargin">Paste JSON here:</label>
                <input className="inputTextField " onChange={getJSON} type="text"></input>
            </div>
            {state != null ?
                <div>
                    {(state).map(timeslot => <TimeSlot key={timeslot.id} timeSlot={timeslot}/>)}
                </div>
                : ""}
        </div>
    );
}

export default ParseTimeLine;