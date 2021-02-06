package prototypes;

import com.google.gson.annotations.Expose;
import polimi.it.DL.entities.Shop;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

public class ShopShift implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Date openingTime;
    private Date closingTime;
    private String day;
    private polimi.it.DL.entities.Shop shop;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Date openingTime) {
        this.openingTime = openingTime;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public polimi.it.DL.entities.Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
