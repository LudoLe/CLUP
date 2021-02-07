package polimi.it.DL.services;

import polimi.it.DL.entities.ShopShift;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.Date;

@Stateless(name= "services/ShopShiftService")
public class ShopShiftService {
    @PersistenceContext(unitName = "clup")
    private EntityManager em;
    @EJB(name="services/ShopService")
    ShopService shopService;

    public ShopShiftService(){}

    public ShopShift find(int id) throws Exception{
        return em.find(ShopShift.class, id);
    }

    public ShopShift create(int shop, Date closingTime, Date openingTime, int dayShift) throws Exception{
        try{
            //checks that username and email aren't already in use
            ShopShift shopShift = new ShopShift();
            shopShift.setShop(shopService.find(shop));
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
