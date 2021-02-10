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


    /**this function retrieves the shop from the database and pack the http response with
     * the shop if it is found or with and alert message if it's not found
     * @param shopid used to find the ticket in the database
     * @return the http-response
     * */
    public Response getShopInfo(int shopid){
        Response response;
        Response.Status status;

        try{
            Shop shop= shopService.find(shopid);
            if(shop==null){
                status = Response.Status.NOT_FOUND;
                response = responseWrapper.generateResponse(status,"no such shop");
            }else{


                List<Ticket> tickets = ticketService.findAllTicketsForShopAndDetach(shopid);
                tsc = new TicketSchedulerComponent(tickets);
                tsc.buildQueue();
                Date date = tsc.getQueueTime();
                ticketService.mergeAllTickets(tickets);

                status = Response.Status.OK;
                ShopResponse response1 = new ShopResponse();
                response1.setQueueTime(date);
                response1.setShop(shop);

                response = responseWrapper.generateResponse(status,response1);
            }
            return response;
        }catch(Exception e){
            String message = "something went wrong";
            status = Response.Status.INTERNAL_SERVER_ERROR;
            response = responseWrapper.generateResponse(status,message);
            return response;
        }
    }

    /**
     * this function retrieves the analytics inferred on the queue and on the shop
     * it is accessible b
     * */
    public Response getShopAnalytics(int shopid) throws Exception {
        Response response;
        Response.Status status;
        ShopAnalytics shopAnalytics = new ShopAnalytics();
        Integer pplInTheShop;
        Integer pplEnqueued;


        pplInTheShop = ticketService.peopleInTheShop(shopid);
        if(pplInTheShop==null){shopAnalytics.setPeopleInTheShop(0);}
        else shopAnalytics.setPeopleInTheShop(pplInTheShop);
        pplEnqueued = ticketService.peopleEnqueued(shopid);
        if(pplEnqueued==null){shopAnalytics.setPeopleEnqueued(0);}
        else shopAnalytics.setPeopleInTheShop(pplEnqueued);

        List<Ticket> tickets = ticketService.findAllTicketsForShopAndDetach(shopid);
        tsc = new TicketSchedulerComponent(tickets);
        tsc.buildQueue();
        Date date = tsc.getQueueTime();
        ticketService.mergeAllTickets(tickets);

        shopAnalytics.setEstimatedDurationOfTheQueue(date);
        status = Response.Status.OK;
        response = responseWrapper.generateResponse(status,shopAnalytics);
        return response;

    }
}
