package prototypes;

import java.io.Serializable;
import java.util.List;

public class UserInfo  implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String email;
    private String username;
    private String phoneNumber;
    List<Ticket> tickets;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(List<Ticket> tickets) {
        this.tickets= tickets;
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


}
