import React, { useState, useEffect } from 'react';
import { WaveLoading } from 'react-loadingg';
import { calcDay } from '../utils/dateParser.js';
import { axiosGET } from '../utils/httpRequest.js';
import Queue from './sub-components/Queue.js';
import Navigation from './sub-components/Navigation';
import GoToButton from './sub-components/GoToButton';

const ShopManager = (props) => {

    const [state, setState] = useState({
        isLoadedShop: false,
        shop: null,
        isLoadedQueueMaxDuration: false,
        queueMaxDuration: null,
        shopId: props.match.params.id,
        queueTrigger: false,
        tickets: null
    });

    //fetch shop info
    useEffect(() => {
        const onOk = (response) => {
            setState(prevState => {
                return {
                    ...prevState,
                    isLoadedShop: true,
                    shop: response.data.shop,
                    tickets: response.data.tickets
                }
            });
        }
        axiosGET("SSW", ("/shopDetail/" + state.shopId), {}, onOk, null, null, true, false, true);
    }, [state.shopId]);

    //fetch queue expected duration
    useEffect(() => {
        const onOk = (response) => {
            setState(prevState => {
                return {
                    ...prevState,
                    isLoadedQueueMaxDuration: true,
                    queueMaxDuration: response.data
                }
            });
        }
        axiosGET("QSW", ("/queueInfo/" + state.shopId), {}, onOk, null, null, true, false, true);
    }, [state.shopId]);

    const triggerQueue = () => {
        setState(prevState => ({
            ...prevState,
            queueTrigger: !prevState.queueTrigger
        }));
    }

    return (
        <div className="flexColumnCenter">

            <Navigation goBack={true} goHome={true} />

            {state.isLoadedShop ?

                <div className="flexColumnCenter card">

                    <div className="flexColumnCenter">

                        <div> {state.shop.name} </div>

                        <div> {state.shop.image} </div>

                        <div> {state.shop.description} </div>

                        <div> {state.shop.position} </div>

                    </div>

                    <div className="flexColumnCenter">

                        {state.isLoadedQueueMaxDuration ?
                            <div> Expected Queue duration: {state.queueMaxDuration}</div>
                            :
                            <WaveLoading style={{ position: "relative" }} />
                        }

                    </div>

                    {state.shop.shifts ?
                        (state.shop.shifts).map(shift =>
                            <div className="flexColumnCenter">
                                <div> Day: {calcDay(shift.day)}</div>
                                <div>{shift.openingTime} - {shift.closingTime}</div>
                            </div>
                        )
                        :
                        ""
                    }

                </div>
                :
                <WaveLoading style={{ position: "relative" }} />
            }

            {
                state.isLoadedShop ?
                    <div className="flexColumnCenter">
                        < button onClick={triggerQueue}>{state.queueTrigger ? "Hide" : "Show"} tickets</button>
                        {
                            state.queueTrigger ?
                                <Queue tickets={state.tickets} />
                                :
                                ""
                        }
                    </div>
                    :
                    ""
            }

        </div >
    );
}

export default ShopManager;