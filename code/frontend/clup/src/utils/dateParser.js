export const dateToMinutes = (date) => {
    return Math.ceil((Date.parse(date) / 1000) / 60);
}

export const calcDay = (data) => {
    
    var day = data.toString();
    
    var s = "";

    if (day.contains("1")) {
        s += "Monday; ";
    }
    if (day.contains("2")) {
        s += "Tuesday; ";
    }
    if (day.contains("3")) {
        s += "Wednesday; ";
    }
    if (day.contains("4")) {
        s += "Thursday; ";
    }
    if (day.contains("5")) {
        s += "Friday; ";
    }
    if (day.contains("6")) {
        s += "Saturday; ";
    }
    if (day.contains("7")) {
        s += "Sunday; ";
    }
    if (day.contains("0")) {
        s += "Every Day of the Week;";
    }

    return s;
}