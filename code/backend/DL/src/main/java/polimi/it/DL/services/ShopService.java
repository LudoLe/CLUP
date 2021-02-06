package polimi.it.DL.services;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.User;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.resource.cci.MappedRecord;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Stateless
public class ShopService {
    @PersistenceContext(unitName = "clup")
    private EntityManager em;

    @EJB("services/UserService")
    UserService userService;

    @EJB("services/ShopShiftService")
    ShopShiftService shopShiftService;

    public ShopService(){}

    public Shop find(int id) throws Exception{
        return em.find(Shop.class, id);
    }


    public Shop createShop(String description, int shopCapacity,
                           String shopName, String shopManager,
                           String image, int maxClients, String position,
                           Map shifts) throws Exception{
        try{
            //checks that username and email aren't already in use
                   User manager = userService.findByUsername(shopManager);
                   if(user==null)return null;
                else {
                       Shop shop = new Shop();
                       shop.setDescription(description);
                       shop.setShopCapacity(shopCapacity);
                       shop.setName(shopName);
                       shop.setManager(manager);
                       shop.setImage(image);
                       shop.setMaxEnteringClientInATimeslot(maxClients);
                       shop.setPosition(position);
                       try {
                           for(String day :  shifts.values().){
                               ShopShiftService q = shopShiftService.create(shop, dayShift);
                               em.persist(q);
                           }

                   }



        }
        } catch (PersistenceException e) {
            throw new Exception("Could not insert user");
        }
    }
}
