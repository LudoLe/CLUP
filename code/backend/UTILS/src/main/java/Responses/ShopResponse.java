package Responses;

import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.Ticket;

import java.util.Date;
import java.util.List;

public class ShopResponse{

    private Shop shop;
    private Date queueTime;
    private List<Ticket> tickets;

    public ShopResponse(){
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Date getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(Date queueTime) {
        this.queueTime = queueTime;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
