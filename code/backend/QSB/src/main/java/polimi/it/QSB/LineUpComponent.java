package polimi.it.QSB;

import Responses.StringResponse;
import polimi.it.DL.entities.Shop;
import polimi.it.DL.services.ShopService;
import polimi.it.DL.services.TicketService;
import polimi.it.DL.services.UserService;
import prototypes.EnqueueData;
import responseWrapper.ResponseWrapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.Date;

@Stateless(name = "LineUpComponent")
public class LineUpComponent {

    @EJB(name = "services/ShopService")
    ShopService shopService;

    @EJB(name = "services/UserService")
    UserService userService;

    @EJB(name = "ResponseWrapper")
    ResponseWrapper responseWrapper;

    @EJB(name = "services/TicketService")
    TicketService ticketService;

    /*public LineUpComponent(boolean flag,ResponseWrapper responseWrapper, ShopService shopService, TicketService ticketService, UserService userService){
       if(flag)
       {this.responseWrapper= responseWrapper;
        this.shopService=shopService;
        this.ticketService=ticketService;
        this.userService=userService;
       }
    }*/


        public boolean checkIfAlreadyEnqueued(String username) throws Exception{
        boolean bol;
        bol=ticketService.alreadyHasTicket(username);
        return bol;
    }

    public boolean checkProperty(String username, int ticketid) throws Exception{
        boolean bol;
        bol=ticketService.itsTicketOf(username, ticketid);
        return bol;
    }


    public String noSenseTime(EnqueueData enqueueData) throws Exception{
        Date newHour = new Date(3600000L);
        Date newMinute = new Date(60000L*15);
        Date newMinute2 = new Date(60000L*5);
        Date newMinute10 = new Date(4900000L);

        if(enqueueData.getPermanence().after(newHour))return "permanence time too long";
        if(enqueueData.getPermanence().before(newMinute))return "permanence time too little";
        if(enqueueData.getTimeToGetToTheShop().before(newMinute2))return "time to get to the shop  too little";
        if(enqueueData.getTimeToGetToTheShop().after(newMinute10))return "time to get to the shop  too long";
        return "OK";
    }


    public boolean corruptedData(EnqueueData enqueueData) throws Exception {
        if(enqueueData==null ) {
           return true;
        }
        if(shopService.find(enqueueData.getShopid())==null){
            return true;
        }
        return false;
    }
    public Response enqueue(EnqueueData enqueueData, String username){
        Response response;
        Response.Status status;
        try{
            Shop shop = shopService.find(enqueueData.getShopid());
            ticketService.create( shop, userService.findByUsername(username), enqueueData.getPermanence(), enqueueData.getTimeToGetToTheShop());
            TicketSchedulerComponent tsc = (new TicketSchedulerComponent(ticketService.findAllTicketsForShopAndDetach(shop.getId())));
            tsc.buildQueue();

          // ticketService.updateAllTickets(tickets);
        }catch (Exception e){
            status = Response.Status.INTERNAL_SERVER_ERROR;
            response = responseWrapper.generateResponse(status, new StringResponse("Something went wrong retry later"));
            return response;

        }
        status = Response.Status.OK;
        response = responseWrapper.generateResponse(status, new StringResponse("You have been correctly enqueued"));
        return response;
    }

    public Response dequeue(int ticketid){
        Response response;
        Response.Status status;
        try{
            ticketService.delete(ticketid);
        }catch (Exception e){
            status = Response.Status.INTERNAL_SERVER_ERROR;
            response = responseWrapper.generateResponse(status, new StringResponse("Something went wrong retry later"));
            return response;

        }
        status = Response.Status.OK;
        response = responseWrapper.generateResponse(status, new StringResponse("You have been correctly deenqueued"));
        return response;
    }


}
