package prototypes;


import java.io.Serializable;

public class Shop implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String description;
    private String position;
    private String image;
    private int shopCapacity;
    private int timeslotMinutesDuration;
    private int maxEnteringClientInATimeslot;

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
