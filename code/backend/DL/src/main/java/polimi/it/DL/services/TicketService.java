package polimi.it.DL.services;

import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.Ticket;
import polimi.it.DL.entities.User;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Stateless(name= "services/TicketService")
public class TicketService {

    boolean test = false;

    @PersistenceContext(unitName = "clup")
    private EntityManager em;

    @EJB(name = "services/ShopServices")
    private ShopService shopService;

    @EJB(name = "services/UserServices")
    private UserService userService;

    public TicketService(){}

    public Ticket find(int id) throws Exception{
        return em.find(Ticket.class, id);
    }

    /**
     * this method checks whether a user has a queue ticket
     * @param username of the user we are interested in
     * @return boolean value
     * */
    public boolean alreadyHasTicket(String username) throws Exception{
        Ticket ticket= em.createNamedQuery("Ticket.findForUser", Ticket.class).setParameter(1, username).getResultList().stream().findFirst().orElse(null);
        return ticket != null;
    }

    //lo porti a livello di business perchè è ridicolo
    public boolean itsTicketOf(String username, int ticketId) throws Exception{
        Ticket ticket= em.createNamedQuery("Ticket.findForUser", Ticket.class).setParameter(1,username).getResultList().stream().findFirst().orElse(null);
        Ticket ticket2= find(ticketId);
        if(ticket==null || ticket2==null)return false;
        return ticket.equals(ticket2);
    }

    /**
     * this method create a ticket in the database,
     * it is initially created with the status "invalid" and with some of the field left empty.
     * these fields will be then updated by a dedicated algorithm.
     * @param shop is the  shop the ticket allows to enter in
     * @param user is the user who owns the ticket
     * @param permanence the permanence time
     * @param timeToGetToTheShop is the time that will take the client to get to the shop
     * @return the ticket just created
     * */
    public Ticket create(Shop shop, User user, Date permanence, Date timeToGetToTheShop) throws Exception {
        try {

            Ticket ticket = new Ticket();
            ticket.setShop(shop);
            ticket.setUser(user);
            ticket.setTimeToReachTheShop(timeToGetToTheShop);
            ticket.setExpectedDuration(permanence);
            ticket.setStatus("invalid");
            em.persist(ticket);
            em.flush();
            return ticket;
        } catch (PersistenceException e) {
            throw new Exception("Could not insert ticket");
        }
    }


    /**
     * this method delete a ticket from the database
     * @param ticketId is the id of the ticket to delete
     * */
    public boolean delete(int ticketId) throws Exception{
        Ticket ticket = this.find(ticketId);
        if(ticket == null) return false;
        em.remove(ticket);
        em.flush();
        return true;
    }

    /**
     * this method detach the tickets from the database in order to safely update them
     * @param shopid is the id of the shop to retrieve the tickets of
     * @return the list of tickets to be updated
     * */
    public List<Ticket> findAllTicketsForShopAndDetach(int shopid) throws Exception{
        List<Ticket> tickets= em.createNamedQuery("Ticket.findForUser", Ticket.class).setParameter(1, shopid).getResultList();
        tickets.forEach(ticket -> em.detach(ticket));
        return  tickets;
    }
    /**
     * this method merge the update tickets to the database
     * @param tickets the tickets to be updated
     * */
    public void mergeAllTickets(List<Ticket> tickets) throws Exception{
        tickets.forEach(ticket -> em.merge(ticket));
        em.flush();
    }

    public void scanEnterTicket(int ticketId, Date date) throws Exception{
        Ticket ticket = this.find(ticketId);
            ticket.setEnterTime(date);
            em.persist(ticket);
            em.flush();
    }

    public boolean scanExitTicket(int ticketId, Date date) throws Exception{

        Ticket ticket = this.find(ticketId);
        if(ticket==null)return false;
        if(ticket.getEnterTime()==null) return false;
            ticket.setExitTime(date);
            em.persist(ticket);
            em.flush();
            return true;
        }



    }

