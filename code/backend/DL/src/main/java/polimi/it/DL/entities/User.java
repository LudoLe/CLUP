package polimi.it.DL.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "`user`", schema = "clup")
@NamedQueries({
        @NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
        @NamedQuery(name="User.findByUsername", query="SELECT u FROM User u WHERE u.username=?1"),
        @NamedQuery(name="User.getHashByUserId", query="SELECT u.password FROM User u WHERE u.id=?1"),
        @NamedQuery(name="User.exists", query="SELECT count(u) FROM User u WHERE u.username=?1 OR u.email=?2"),
        @NamedQuery(name="User.findByToken", query="SELECT u FROM User u WHERE u.sessionToken=?1"),

})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Expose
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="is_manager")
    private boolean isManager;

    @Column(name="session_token")
    private String sessionToken;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    @OneToMany(mappedBy="manager", fetch = FetchType.LAZY)
    private List<Shop> shops;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean getIsManager() {
        return isManager;
    }
    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public List<Shop> getShops() {
        return shops;
    }
    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
