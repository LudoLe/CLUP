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
    private ResponseWrapper responseWrapper ;


    public Response getQueueDuration(int shopid) throws Exception {

        Response response;
        Response.Status status;

        List<Ticket> tickets = ticketService.findAllTicketsForShopAndDetach(shopid);

        if(tickets == null){
            status = Response.Status.BAD_REQUEST;
            response = responseWrapper.generateResponse(status, "no such shop");
        }else if(tickets.isEmpty()){
            status = Response.Status.OK;
            response = responseWrapper.generateResponse(status, new Date(0L));
        }
        else{
            tsc = new TicketSchedulerComponent(tickets);
            tsc.buildQueue();
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
        if(ticket == null){
            return responseWrapper.generateResponse(Response.Status.BAD_REQUEST, "no such ticket");
        }
        if(ticket.getStatus().equals("invalid")){
            return responseWrapper.generateResponse(Response.Status.BAD_REQUEST, "ticket not valid");
        }

            if(ticket.getEnterTime()==null && ticket.getExitTime()== null && ticket.getStatus().equals("valid")){
            ticket = ticketService.scanEnterTicket(ticketid, new Date());
            System.out.println(ticket.getEnterTime());
        }else if(ticket.getEnterTime()!=null && ticket.getExitTime()==null && ticket.getStatus().equals("in_use")){

            ticket = ticketService.scanExitTicket(ticketid, new Date());

            System.out.println(ticket.getExitTime());

            ticketService.delete(ticketid);

        }else if(ticket.getEnterTime()==null && ticket.getExitTime()!=null){
            System.out.println(ticket.getExitTime());

            status = Response.Status.NOT_ACCEPTABLE;
            response = responseWrapper.generateResponse(status, "uscita senza entrata");
            return response;

          }else{
            status = Response.Status.BAD_REQUEST;
            response = responseWrapper.generateResponse(status, "something wrong with your ticket, please talk to a manager");
            return response;
        }
        status = Response.Status.OK;
        response = responseWrapper.generateResponse(status, ticket);
        return response;

    }



}
