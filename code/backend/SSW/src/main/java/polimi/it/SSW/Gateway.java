package polimi.it.SSW;

import Responses.StringResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import polimi.it.AMB.AAVEngine;
import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.ShopShift;
import polimi.it.DL.entities.Ticket;
import polimi.it.DL.entities.User;
import polimi.it.SSB.ManageShopComponent;
import polimi.it.SSB.ShopInfoComponent;
import prototypes.ShopProto;
import prototypes.ShopShiftProto;
import responseWrapper.ResponseWrapper;

import javax.ejb.EJB;
import javax.faces.annotation.RequestMap;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;


@Path("/SSW")
@Api(value = "Methods")
public class Gateway {

    @EJB(name = "ResponseWrapper")
    private ResponseWrapper responseWrapper ;

    @EJB(name = "AVVEngine")
    AAVEngine avv;

    @EJB(name = "SIC")
    ShopInfoComponent sic;

    @EJB(name = "ManageShopComponent")
    ManageShopComponent msc;

    @GET
    @ApiOperation(value = "tickets")
    @Path("/tickets")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered", response = List.class),
            @ApiResponse(code = 400, message = "Registration failed", response = String.class ),
            @ApiResponse(code = 500, message = "Invalid payload/error", response = String.class )})
    public Response getUserTickets(@HeaderParam("username") String username, @HeaderParam("session-token") String sessionToken ){
        String message;
        Response response;
        Response.Status status;

        try {
            if (!avv.isAuthorized(username, sessionToken)) {
                message= "Not authorized!!!";
                status = Response.Status.BAD_REQUEST;
            } else {
                response = sic.getTickets(username);
                return response;
            }
        } catch (Exception e) {
            message = "Internal server error. Please try again later1.";
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        response = responseWrapper.generateResponse(null,status, message);
        return response;
    }

    //TODO controllare roba relativa all'essere un manager
    @GET
    @ApiOperation(value = "ticketinfo")
    @Path("/ticketDetail/{ticketid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered", response = Ticket.class),
            @ApiResponse(code = 400, message = "Registration failed", response = String.class),
            @ApiResponse(code = 500, message = "Invalid payload/error", response = String.class )})
    public Response getTicketDetail(@HeaderParam("username") String username, @HeaderParam("session-token") String sessionToken, @PathParam("ticketid") int ticketid){
        String message;
        Response response;
        Response.Status status;

        try {
            if (!avv.isAuthorized(username, sessionToken)&&(!avv.isAuthorizedToAccessTicket(ticketid, username))) {
                message= "Not authorized!!!";
                status = Response.Status.BAD_REQUEST;
                avv.invalidateSessionToken(username);
            } else {
                response = sic.getTicketInfo(ticketid, username);
                return response;
            }
        } catch (Exception e) {
            message = "Internal server error. Please try again later1.";
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        response = responseWrapper.generateResponse(null,status, message);
        return response;
    }

    @GET
    @ApiOperation(value = "shopinfo")
    @Path("/shopDetail/{shopid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shop Info Retrieved", response = Shop.class),
            @ApiResponse(code = 400, message = "Parametri errati", response =  String.class),
            @ApiResponse(code = 500, message = "We messed up", response = String.class)})
    public Response getShopDetail(@HeaderParam("username") String username, @HeaderParam("session-token") String sessionToken, @PathParam("shopid") int shopid){
        String message;
        Response response;
        Response.Status status;

        try {
            if (!avv.isAuthorizedAndManager(username, sessionToken)&&(!avv.isAuthorizedToAccessShop(shopid, username))) {
                message= "Not authorized!!!";
                status = Response.Status.BAD_REQUEST;
                avv.invalidateSessionToken(username);
            } else {
                response = sic.getShopInfo(shopid, username);
                return response;
            }
        } catch (Exception e) {
            message = "Internal server error. Please try again later1.";
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        response = responseWrapper.generateResponse(null,status, message);
        return response;
    }

    @GET
    @ApiOperation(value = "getShops")
    @Path("/shops")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shops Retrieved",response = List.class),
            @ApiResponse(code = 400, message = "Parametri errati", response = String.class),
            @ApiResponse(code = 500, message = "We messed up", response = String.class)})
    public Response getShops(@HeaderParam("username") String username, @HeaderParam("session-token") String sessionToken){
        String message;
        Response response;
        Response.Status status;

        try {
            if (!avv.isAuthorizedAndManager(username, sessionToken)) {
                message= "Not authorized!!!";
                status = Response.Status.BAD_REQUEST;
            } else {
                response = sic.getShops(username);
                return response;
            }
        } catch (Exception e) {
            message = "Internal server error. Please try again later1.";
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        response = responseWrapper.generateResponse(null,status, message);
        return response;
    }

    @POST
    @ApiOperation(value = "register shop")
    @Path("/newshop")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shops succefully registered", response = Shop.class),
            @ApiResponse(code = 400, message = "Parametri errati", response = String.class),
            @ApiResponse(code = 500, message = "We messed up", response = String.class)})
    public Response registerNewShop(@HeaderParam("username") String username,@HeaderParam("sessionToken") String sessionToken, @Valid @RequestMap ShopProto shop){
        String message;
        Response response;

        Response.Status status;

        try {
            if (!avv.isAuthorizedAndManager(username, sessionToken)) {
                message= "Not authorized!!!";
                status = Response.Status.UNAUTHORIZED;
                System.out.println("we are in here not authorized");
                avv.invalidateSessionToken(username);
                return responseWrapper.generateResponse(null, status, message);

            } else {
                try{
                    boolean bol = msc.checkIfCorruptedData(shop);
                    System.out.println(bol);
                    if(bol){
                        System.out.println("corrupte");
                        message = "Corrupted Data";
                        status = Response.Status.BAD_REQUEST;
                        String token = avv.getNewSessionToken(username);
                        return responseWrapper.generateResponse(token,status, message);
                    }else{
                        response = msc.registerNewShop(shop, username);
                        return response;
                    }
                }catch (Exception e){
                    message = "Internal server error. Please try again later1.";
                    status = Response.Status.INTERNAL_SERVER_ERROR;
                    return responseWrapper.generateResponse(null,status, message);

                }
            }
        } catch (Exception e) {
            message = "Internal server error. Please try again later1.";
            status = Response.Status.INTERNAL_SERVER_ERROR;
            return responseWrapper.generateResponse(null,status, message);

        }
    }

    @POST
    @ApiOperation(value = "register shop shifts")
    @Path("/newshopshifts")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shops succefully registered the shifts", response = List.class),
            @ApiResponse(code = 400, message = "Parametri errati", response =String.class),
            @ApiResponse(code = 500, message = "We messed up", response =String.class)})
    public Response registerNewShopShifts(@HeaderParam("username") String username, @HeaderParam("session-token") String sessionToken, @Valid @RequestMap List<ShopShiftProto> shopShifts){
        String message;
        Response response;
        Response.Status status;


        try {
            if (!avv.isAuthorizedAndManager(username, sessionToken)) {
                System.out.println("unauthorized!");
                avv.invalidateSessionToken(username);
                message= "Not authorized!!!";
                status = Response.Status.BAD_REQUEST;
            } else {
                System.out.println("starting to add");
                response = msc.registerNewShiftShop(shopShifts, username);
                return response;
            }
        } catch (Exception e) {
            message = "Internal server error. Please try again later1.";
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        response = responseWrapper.generateResponse(null, status, message);
        return response;
    }
}