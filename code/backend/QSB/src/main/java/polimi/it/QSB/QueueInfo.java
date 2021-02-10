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
        tsc = new TicketSchedulerComponent(tickets);
        tsc.buildQueue();
        Date date = tsc.getQueueTime();

        status = Response.Status.OK;
        response = responseWrapper.generateResponse(status, date);

        return response;

    }



}
