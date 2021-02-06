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
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

@Stateless(name= "services/TicketService")
public class TicketService {

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

    public Ticket create(Date entertime, Date exittime, int shopid, int userid) throws Exception{
        try{
            //checks that username and email aren't already in use
            Shop shop = shopService.find(shopid);
            User user = userService.find(userid);
            Ticket ticket = new Ticket();
            ticket.setShop(shop);
            ticket.setUser(user);
            ticket.setScheduledEnteringTime(entertime);
            ticket.setScheduledEnteringTime(exittime);

            em.persist(ticket);
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

    }

