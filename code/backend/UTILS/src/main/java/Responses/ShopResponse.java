package Responses;

import polimi.it.DL.entities.Shop;

import java.util.Date;

public class ShopResponse{

    private Shop shop;
    private Date queueTime;

    public ShopResponse(){
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Date getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(Date queueTime) {
        this.queueTime = queueTime;
    }
}
