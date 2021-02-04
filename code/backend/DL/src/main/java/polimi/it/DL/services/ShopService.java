package polimi.it.DL.services;

import polimi.it.DL.entities.Shop;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
public class ShopService {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public ShopService(){}

    public Shop find(int id) throws Exception{
        return em.find(Shop.class, id);
    }
}
