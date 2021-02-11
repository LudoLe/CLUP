package polimi.it.SSB;

import Responses.ShopAnalytics;
import Responses.ShopResponse;
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
import java.util.Date;
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

    public ShopInfoComponent(){}

    /**
     * constructor for test purposes
     * */
    public ShopInfoComponent(AAVEngine aavEngine, ShopService shopService, ResponseWrapper responseWrapper, TicketService ticketService, UserService userService){
        this.aav=aavEngine;
        this.shopService=shopService;
        this.responseWrapper=responseWrapper;
        this.ticketService=ticketService;
        this.userService=userService;
    }


    /**this function retrieve the ticket from the database and pack the http response with
     * the ticket if it is found or with and alert message if it's not found
     * @param ticketid used to find the ticket in the database
     * @return the http-response
     * */
    public Response getTicketInfo(int ticketid){
        Response response;
        Response.Status status;

        try{
            Ticket ticket= ticketService.find(ticketid);
            if(ticket==null){
                status = Response.Status.NOT_FOUND;
                response = responseWrapper.generateResponse(status,"ticket not found");
            }else{
                status = Response.Status.OK;
                response = responseWrapper.generateResponse(status,ticket);
            }
            return response;
        }catch(Exception e){
            String message = "couldnt retrieve ticket info";
            status = Response.Status.INTERNAL_SERVER_ERROR;
            response = responseWrapper.generateResponse(status,message);
            return response;

        }
    }


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
                return responseWrapper.generateResponse(status,"no such shop");
            }else{
                ShopResponse shopResponse = new ShopResponse();
                List<Ticket> tickets = shopService.getTicketsOfShop(shop);
                shopResponse.setShop(shop);
                shopResponse.setTickets(tickets);
                status = Response.Status.OK;
                return responseWrapper.generateResponse(status,shopResponse);
            }
        }catch(Exception e){
            String message = "something went wrong";
            status = Response.Status.INTERNAL_SERVER_ERROR;
            response = responseWrapper.generateResponse(status,message);
            return response;
        }
    }




    /**this function retrieves the shops from the database and pack the http response with
     * the shops entities if found or with and an alert message if not found
     * @param username used to find the shops in the database related to such user
     * @return the http-response
     * */
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
            String message = "couldnt retrieve shops";
            status = Response.Status.INTERNAL_SERVER_ERROR;
            response = responseWrapper.generateResponse(status,message);
            return response;

        }
    }
    /**
     this method fetch all of the shops registered on Clup
     * @return http response
     * */
    public Response getAllShops(){
        Response response;
        Response.Status status;
            List<Shop> shops= shopService.getAllShops();
            status = Response.Status.OK;
            return responseWrapper.generateResponse(status, shops);

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


        status = Response.Status.OK;
        response = responseWrapper.generateResponse(status,shopAnalytics);
        return response;

    }

    /**this function retrieves the tickets from the database and pack the http response with
     * a list of tickets entities if found or with and a message if the user have no tickets yet
     * @param username used to find the tickets in the database related to such user
     * @return the http-response
     * */
    public Response getTickets(String username){
       Response response;
       Response.Status status;
        try{
            List<Ticket> tickets = userService.getTicketsFromUS(username);
            status = Response.Status.OK;
            return responseWrapper.generateResponse(status,tickets);

        }catch(Exception e){
            String message = "couldnt retrieve tickets";
            status = Response.Status.INTERNAL_SERVER_ERROR;
            response = responseWrapper.generateResponse(status,message);
            return response;
        }
    }
}
