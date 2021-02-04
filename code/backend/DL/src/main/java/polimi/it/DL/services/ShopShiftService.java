package polimi.it.DL.services;

import polimi.it.DL.entities.ShopShift;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
public class ShopShiftService {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public ShopShiftService(){}

    public ShopShift find(int id) throws Exception{
        return em.find(ShopShift.class, id);
    }
}
