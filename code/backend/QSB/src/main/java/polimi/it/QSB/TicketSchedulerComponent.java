package polimi.it.QSB;

import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.Ticket;
import polimi.it.DL.services.ShopService;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.List;

public class TicketSchedulerComponent {

    @EJB(name="services/ShopService")
    ShopService shopService;

    int shopid;

    public TicketSchedulerComponent(int shopid) throws Exception {
        this.shopid=shopid;
    }
    Shop shop = shopService.find(shopid);
    List<Ticket> tickets = shop.getTickets();

}
