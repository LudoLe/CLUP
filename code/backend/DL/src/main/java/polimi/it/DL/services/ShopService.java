package polimi.it.DL.services;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.ShopShift;
import polimi.it.DL.entities.Ticket;
import polimi.it.DL.entities.User;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.resource.cci.MappedRecord;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless(name= "services/ShopService")
public class ShopService {
    @PersistenceContext(unitName = "clup")
    private EntityManager em;

    @EJB(name="services/UserService")
    UserService userService;

    @EJB(name="services/TicketService")
    TicketService ticketService;

    @EJB(name= "services/ShopShiftService")
    ShopShiftService shopShiftService;

    public ShopService(){}

    public Shop find(int id) throws Exception{
        return em.find(Shop.class, id);
    }


    public Shop createShop(String description, int shopCapacity,
                           String shopName, String shopManager,
                           String image, int maxClients, String position) throws Exception{
        try{
            //checks that username and email aren't already in use
                   User manager = userService.findByUsername(shopManager);
                   if(manager==null)return null;
                else {
                       Shop shop = new Shop();
                       shop.setDescription(description);
                       shop.setShopCapacity(shopCapacity);
                       shop.setName(shopName);
                       shop.setManager(manager);
                       shop.setImage(image);
                       shop.setMaxEnteringClientInATimeslot(maxClients);
                       shop.setPosition(position);
                       em.persist(shop);
                       em.flush();
                       return shop;
                   }

        } catch (PersistenceException e) {
            throw new Exception("Could not insert user");
        }
    }

    public boolean lessThanActualCapacity(int shopId) throws Exception {
        Shop shop = find(shopId);
        int maxCapacity=shop.getShopCapacity();
        int peopleInTheShop= em.createNamedQuery("Ticket.PeopleInTheShop", Integer.class).getResultList().stream().findFirst().orElse(0);
        return peopleInTheShop < maxCapacity;

    }

    public Date lastTicketTime(int shopId) {
        Ticket ticket = em.createNamedQuery("Ticket.listOrderedForShop", Ticket.class).getResultList().stream().findFirst().orElse(null);
        assert ticket!=null;
        return ticket.getScheduledExitingTime();

    }

    public java.util.Date closingTimeForShopForDay(int shopId, String day) {
        ShopShift shopShift = em.createNamedQuery("ShopShift.forShopForDay", ShopShift.class).setParameter(1, shopId).setParameter(2, day).getResultList().stream().findFirst().orElse(null);
        assert shopShift!=null;
        return shopShift.getClosingTime();
    }

    public java.util.Date openingTimeForShopForDay(int shopId, String day) {
        ShopShift shopShift = em.createNamedQuery("ShopShift.forShopForDay", ShopShift.class).setParameter(1, shopId).setParameter(2, day).getResultList().stream().findFirst().orElse(null);
        assert shopShift!=null;
        return shopShift.getOpeningTime();

    }
}

