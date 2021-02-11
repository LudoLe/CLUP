import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import axios from 'axios';
import { DEBUG_MODE } from '../constants/debug.js';
import { SSW_URL_API } from '../constants/urlsAPI.js';
import { showError } from '../utils/showError';
import Navigation from './sub-components/Navigation';

const NewShop = () => {

    const [state, setState] = useState({
        name: "",
        description: "",
        position: "",
        image: "",
        shopCapacity: 0,
        timeslotMinutesDuration: 0,
        maxEnteringClientInATimeslot: 0,
        shiftsProto: [],
        quantityOfSshifts: 1
    });

    const history = useHistory;

    const handleChange = (e) => {
        const { id, value } = e.target
        setState(prevState => ({
            ...prevState,
            [id]: value
        }))
    }

    const handleShift = (e) => { //TODO:

        var shiftsProtoNew = {
            ...state.shiftsProto,

        }

        setState(prevState => ({
            ...prevState,
            shiftsProto: shiftsProtoNew
        }))
    }

    const handleChangeFile = (e) => {
        const file = e.target.files[0];
        console.log("FILEEE");
        console.log(file);
        setState(prevState => ({
            ...prevState,
            image: file
        }))
    }

    const handleNewShopSubmit = (e) => {
        e.preventDefault();
        if (document.getElementById("newShop").reportValidity()) {
            sendNewShopToServer();
        }
    }

    const sendNewShopToServer = () => {
        /* const payload = {
            name: state.name,
            description: state.description,
            position: state.position,
            shopCapacity: state.shopCapacity,
            timeslotMinutesDuration: state.timeslotMinutesDuration,
            maxEnteringClientInATimeslot: state.shopCapacity,
            image: state.image, //TODO: togliere
            shiftsProto: []
        }
        for (let i = 0; i < state.shiftsProto.length; i++) {
            const shift = state.shiftsProto[i];
            payload.shiftsProto.push(shift);
        } */
        const onOk = (response) => {
            if (response.id) {
                history.push('/ShopManager/' + response.id);
            }
            else {
                response = {
                    ...response,
                    data: "server was not able to register a new shop"
                }
                showError(response);
            }
        }

        var formData = new FormData(document.getElementById("newShop"));

        //formData.append("image", state.file);
        //formData.append("document", JSON.stringify(payload));

        console.log(formData);

        axios({
            method: 'post',
            url: SSW_URL_API + '/newshop',
            data: formData,
        })
            .then(function (response) {
                console.log("response data:");
                console.log(response.data);
                console.log("response status:");
                console.log(response.status);
                console.log("response headers");
                console.log(response.headers);
                if (response.status === 200) {
                    onOk(response);
                }
                else {
                    DEBUG_MODE ? console.log("Should redirect to /Error") : showError(response);
                }
            })
            .catch(function (error) {
                console.log(error);
                if (error.response) {
                    console.log("error data:");
                    console.log(error.response.data);
                    console.log("error status:");
                    console.log(error.response.status);
                    console.log("error headers");
                    console.log(error.response.headers);
                    DEBUG_MODE ? console.log("redirect to /Error") : showError(error.response);
                }
                else {
                    DEBUG_MODE ? console.log("redirect to /Error") : showError(error.response);
                }
            });
    }

    return (
        <div className="flexColumnCenter">
            <Navigation goBack={true} goHome={true}/>
            <form id="newShop" onSubmit={handleNewShopSubmit} autoComplete="off">
                <div>
                    <label>Shop name:</label>
                    <input onChange={handleChange}
                        value={state.name}
                        id="name"
                        type='text' placeholder="shop name" name="name" required />
                </div>
                <div>
                    <label>Description:</label>
                    <input onChange={handleChange}
                        value={state.description}
                        id="description"
                        type='text' placeholder="This shop is..." name="description" required />
                </div>
                <div>
                    <label>Position:</label>
                    <input onChange={handleChange}
                        value={state.position}
                        id="position"
                        type='text' placeholder="ShopStreet, 5, ShopCity" name="position" required />
                </div>
                <div>
                    <label>Shop capacity:</label>
                    <input onChange={handleChange}
                        value={state.shopCapacity}
                        id="shopCapacity"
                        type='number' placeholder="max number of clients" name="shopCapacity" required />
                </div>
                <div>
                    <label>Duration of a time slot (in minutes):</label>
                    <input onChange={handleChange}
                        value={state.timeslotMinutesDuration}
                        id="timeslotMinutesDuration"
                        type='number' placeholder="3" name="timeslotMinutesDuration" required />
                </div>
                <div>
                    <label>Shop image:</label>
                    <input type="file" onChange={handleChangeFile}
                        id="image"
                        name="image" required />
                </div>
                {/* SHIFTS */}
                <div>
                    <button type="submit">Create Shop</button>
                </div>
            </form>
        </div>
    );
}

export default NewShop;