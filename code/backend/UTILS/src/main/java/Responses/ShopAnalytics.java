package Responses;

import java.util.Date;

public class ShopAnalytics {
    private int peopleInTheShop;
    private int peopleEnqueued;
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
