package prototypes;

import java.io.Serializable;
import java.util.Date;

public class EnqueueData implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Date permanence;
    private Date timeToGetToTheShop;
    private int shopid;


    public Date getPermanence() {
        return permanence;
    }

    public void setPermanence(Date permanence) {
        this.permanence = permanence;
    }

    public Date getTimeToGetToTheShop() {
        return timeToGetToTheShop;
    }

    public void setTimeToGetToTheShop(Date timeToGetToTheShop) {
        this.timeToGetToTheShop = timeToGetToTheShop;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }
}
