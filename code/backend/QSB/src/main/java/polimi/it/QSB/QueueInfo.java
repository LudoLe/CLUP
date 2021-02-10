package polimi.it.QSB;

import Responses.ShopAnalytics;
import polimi.it.DL.entities.Ticket;
import polimi.it.DL.services.ShopService;
import polimi.it.DL.services.TicketService;
import polimi.it.DL.services.UserService;
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

    @EJB(name = "TicketSchedulerComponent")
    private TicketSchedulerComponent tsc;

    @EJB(name = "ResponseWrapper")
    private ResponseWrapper responseWrapper ;



    public Response getShopAnalytics(int shopid) throws Exception {
        Response response;
        Response.Status status;
        ShopAnalytics shopAnalytics = new ShopAnalytics();
        Integer pplInTheShop;
        Integer pplEnqueued;
        Ticket tick;
        Date estimatedTime;

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
