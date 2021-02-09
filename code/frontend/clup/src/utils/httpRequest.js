import axios from 'axios';
import { AMW_URL_API, QSW_URL_API, SSW_URL_API, ACCESS_TOKEN_NAME } from '../constants/urlsAPI.js';
import { DEBUG_MODE } from '../constants/debug.js';
import history from '../utils/history'; 

const BASE_HEADERS_POST = {
    "accept": "application/json",
    "Content-Type": "application/json"
}

const BASE_HEADERS_GET = {
    "accept": "application/json"
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

const goToErrorPage = (error)=>{
    sessionStorage.setItem("error", JSON.stringify(error));
    history.push("/Error");
}

export const axiosPOST = (service, url, payload, headers, onOk, on500, onError, useOldSessionToken, setNewSessionToken, useUsername) => {
    console.log("############################################");
    console.log("ASYNC HTTP REQUEST:");

    const config = {
        headers: {
            ...headers,
            ...BASE_HEADERS_POST
        }
    }

    if (useOldSessionToken) {
        console.log("old session-token: " + getSessionToken() + " .");
        config.headers['session-token'] = getSessionToken();
    }
    if (useUsername) {
        console.log("username: " + getUsernameLocal());
        config.headers['username'] = getUsernameLocal();
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
    console.log("useUsername: " + useUsername);
    console.log("--------------------------------------------");
    console.log("HTTP RESPONSE:");

    axios.post(URL, payload, config)
    .then(function (response) {
        console.log("response data:");
        console.log(response.data);
        console.log("response status:");
        console.log(response.status);
        console.log("response headers");
        console.log(response.headers);
        if (response.status === 200) {
            if (setNewSessionToken) {
                console.log("new session-token: " + response.headers["session-token"] + " .");
                setSessionToken(response.headers["session-token"]);
            }
            onOk(response);
        }
        else {
            DEBUG_MODE ? console.log("(correct, but not 200:) Should redirect to /Error") : goToErrorPage(response);
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
            if (on500 !== null && error.response.status === 500) {
                on500(error);
            }
            else {
                DEBUG_MODE ? console.log("(on500:) redirect to /Error") : goToErrorPage(error.response);
            }
        }
        else {
            if (onError !== null) {
                onError(error);
            }
            else {
                DEBUG_MODE ? console.log("(onError:) redirect to /Error") : goToErrorPage(error.response);
            }
        }
    });
}

export const axiosGET = (service, url, headers, onOk, on500, onError, useOldSessionToken, setNewSessionToken, useUsername) => {
    console.log("############################################");
    console.log("ASYNC HTTP REQUEST:");

    const config = {
        headers: {
            ...headers,
            ...BASE_HEADERS_GET
        }
    }

    if (useOldSessionToken) {
        config.headers['session-token'] = getSessionToken();
    }
    if (useUsername) {
        config.headers['username'] = getUsernameLocal();
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
    console.log("useUsername: " + useUsername);
    console.log("--------------------------------------------");
    console.log("HTTP RESPONSE:");

    axios.get(URL, config)
        .then(function (response) {
            console.log("response data:");
            console.log(response.data);
            console.log("response status:");
            console.log(response.status);
            console.log("response headers");
            console.log(response.headers);
            if (response.status === 200) {
                if (setNewSessionToken) {
                    console.log("new session-token: " + response.headers["session-token"] + " .");
                    setSessionToken(response.headers["session-token"]);
                }
                onOk(response);
            }
            else {
                DEBUG_MODE ? console.log("(correct, but not 200:) Should redirect to /Error") : goToErrorPage(response);
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
                if (on500 !== null && error.response.status === 500) {
                    on500(error);
                }
                else {
                    DEBUG_MODE ? console.log("(on500:) redirect to /Error") : goToErrorPage(error.response);
                }
            }
            else {
                if (onError !== null) {
                    onError(error);
                }
                else {
                    DEBUG_MODE ? console.log("(onError:) redirect to /Error") : goToErrorPage(error.response);
                }
            }
        });
}