package polimi.it.DL.services;

import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.Ticket;
import polimi.it.DL.entities.User;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

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
     * this method update in the database the scheduled entering time and exiting time of all of the ticket in the queue along with status and arrival time
     * @param tickets contains the tickets to be recreated with the updated parameters
     * */
    public void updateAllTickets(ArrayList<Ticket> tickets) throws Exception{
        try{
            for(Ticket t : tickets){
                createAfterBuildedQueue(t.getShop(), t.getUser(), t.getExpectedDuration(),
                        t.getTimeToReachTheShop(), t.getStatus(), t.getScheduledEnteringTime(),
                        t.getScheduledExitingTime(), t.getArrivalTime());
                em.persist(t);
                em.refresh(t);

            }
            em.flush();
        } catch (PersistenceException e) {
            throw new Exception("Could not insert ticket");
        }
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

        public void createAfterBuildedQueue(Shop shop, User user, Date permanence, Date timeToGetToTheShop, String status, Date scheduledEnteringTime, Date scheduledExitingTime, Date arrivalTime) throws Exception{
            try{
                Ticket ticket = new Ticket();
                ticket.setShop(shop);
                ticket.setUser(user);
                ticket.setTimeToReachTheShop(timeToGetToTheShop);
                ticket.setExpectedDuration(permanence);
                ticket.setStatus(status);
                ticket.setScheduledEnteringTime(scheduledEnteringTime);
                ticket.setScheduledExitingTime(scheduledExitingTime);
                ticket.setArrivalTime(arrivalTime);
                em.persist(ticket);
                em.flush();
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

    public boolean scanEnterTicket(int ticketId) throws Exception{

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss[.nnn]");
        Date parsedDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));

        Ticket ticket = this.find(ticketId);
        Date ticketDate=ticket.getScheduledEnteringTime();

        Calendar c = Calendar.getInstance();
        c.setTime(ticketDate);

        // Perform addition/subtraction
        c.add(Calendar.MINUTE, 5);
        Date ticketDateAfter  = c.getTime();
        Calendar c2 = Calendar.getInstance();
        c.setTime(ticketDate);
        c2.add(Calendar.MINUTE, -5);
        Date ticketDateBefore = c2.getTime();

        if(!ticketDate.after(ticketDateAfter)){
            ticket.setEnterTime(parsedDate);
            em.persist(ticket);
            em.flush();
            return true;
        }
        else return false;
    }

    public void setScheduledEntrance(EntityManager em, boolean test){
        this.em = em;
        this.test = test;
    }

    public boolean scanExitTicket(int ticketId) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss[.nnn]");
        Date parsedDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        Ticket ticket = this.find(ticketId);

        if(ticket==null)return false;
        if(ticket.getEnterTime()==null) return false;

            ticket.setExitTime(parsedDate);
            em.persist(ticket);
            em.flush();
            return true;
        }

    public TicketService(EntityManager em, boolean test){
        this.em = em;
        this.test = test;
    }


    }

