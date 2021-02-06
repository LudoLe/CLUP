package polimi.it.DL.services;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.ShopShift;
import polimi.it.DL.entities.User;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.sql.Date;

@Stateless(name= "services/ShopShiftService")
public class ShopShiftService {
    @PersistenceContext(unitName = "clup")
    private EntityManager em;

    public ShopShiftService(){}

    public ShopShift find(int id) throws Exception{
        return em.find(ShopShift.class, id);
    }

    public ShopShift create(Shop shop, Date closingTime, Date openingTime, String dayShift) throws Exception{
        try{
            //checks that username and email aren't already in use
            ShopShift shopShift = new ShopShift();
            shopShift.setShop(shop);
            shopShift.setClosingTime(closingTime);
            shopShift.setOpeningTime(openingTime);
            shopShift.setDay(dayShift);
            em.persist(shopShift);
            em.flush();
            return shopShift;
        } catch (PersistenceException e) {
            throw new Exception("Could not insert user");
        }
    }
}
