package prototypes;

import java.io.Serializable;
import java.util.Date;

public class EnqueueData implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Date enteringTime;
    private Date exitingTime;
    private int userid;
    private int shopid;


    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Date getExitingTime() {
        return exitingTime;
    }

    public void setExitingTime(Date exitingTime) {
        this.exitingTime = exitingTime;
    }

    public Date getEnteringTime() {
        return enteringTime;
    }

    public void setEnteringTime(Date enteringTime) {
        this.enteringTime = enteringTime;
    }
}
