package polimi.it.DataLayer.Entities;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "shop_shift", schema = "clup", catalog = "")
public class ShopShiftEntity {
    private int id;
    private Time openingTime;
    private Time closingTime;
    private String day;
    private ShopEntity shopByShopId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "opening_time", nullable = false)
    public Time getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Time openingTime) {
        this.openingTime = openingTime;
    }

    @Basic
    @Column(name = "closing_time", nullable = false)
    public Time getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Time closingTime) {
        this.closingTime = closingTime;
    }

    @Basic
    @Column(name = "day", nullable = false, length = 45)
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopShiftEntity that = (ShopShiftEntity) o;

        if (id != that.id) return false;
        if (openingTime != null ? !openingTime.equals(that.openingTime) : that.openingTime != null) return false;
        if (closingTime != null ? !closingTime.equals(that.closingTime) : that.closingTime != null) return false;
        if (day != null ? !day.equals(that.day) : that.day != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (openingTime != null ? openingTime.hashCode() : 0);
        result = 31 * result + (closingTime != null ? closingTime.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id", nullable = false)
    public ShopEntity getShopByShopId() {
        return shopByShopId;
    }

    public void setShopByShopId(ShopEntity shopByShopId) {
        this.shopByShopId = shopByShopId;
    }
}
