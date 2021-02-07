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

    public boolean existsWithThatNameAndPosition(String name, String position){
        int bol = em.createNamedQuery("Shop.existWithThatNameAndPosition", Integer.class).setParameter(1, name).setParameter(2, position).getResultList().stream().findFirst().orElse(0);
        if(bol==0)return true;
        else return false;
    }


    public Shop createShop(String description, int shopCapacity,
                           String shopName, User shopManager,
                           String image, int maxClients, String position,
                           int timeSlot
    ) throws Exception{
        try{
            //checks that username and email aren't already in use
            System.out.println("before creating a shop in create shop service");

                       Shop shop = new Shop();
                       shop.setDescription(description);
                       shop.setShopCapacity(shopCapacity);
                       shop.setName(shopName);
                       shop.setManager(shopManager);
                       shop.setImage(image);
                       shop.setMaxEnteringClientInATimeslot(maxClients);
                       shop.setPosition(position);
                       shop.setTimeslotMinutesDuration(timeSlot);
                       em.persist(shop);
                       em.flush();
                       System.out.println("after creating a shop in create shop service");

                       return shop;


        } catch (PersistenceException e) {
            throw new Exception("Could not insert shop");
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

