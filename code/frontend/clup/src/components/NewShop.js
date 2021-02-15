import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import { axiosPOST} from '../utils/httpRequest';
import { showError } from '../utils/showError';
import Navigation from './sub-components/Navigation';

const NewShop = () => {

    const [state, setState] = useState({
        name: "",
        description: "",
        position: "",
        image: "no image",
        shopCapacity: 1,
        timeslotMinutesDuration: 3,
        maxEnteringClientInATimeslot: 0,
        shiftsProto: [
            {
                day: 0,
                openingTime: new Date(),
                closingTime: new Date(),
            }
        ]
    });

    const history = useHistory();

    const handleChange = (e) => {
        const { id, value } = e.target
        setState(prevState => ({
            ...prevState,
            [id]: value
        }))
    }

    const handleNewShopSubmit = (e) => {
        e.preventDefault();
        if (document.getElementById("newShop").reportValidity()) {
            sendNewShopToServer();
        }
    }

    const sendNewShopToServer = () => {
        const payload = {
            name: state.name,
            description: state.description,
            position: state.position,
            shopCapacity: parseInt(state.shopCapacity),
            timeslotMinutesDuration: parseInt(state.timeslotMinutesDuration),
            maxEnteringClientInATimeslot: parseInt(state.shopCapacity),
            image: state.image,
            shiftsProto: state.shiftsProto
        }
        const onOk = (response) => {
            console.log("RESPOOOOOONSEEEE");
            console.log(response);
            if (response) {
                history.push('/ShopManager/' + response.data);
            }
            else {
                response = {
                    ...response,
                    data: "server was not able to register a new shop"
                }
                showError(response);
            }
        }
        axiosPOST("SSW", "/newshop", payload, {}, onOk, null, null, true, false, true);
    }

    return (
        <div className="flexColumnCenter">
            <Navigation goBack={true} goHome={true} />
            <div class="tooltip">info
                    <span class="tooltiptext">
                    This page consists of a form to create a new shop.<br/>
                    The "shop capacity" field represent the amount of persone that the store can host at the same time.<br/>
                    The "Duration of a time slot" field is one of the parameters used to calculate a queue. Usually 3 is a good value. To have more information please read the algorithm section of the DD document.
                </span>
            </div>
            <form className="card flexColumnCenter" id="newShop" onSubmit={handleNewShopSubmit} autoComplete="off">
                <div className="flexColumnCenter bold">
                    <label>Shop name:</label>
                    <input className="inputTextField" onChange={handleChange}
                        value={state.name}
                        id="name"
                        type='text' placeholder="shop name" name="name" required />
                </div>
                <div className="flexColumnCenter bold">
                    <label>Description:</label>
                    <input className="inputTextField" onChange={handleChange}
                        value={state.description}
                        id="description"
                        type='text' placeholder="This shop is..." name="description" required />
                </div>
                <div className="flexColumnCenter bold">
                    <label>Position:</label>
                    <input className="inputTextField" onChange={handleChange}
                        value={state.position}
                        id="position"
                        type='text' placeholder="ShopStreet, 5, ShopCity" name="position" required />
                </div>
                <div className="flexColumnCenter bold">
                    <label>Shop capacity:</label>
                    <input className="inputTextField" onChange={handleChange}
                        value={state.shopCapacity}
                        id="shopCapacity"
                        type='number' placeholder="max number of clients" name="shopCapacity" required />
                </div>
                <div className="flexColumnCenter bold">
                    <label>Duration of a time slot (in minutes):</label>
                    <input className="inputTextField" onChange={handleChange}
                        value={state.timeslotMinutesDuration}
                        id="timeslotMinutesDuration"
                        type='number' placeholder="3" name="timeslotMinutesDuration" required />
                </div>
                <div>
                    <button type="submit">Create Shop</button>
                </div>
            </form>
        </div>
    );
}

export default NewShop;