package polimi.it.DL.entities;

import com.google.gson.annotations.Expose;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(name = "Shop.existWithThatNameAndPosition", query = "SELECT COUNT(t) FROM Shop t WHERE t.name =?1 AND t.position = ?2 "),
})
@Table(name = "`shop`", schema = "clup")
public class Shop implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Expose
    private int id;
    @Expose
    @Column(name = "name")
    private String name;
    @Expose
    @Column(name = "description")
    private String description;
    @Expose
    @Column(name = "position")
    private String position;
    @Expose
    @Column(name = "image")
    private String image;
    @Expose
    @Column(name = "shop_capacity")
    private int shopCapacity;
    @Expose
    @Column(name = "timeslot_minutes_duration")
    private int timeslotMinutesDuration;
    @Expose
    @Column(name = "max_entering_client_in_a_timeslot")
    private int maxEnteringClientInATimeslot;

    @ManyToOne
    @JoinColumn
    private User manager;

    @OneToMany(mappedBy="shop", fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    @OneToMany(mappedBy="shop", fetch = FetchType.LAZY)
    private List<ShopShift> shifts;

    //setters and getters

    public List<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(List<Ticket> tickets){this.tickets=tickets;}

    public List<ShopShift> getShifts() {
        return shifts;
    }
    public void setShifts(List<ShopShift> shifts){this.shifts=shifts;}

    public User getManager() {
        return manager;
    }
    public void setManager(User manager) {
        this.manager = manager;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public int getShopCapacity() {
        return shopCapacity;
    }
    public void setShopCapacity(int shopCapacity) {
        this.shopCapacity = shopCapacity;
    }

    public int getTimeslotMinutesDuration() {
        return timeslotMinutesDuration;
    }
    public void setTimeslotMinutesDuration(int timeslotMinutesDuration) {
        this.timeslotMinutesDuration = timeslotMinutesDuration;
    }

    public int getMaxEnteringClientInATimeslot() {
        return maxEnteringClientInATimeslot;
    }
    public void setMaxEnteringClientInATimeslot(int maxEnteringClientInATimeslot) {
        this.maxEnteringClientInATimeslot = maxEnteringClientInATimeslot;
    }
}

