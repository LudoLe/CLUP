import '../css/Error.css';

export const showError = (error) =>{

    let ErrorBackground = document.createElement("div");
    ErrorBackground.classList.add("errorBackground");
    ErrorBackground.id = "error";


    let Error = document.createElement("div");
    Error.classList.add("flexColumnCenter");
    Error.classList.add("error");
 
    let h3_1 = document.createElement("h3");
    h3_1.innerHTML = "ERROR!";
    
    let h3_2 = document.createElement("h3");
    h3_2.innerHTML = "STATUS CODE: " + error.status;
    
    let h3_3 = document.createElement("h3");
    h3_3.innerHTML = "ERROR MESSAGE: " + error.data;
    
    let buttonToLogin = document.createElement("button");
    buttonToLogin.innerHTML = "Go to Login";
    const handleToLogin = () =>{
        window.location.href = "/";
    }
    buttonToLogin.addEventListener("click", handleToLogin);

    let buttonClose = document.createElement("button");
    buttonClose.innerHTML = "Close";
    const handleClose = () =>{
        document.getElementById("App").removeChild(document.getElementById("error"));
    }
    buttonClose.addEventListener("click", handleClose);

    Error.appendChild(h3_1);
    Error.appendChild(h3_2);
    Error.appendChild(h3_3);
    Error.appendChild(buttonToLogin);
    Error.appendChild(buttonClose);

    ErrorBackground.appendChild(Error);

    document.getElementById("App").appendChild(ErrorBackground);
}