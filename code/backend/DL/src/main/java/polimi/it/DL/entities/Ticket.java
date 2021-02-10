package polimi.it.DL.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NamedQueries({
        @NamedQuery(name="Ticket.PeopleInTheShopOrEnqueued", query="SELECT COUNT(t) FROM Ticket t WHERE t.status=?1  " +
                " AND t.shop = (SELECT s FROM Shop s WHERE s.id=?2) "),
        @NamedQuery(name="Ticket.existsOnDateForShop", query="SELECT COUNT(t) FROM Ticket t WHERE t.scheduledEnteringTime=?1" +
                " AND t.shop = (SELECT s FROM Shop s WHERE s.id=?2) "),
        @NamedQuery(name="Ticket.existsOnDateForUser", query="SELECT COUNT(t) FROM Ticket t WHERE t.scheduledEnteringTime=?1" +
                " AND t.user = (SELECT u FROM User u WHERE u.id=?2) "),
        @NamedQuery(name="Ticket.getByTimeForShop", query="SELECT q FROM Ticket q WHERE q.scheduledEnteringTime=?1"+
                " AND q.shop = (SELECT s FROM Shop s WHERE s.id=?2) "),
        @NamedQuery(name="Ticket.findForUser", query="SELECT q FROM Ticket q WHERE q.user = (SELECT u FROM User u WHERE u.username=?1) "),
        @NamedQuery(name="Ticket.listOrderedForShop", query="SELECT t FROM Ticket t WHERE  t.shop = (SELECT s FROM Shop s WHERE s.id=?1) " + " ORDER BY t.scheduledExitingTime ASC"),
        @NamedQuery(name="Ticket.listOrderedForUser", query="SELECT t FROM Ticket t WHERE  t.user = (SELECT s FROM User s WHERE s.id=?2) " + " ORDER BY t.scheduledExitingTime ASC"),
        @NamedQuery(name="Questionnaire.listPastForShop", query="SELECT q FROM Ticket q WHERE q.scheduledEnteringTime < CURRENT_DATE AND q.shop = (SELECT s FROM Shop s WHERE s.id=?2) ORDER BY q.scheduledEnteringTime ASC"),
        @NamedQuery(name="Questionnaire.listPastForUser", query="SELECT q FROM Ticket q WHERE q.scheduledEnteringTime < CURRENT_DATE AND q.user = (SELECT s FROM User s WHERE s.id=?2) ORDER BY q.scheduledEnteringTime ASC"),
})
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
    @Column(name="status")
    private String status;
    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="enter_time")
    private Date enterTime;
    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="time_to_reach_the_shop")
    private Date timeToReachTheShop;
    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="exit_time")
    private Date exitTime;
    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="expected_duration")
    private Date expectedDuration;
    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="scheduled_entering_time")
    private Date scheduledEnteringTime;
    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="scheduled_exiting_time")
    private Date scheduledExitingTime;
    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="arrival_time")
    private Date arrivalTime;

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

    public Date getTimeToReachTheShop() {
        return timeToReachTheShop;
    }
    public void setTimeToReachTheShop(Date timeToReachTheShop) {
        this.timeToReachTheShop = timeToReachTheShop;
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

    public Date getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

}

