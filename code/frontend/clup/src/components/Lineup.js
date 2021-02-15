import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import { axiosPOST } from '../utils/httpRequest';
import { showError } from '../utils/showError';
import Navigation from './sub-components/Navigation';

const Lineup = (props) => {

    const [state, setState] = useState({
        ttr: "",
        duration: "",
        shopId: parseInt(props.match.params.id)
    });

    const history = useHistory();

    const handleChange = (e) => {
        const { id, value } = e.target
        setState(prevState => ({
            ...prevState,
            [id]: value
        }))
    }

    const handleLineupSubmit = (e) => {
        e.preventDefault();
        if (document.getElementById("lineUp").reportValidity()) {
            sendLineUpToServer();
        }
    }

    const sendLineUpToServer = () => {

        var ttr = new Date(state.ttr*60*1000);

        var duration = new Date(state.duration*60*1000);

        const payload = {
            "timeToGetToTheShop": ttr,
            "permanence": duration,
            "shopid": state.shopId
        }
        const onOk = (response) => {
            if (response.data.id) {
                history.push('/Ticket/' + response.data.id);
            }
            else {
                response = {
                    ...response,
                    data: "server was not ble to enqueue you (Lineup.js message, because response has no body)"
                }
                showError(response);
            }
        }
        axiosPOST("QSW", "/enqueue", payload, {}, onOk, null, null, true, false, true);
    }

    return (
        <div className="flexColumnCenter">
            <Navigation goBack={true} goHome={true}/>
            <div class="tooltip">Info
                    <span class="tooltiptext">
                        This page is used to enqueue in a shop. <br />
                        You just need to indicate the time you'll need to reach the shop and the expected duration of you permanence inside the shop < br/>
                        These two value will be used to position you in the queue correctly, to have a great expirience with our application please insert true values and respect them.
                </span>
            </div>
            <form className="flexColumnCenter card" id="lineUp" onSubmit={handleLineupSubmit} autoComplete="off">
                <div className="flexColumnCenter littleMargin">
                    <label className="bold">Time to reach the shop (in minutes)</label>
                    <input className="inputTextField" onChange={handleChange}
                        value={state.ttr}
                        id="ttr"
                        type='number' placeholder="10" name="ttr" required /> 
                </div>
                <div className="flexColumnCenter littleMargin">
                    <label className="bold">Duration of the permanence (in minutes)</label>
                    <input className="inputTextField" onChange={handleChange}
                        value={state.duration}
                        id="duration"
                        type='number' placeholder="15" name="duration" required />
                </div>
                <div>
                    <button type="submit">Lineup</button>
                </div>
            </form>
        </div>
    );
}

export default Lineup;