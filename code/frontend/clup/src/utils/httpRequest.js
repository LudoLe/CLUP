import axios from 'axios';
import { AMW_URL_API, QSW_URL_API, SSW_URL_API, ACCESS_TOKEN_NAME } from '../constants/urlsAPI.js';


const BASE_HEADERS = {
    "accept": "application/json",
    "Content-Type": "application/json"
}

const USERNAME = 'username';

export const getSessionToken = () => {
    return localStorage.getItem(ACCESS_TOKEN_NAME);
}

export const setSessionToken = (token) => {
    localStorage.setItem(ACCESS_TOKEN_NAME, token);
}

export const getUsernameLocal = () => {
    return localStorage.getItem(USERNAME);
}

export const setUsernameLocal = (username) => {
    localStorage.setItem(USERNAME, username);
}

export const axiosPOST = (service, url, payload, headers, onOk, on500, onError, useOldSessionToken, setNewSessionToken) => {
    console.log("---------------------------");
    console.log("async http request");

    const config = {
        headers: {
            ...headers,
            ...BASE_HEADERS
        }
    }

    if (useOldSessionToken) {
        console.log("old session-token: " + getSessionToken() + " .");
        config.headers['session-token'] = getSessionToken();
    }

    var URL;

    if (service === "AMW") {
        URL = AMW_URL_API;
    }
    else if (service === "QSW") {
        URL = QSW_URL_API;
    }
    else {
        URL = SSW_URL_API;
    }

    URL += url;

    console.log("POST");
    console.log(URL);
    console.log(payload);
    console.log(config.headers);
    console.log("useOldSessionToken: " + useOldSessionToken);
    console.log("setNewSessionToken: " + setNewSessionToken);

    axios.post(URL, payload, config)
        .then(function (response) {
            console.log("response is: ");
            console.log(response);
            if (response.status === 200) {
                if (setNewSessionToken) {
                    console.log("response.headers is");
                    console.log(response.headers);
                    console.log("new session-token: " + response.headers["session-token"] + " .");
                    setSessionToken(response.headers["session-token"]);
                }
                onOk(response);
            } else if (response.status === 500) {
                if (on500 !== null) {
                    on500(response);
                }
                else {
                    window.location.reload();
                }
            }
            else {
                window.location.href = "/Login";
            }
        })
        .catch(function (error) {
            if (onError !== null) {
                onError(error);
            }
            else {
                window.location.href = "/login";
            }
        });
}

export const axiosGET = (service, url, headers, onOk, on500, onError, useOldSessionToken, setNewSessionToken) => {
    console.log("---------------------------");
    console.log("async http request");

    const config = {
        headers: {
            ...headers,
            ...BASE_HEADERS
        }
    }

    if (useOldSessionToken) {
        console.log("old session-token: " + getSessionToken() + " .");
        config.headers['session-token'] = getSessionToken();
    }

    var URL;

    if (service === "AMW") {
        URL = AMW_URL_API;
    }
    else if (service === "QSW") {
        URL = QSW_URL_API;
    }
    else {
        URL = SSW_URL_API;
    }

    URL += url;

    console.log("GET");
    console.log(URL);
    console.log(config.headers);
    console.log("useOldSessionToken: " + useOldSessionToken);
    console.log("setNewSessionToken: " + setNewSessionToken);

    axios.get(URL, config)
        .then(function (response) {
            console.log("response is: ");
            console.log(response);
            if (response.status === 200) {
                if (setNewSessionToken) {
                    console.log("new session-token: " + response.headers["session-token"] + " .");
                    setSessionToken(response.headers["session-token"]);
                }
                onOk(response);
            } else if (response.status === 500) {
                if (on500 !== null) {
                    on500(response);
                }
                else {
                    window.location.reload();
                }
            }
            else {
                window.location.href = "/Login";
            }
        })
        .catch(function (error) {
            if (onError !== null) {
                onError(error);
            }
            else {
                window.location.href = "/login";
            }
        });
}