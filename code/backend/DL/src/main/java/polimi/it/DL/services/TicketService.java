package polimi.it.DL.services;

import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.Ticket;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
public class TicketService {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public TicketService(){}

    public Ticket find(int id) throws Exception{
        return em.find(Ticket.class, id);
    }
}
