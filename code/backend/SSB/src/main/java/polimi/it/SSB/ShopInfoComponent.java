package polimi.it.SSB;

import polimi.it.AMB.AAVEngine;
import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.Ticket;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.ShopService;
import polimi.it.DL.services.TicketService;
import polimi.it.DL.services.UserService;
import responseWrapper.ResponseWrapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless(name = "SIC")
public class ShopInfoComponent {

    @EJB(name = "services/UserService")
    private UserService userService;

    @EJB(name = "services/TicketService")
    private TicketService ticketService;

    @EJB(name = "services/ShopService")
    private ShopService shopService;

    @EJB(name = "ResponseWrapper")
    private ResponseWrapper responseWrapper ;

    @EJB(name = "AAVEngine")
    private AAVEngine aav ;


    public Response getTicketInfo(int ticketid, String username){
        Response response;
        Response.Status status;

        try{
            Ticket ticket= ticketService.find(ticketid);
            status = Response.Status.OK;
            response = responseWrapper.generateResponse(status,ticket);
            return response;

        }catch(Exception e){
            String message = "couldnt retrieve ticket info";
            status = Response.Status.INTERNAL_SERVER_ERROR;
            response = responseWrapper.generateResponse(status,message);
            return response;

        }
    }

    public Response getShopInfo(int shopid, String username){
        Response response;
        Response.Status status;

        try{
            Shop shop= shopService.find(shopid);
            status = Response.Status.OK;
            response = responseWrapper.generateResponse(status,shop);
            return response;

        }catch(Exception e){
            String message = "couldnt retrieve ticket info";
            status = Response.Status.INTERNAL_SERVER_ERROR;
            response = responseWrapper.generateResponse(status,message);
            return response;

        }
    }

    public Response getShops(String username){
        Response response;
        Response.Status status;

        try{
            User user= userService.findByUsername(username);
            List<Shop> shops = user.getShops();
            status = Response.Status.OK;
            response = responseWrapper.generateResponse(status, shops);
            return response;

        }catch(Exception e){
            String message = "couldnt retrieve ticket info";
            status = Response.Status.INTERNAL_SERVER_ERROR;
            response = responseWrapper.generateResponse(status,message);
            return response;

        }
    }


    public Response getTickets(String username){
       Response response;
       Response.Status status;

        try{
            User user= userService.findByUsername(username);
            List<Ticket> tickets = user.getTickets();
            status = Response.Status.OK;
            response = responseWrapper.generateResponse(status,tickets);
            return response;

        }catch(Exception e){
            String message = "couldnt retrieve tickets";
            status = Response.Status.INTERNAL_SERVER_ERROR;
            response = responseWrapper.generateResponse(status,message);
            return response;

        }

    }


}
