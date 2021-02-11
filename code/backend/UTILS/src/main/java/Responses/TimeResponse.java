package Responses;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class TimeResponse {
    private static final long serialVersionUID = 1L;

    @Expose
    private Date enteringTime;
    @Expose
    private Date timeForShopping;

    public TimeResponse(Date timeResponse, Date enteringTime){
        timeForShopping=timeResponse;
        this.enteringTime= enteringTime;
    }

    public Date getEnteringTime() {
        return enteringTime;
    }

    public void setEnteringTime(Date enteringTime) {
        this.enteringTime = enteringTime;
    }

    public Date getTimeForShopping() {
        return timeForShopping;
    }

    public void setTimeForShopping(Date timeForShopping) {
        this.timeForShopping = timeForShopping;
    }
}
