package polimi.it.DL.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "`shop_shift`", schema = "clup")
public class ShopShift implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Expose
    private int id;
    @Expose
    @Temporal(TemporalType.TIME)
    private Time openingTime;
    @Expose
    @Temporal(TemporalType.TIME)
    private Time closingTime;
    @Expose
    private String day;
    @Expose
    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id", nullable = false)
    private Shop shop;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Time getOpeningTime() {
        return openingTime;
    }
    public void setOpeningTime(Time openingTime) {
        this.openingTime = openingTime;
    }

    public Time getClosingTime() {
        return closingTime;
    }
    public void setClosingTime(Time closingTime) {
        this.closingTime = closingTime;
    }

    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }

    public Shop getShop() {return shop;}
    public void setShop(Shop shop) {this.shop = shop; }
}
