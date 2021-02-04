package polimi.it.DL.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "`ticket`", schema = "clup")
public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Expose
    private int id;
    @Expose
    private String status;
    @Expose
    @Temporal(TemporalType.TIME)
    private Time enterTime;
    @Expose
    @Temporal(TemporalType.TIME)
    private Time exitTime;
    @Expose
    @Temporal(TemporalType.TIME)
    private Time expectedDuration;
    @Expose
    @Temporal(TemporalType.TIME)
    private Time scheduledEnteringTime;
    @Expose
    @Temporal(TemporalType.TIME)
    private Time scheduledExitingTime;
    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id", nullable = false)
    private Shop shop;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Time getEnterTime() {
        return enterTime;
    }
    public void setEnterTime(Time enterTime) {
        this.enterTime = enterTime;
    }

    public Time getExitTime() {
        return exitTime;
    }
    public void setExitTime(Time exitTime) {
        this.exitTime = exitTime;
    }

    public Time getExpectedDuration() {
        return expectedDuration;
    }
    public void setExpectedDuration(Time expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    public Time getScheduledEnteringTime() {
        return scheduledEnteringTime;
    }
    public void setScheduledEnteringTime(Time scheduledEnteringTime) {
        this.scheduledEnteringTime = scheduledEnteringTime;
    }

    public Time getScheduledExitingTime() {
        return scheduledExitingTime;
    }
    public void setScheduledExitingTime(Time scheduledExitingTime) {
        this.scheduledExitingTime = scheduledExitingTime;
    }


    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
