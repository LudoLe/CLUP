import React, { useState, useEffect } from 'react';
import { WaveLoading } from 'react-loadingg';
import { calcDay } from '../utils/dateParser.js';
import { axiosGET } from '../utils/httpRequest.js';
import Queue from './sub-components/Queue.js';
import Navigation from './sub-components/Navigation';

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

            <div class="tooltip">info
                    <span class="tooltiptext">
                    This is the page of a shop: <br />
                    In this page you can see the following informations:<br/>
                    -shop name<br/>
                    -shop description <br/>
                    -shop position <br/>
                    -button to show all the tickets of the shop <br/>
                    -informations about the tickets (...) <br/>
                    -the "scan ticket" button: this button is here to show what would happen if a customer scanned the ticket to enter or exit a shop
                </span>
            </div>

            {state.isLoadedShop ?

                <div className="flexColumnCenter card">

                    <div className="flexColumnCenter">

                        <div className="bold"> {state.shop.name} </div>

                        <div> description: {state.shop.description} </div>

                        <div> position: {state.shop.position} </div>

                    </div>

                    <div className="flexColumnCenter">

                        {state.isLoadedQueueMaxDuration ?
                            <div className="littleMargin"> <div className="bold"> Expected Queue duration </div> {state.queueMaxDuration} </div>
                            :
                            <WaveLoading style={{ position: "relative" }} />
                        }

                    </div>

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
                                <Queue shopId={state.shopId} tickets={state.tickets} />
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