package polimi.it.DataLayer.Entities;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "shop", schema = "clup", catalog = "")
public class ShopEntity {
    private int id;
    private String name;
    private String description;
    private String position;
    private byte[] image;
    private int shopCapacity;
    private int timeslotMinutesDuration;
    private int maxEnteringClientInATimeslot;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 250)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "position", nullable = false, length = 100)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "image", nullable = true)
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Basic
    @Column(name = "shop_capacity", nullable = false)
    public int getShopCapacity() {
        return shopCapacity;
    }

    public void setShopCapacity(int shopCapacity) {
        this.shopCapacity = shopCapacity;
    }

    @Basic
    @Column(name = "timeslot_minutes_duration", nullable = false)
    public int getTimeslotMinutesDuration() {
        return timeslotMinutesDuration;
    }

    public void setTimeslotMinutesDuration(int timeslotMinutesDuration) {
        this.timeslotMinutesDuration = timeslotMinutesDuration;
    }

    @Basic
    @Column(name = "max_entering_client_in_a_timeslot", nullable = false)
    public int getMaxEnteringClientInATimeslot() {
        return maxEnteringClientInATimeslot;
    }

    public void setMaxEnteringClientInATimeslot(int maxEnteringClientInATimeslot) {
        this.maxEnteringClientInATimeslot = maxEnteringClientInATimeslot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopEntity that = (ShopEntity) o;

        if (id != that.id) return false;
        if (shopCapacity != that.shopCapacity) return false;
        if (timeslotMinutesDuration != that.timeslotMinutesDuration) return false;
        if (maxEnteringClientInATimeslot != that.maxEnteringClientInATimeslot) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (!Arrays.equals(image, that.image)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(image);
        result = 31 * result + shopCapacity;
        result = 31 * result + timeslotMinutesDuration;
        result = 31 * result + maxEnteringClientInATimeslot;
        return result;
    }
}
