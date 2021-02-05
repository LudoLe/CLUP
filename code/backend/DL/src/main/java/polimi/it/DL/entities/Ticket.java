package polimi.it.DL.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


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
    @Temporal(TemporalType.TIMESTAMP)
    private Date enterTime;
    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    private Date exitTime;
    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    private Date expectedDuration;
    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledEnteringTime;
    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledExitingTime;

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

    public Date getEnterTime() {
        return enterTime;
    }
    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public Date getExitTime() {
        return exitTime;
    }
    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public Date getExpectedDuration() {
        return expectedDuration;
    }
    public void setExpectedDuration(Date expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    public Date getScheduledEnteringTime() {
        return scheduledEnteringTime;
    }
    public void setScheduledEnteringTime(Date scheduledEnteringTime) {
        this.scheduledEnteringTime = scheduledEnteringTime;
    }

    public Date getScheduledExitingTime() {
        return scheduledExitingTime;
    }
    public void setScheduledExitingTime(Date scheduledExitingTime) {
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
