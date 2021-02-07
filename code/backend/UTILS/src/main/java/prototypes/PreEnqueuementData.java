package prototypes;

import java.io.Serializable;
import java.util.Date;

public class PreEnqueuementData implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int timeToGetToTheShop;
    private int shopId;


    public int getTimeToGetToTheShop() {
        return timeToGetToTheShop;
    }

    public void setTimeToGetToTheShop(int timeToGetToTheShop) {
        this.timeToGetToTheShop = timeToGetToTheShop;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}
