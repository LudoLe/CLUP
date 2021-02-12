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
            <div className="flexColumnCenter">
                <label>Insert JSON to parse:</label>
                <input onChange={getJSON} type="text"></input>
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