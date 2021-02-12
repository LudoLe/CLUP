package polimi.it.QSB;

import Responses.ShopAnalytics;
import Responses.ShopResponse;
import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.Ticket;
import polimi.it.DL.services.ShopService;
import polimi.it.DL.services.TicketService;
import responseWrapper.ResponseWrapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Stateless(name = "QueueInfo")
public class QueueInfo {


    @EJB(name = "services/TicketService")
    private TicketService ticketService;

    @EJB(name = "services/ShopService")
    private ShopService shopService;

    @EJB(name = "TicketSchedulerComponent")
    private TicketSchedulerComponent tsc;

    @EJB(name = "ResponseWrapper")
    private ResponseWrapper responseWrapper;


    public Response getQueueDuration(int shopid) throws Exception {

        Response response;
        Response.Status status;

        List<Ticket> tickets = ticketService.findAllTicketsForShopAndDetach(shopid);

        if (tickets == null) {
            status = Response.Status.BAD_REQUEST;
            response = responseWrapper.generateResponse(status, "no such shop");
        } else if (tickets.isEmpty()) {
            status = Response.Status.OK;
            response = responseWrapper.generateResponse(status, new Date(0L));
        } else {
            TicketSchedulerComponent tsc = (new TicketSchedulerComponent(ticketService.findAllTicketsForShopAndDetach(shopid)));
            List<Ticket> lt = tsc.buildQueue();
            ticketService.mergeAllTickets(lt);
            Date date = tsc.getQueueTime();
            status = Response.Status.OK;
            response = responseWrapper.generateResponse(status, date);
        }
        return response;

    }

    public Response scanTicket(int ticketid) throws Exception {
        Response response;
        Response.Status status;

        Ticket ticket = ticketService.find(ticketid);
        if (ticket == null) {
            return responseWrapper.generateResponse(Response.Status.BAD_REQUEST, "no such ticket");
        }
        //build the queue in order to access it later
        TicketSchedulerComponent tsc = (new TicketSchedulerComponent(ticketService.findAllTicketsForShopAndDetach(ticket.getShop().getId())));
        List<Ticket> lt = tsc.buildQueue();
        ticketService.mergeAllTickets(lt);

        //checking for ticket that wants to exit the shop
        if (ticket.getStatus().equals("in_use")) {
            ticket = ticketService.scanExitTicket(ticketid, new Date());
            System.out.println("Ticket scanned to exit the shop");
        }
        //checking for ticket that wants to enter the shop
        else if (tsc.canEnter(ticketid)) {
            ticket = ticketService.scanEnterTicket(ticketid, new Date());
            System.out.println("Ticket scanned to enter the shop");
        }
        //otherwise the ticket can't be scanned
        else {
            status = Response.Status.BAD_REQUEST;
            response = responseWrapper.generateResponse(status, "Ticket cannot be scanned.");
            return response;
        }

        //rebuild and update queue after the scan
        tsc = new TicketSchedulerComponent(ticketService.findAllTicketsForShopAndDetach(ticket.getShop().getId()));
        lt = tsc.buildQueue();
        ticketService.mergeAllTickets(lt);

        status = Response.Status.OK;
        response = responseWrapper.generateResponse(status, ticket);
        return response;
    }


}
