package polimi.it.DataLayer.Entities;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "ticket", schema = "clup", catalog = "")
public class TicketEntity {
    private int id;
    private String status;
    private Time enterTime;
    private Time exitTime;
    private Time expectedDuration;
    private Time scheduledEnteringTime;
    private Time scheduledExitingTime;
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
    @Column(name = "status", nullable = false, length = 45)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "enter_time", nullable = true)
    public Time getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Time enterTime) {
        this.enterTime = enterTime;
    }

    @Basic
    @Column(name = "exit_time", nullable = true)
    public Time getExitTime() {
        return exitTime;
    }

    public void setExitTime(Time exitTime) {
        this.exitTime = exitTime;
    }

    @Basic
    @Column(name = "expected_duration", nullable = false)
    public Time getExpectedDuration() {
        return expectedDuration;
    }

    public void setExpectedDuration(Time expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    @Basic
    @Column(name = "scheduled_entering_time", nullable = true)
    public Time getScheduledEnteringTime() {
        return scheduledEnteringTime;
    }

    public void setScheduledEnteringTime(Time scheduledEnteringTime) {
        this.scheduledEnteringTime = scheduledEnteringTime;
    }

    @Basic
    @Column(name = "scheduled_exiting_time", nullable = true)
    public Time getScheduledExitingTime() {
        return scheduledExitingTime;
    }

    public void setScheduledExitingTime(Time scheduledExitingTime) {
        this.scheduledExitingTime = scheduledExitingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketEntity that = (TicketEntity) o;

        if (id != that.id) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (enterTime != null ? !enterTime.equals(that.enterTime) : that.enterTime != null) return false;
        if (exitTime != null ? !exitTime.equals(that.exitTime) : that.exitTime != null) return false;
        if (expectedDuration != null ? !expectedDuration.equals(that.expectedDuration) : that.expectedDuration != null)
            return false;
        if (scheduledEnteringTime != null ? !scheduledEnteringTime.equals(that.scheduledEnteringTime) : that.scheduledEnteringTime != null)
            return false;
        if (scheduledExitingTime != null ? !scheduledExitingTime.equals(that.scheduledExitingTime) : that.scheduledExitingTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (enterTime != null ? enterTime.hashCode() : 0);
        result = 31 * result + (exitTime != null ? exitTime.hashCode() : 0);
        result = 31 * result + (expectedDuration != null ? expectedDuration.hashCode() : 0);
        result = 31 * result + (scheduledEnteringTime != null ? scheduledEnteringTime.hashCode() : 0);
        result = 31 * result + (scheduledExitingTime != null ? scheduledExitingTime.hashCode() : 0);
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
