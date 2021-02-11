package Responses;

import com.google.gson.annotations.Expose;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;
import java.util.Date;

public class ShopAnalytics  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Expose
    private Long peopleInTheShop;
    @Expose
    private Long peopleEnqueued;
    @Expose
    private Date estimatedDurationOfTheQueue;

    public Long getPeopleInTheShop() {
        return peopleInTheShop;
    }

    public void setPeopleInTheShop(Long peopleInTheShop) {
        this.peopleInTheShop = peopleInTheShop;
    }

    public Long getPeopleEnqueued() {
        return peopleEnqueued;
    }

    public void setPeopleEnqueued(Long peopleEnqueued) {
        this.peopleEnqueued = peopleEnqueued;
    }

    public Date getEstimatedDurationOfTheQueue() {
        return estimatedDurationOfTheQueue;
    }

    public void setEstimatedDurationOfTheQueue(Date estimatedDurationOfTheQueue) {
        this.estimatedDurationOfTheQueue = estimatedDurationOfTheQueue;
    }
}
