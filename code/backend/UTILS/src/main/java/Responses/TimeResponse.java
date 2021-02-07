package Responses;

import java.util.Date;

public class TimeResponse {

    Date enteringTime;
    Date timeForShopping;

    public TimeResponse(Date timeResponse, Date enteringTime){
        timeForShopping=timeResponse;
        this.enteringTime= enteringTime;
    }

}
