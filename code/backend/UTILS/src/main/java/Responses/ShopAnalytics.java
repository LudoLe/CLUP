package Responses;

import com.google.gson.annotations.Expose;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;
import java.util.Date;

public class ShopAnalytics  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Expose
    private int peopleInTheShop;
    @Expose
    private int peopleEnqueued;
    @Expose
    private Date estimatedDurationOfTheQueue;

    public int getPeopleInTheShop() {
        return peopleInTheShop;
    }

    public void setPeopleInTheShop(int peopleInTheShop) {
        this.peopleInTheShop = peopleInTheShop;
    }

    public int getPeopleEnqueued() {
        return peopleEnqueued;
    }

    public void setPeopleEnqueued(int peopleEnqueued) {
        this.peopleEnqueued = peopleEnqueued;
    }

    public Date getEstimatedDurationOfTheQueue() {
        return estimatedDurationOfTheQueue;
    }

    public void setEstimatedDurationOfTheQueue(Date estimatedDurationOfTheQueue) {
        this.estimatedDurationOfTheQueue = estimatedDurationOfTheQueue;
    }
}
