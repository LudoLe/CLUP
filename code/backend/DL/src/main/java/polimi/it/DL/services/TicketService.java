package polimi.it.DL.services;

import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.ShopShift;
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


    public boolean updateAllTickets(ArrayList<Map<Ticket, Date>> result) throws Exception{
        try{
            //checks that username and email aren't already in use
            Map<Ticket, Date> enteringTimeMap = result.get(0);
            Map<Ticket, Date> exitingTimeMap = result.get(1);

        } catch (PersistenceException e) {
            throw new Exception("Could not insert ticket");
        }
    }

    public Ticket create(Date entertime, Date exittime, int shopid, User user, Date permanence, Date timetogetthere) throws Exception{
        try{
            //checks that username and email aren't already in use
            Shop shop = shopService.find(shopid);
            Ticket ticket = new Ticket();
            ticket.setShop(shop);
            ticket.setUser(user);
            ticket.setTimeToReachTheShop(timetogetthere);
            ticket.setExpectedDuration(permanence);
            ticket.setScheduledEnteringTime(entertime);
            ticket.setScheduledEnteringTime(exittime);
            ticket.setStatus("invalid");
            em.persist(ticket);
            em.refresh(ticket);
            em.flush();
            return ticket;
        } catch (PersistenceException e) {
            throw new Exception("Could not insert ticket");
        }
    }
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

