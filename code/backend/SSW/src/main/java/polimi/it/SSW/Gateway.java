package polimi.it.SSW;

import Responses.StringResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import polimi.it.AMB.AAVEngine;
import polimi.it.SSB.ManageShopComponent;
import polimi.it.SSB.ShopInfoComponent;
import prototypes.ShopProto;
import prototypes.ShopShift;
import responseWrapper.ResponseWrapper;

import javax.ejb.EJB;
import javax.faces.annotation.RequestMap;
import javax.validation.Valid;
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
            @ApiResponse(code = 200, message = "Successfully registered"),
            @ApiResponse(code = 400, message = "Registration failed"),
            @ApiResponse(code = 500, message = "Invalid payload/error")})
    public Response getUserTickets(@Context HttpHeaders headers){
        String message;
        Response response;
        String username= headers.getRequestHeader("username").get(0);
        String sessionToken= headers.getRequestHeader("sessionToken").get(0);
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
        response = responseWrapper.generateResponse(status, message);
        return response;
    }

    //TODO controllare roba relativa all'essere un manager
    @GET
    @ApiOperation(value = "ticketinfo")
    @Path("/ticketDetail/{ticketid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered"),
            @ApiResponse(code = 400, message = "Registration failed"),
            @ApiResponse(code = 500, message = "Invalid payload/error")})
    public Response getTicketDetail(@Context HttpHeaders headers, @PathParam("ticketid") int ticketid){
        String message;
        Response response;
        String username= headers.getRequestHeader("username").get(0);
        String sessionToken= headers.getRequestHeader("sessionToken").get(0);
        Response.Status status;

        try {
            if (!avv.isAuthorized(username, sessionToken)) {
                message= "Not authorized!!!";
                status = Response.Status.BAD_REQUEST;
            } else {
                response = sic.getTicketInfo(ticketid);
                return response;
            }
        } catch (Exception e) {
            message = "Internal server error. Please try again later1.";
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        response = responseWrapper.generateResponse(status, message);
        return response;
    }

    @GET
    @ApiOperation(value = "shopinfo")
    @Path("/shopDetail/{shopid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shop Info Retrieved"),
            @ApiResponse(code = 400, message = "Parametri errati"),
            @ApiResponse(code = 500, message = "We messed up")})
    public Response getShopDetail(@Context HttpHeaders headers, @PathParam("shopid") int shopid){
        String message;
        Response response;
        String username = headers.getRequestHeader("username").get(0);
        String sessionToken = headers.getRequestHeader("sessionToken").get(0);
        Response.Status status;

        try {
            if (!avv.isAuthorizedAndManager(username, sessionToken)) {
                message= "Not authorized!!!";
                status = Response.Status.BAD_REQUEST;
            } else {
                response = sic.getShopInfo(shopid);
                return response;
            }
        } catch (Exception e) {
            message = "Internal server error. Please try again later1.";
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        response = responseWrapper.generateResponse(status, message);
        return response;
    }

    @GET
    @ApiOperation(value = "getShops")
    @Path("/shops")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shops Retrieved"),
            @ApiResponse(code = 400, message = "Parametri errati"),
            @ApiResponse(code = 500, message = "We messed up")})
    public Response getShops(@Context HttpHeaders headers){
        String message;
        Response response;
        String username = headers.getRequestHeader("username").get(0);
        String sessionToken = headers.getRequestHeader("sessionToken").get(0);
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
        response = responseWrapper.generateResponse(status, message);
        return response;
    }

    @POST
    @ApiOperation(value = "register shop")
    @Path("/newshop")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shops succefully registered", response =StringResponse.class),
            @ApiResponse(code = 400, message = "Parametri errati", response =StringResponse.class),
            @ApiResponse(code = 500, message = "We messed up", response =StringResponse.class)})
    public Response registerNewShop(@HeaderParam("username") String username,@HeaderParam("sessionToken") String sessionToken, @Valid @RequestMap ShopProto shop){
        String message;
        Response response;

        Response.Status status;

        try {
            if (!avv.isAuthorizedAndManager(username, sessionToken)) {
                message= "Not authorized!!!";
                status = Response.Status.BAD_REQUEST;
                System.out.println("we are in here not authorized");
            } else {
                response = msc.registerNewShop(shop);
                return response;

            }
        } catch (Exception e) {
            message = "Internal server error. Please try again later1.";
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        response = responseWrapper.generateResponse(status, new StringResponse(message));
        return response;
    }

    @POST
    @ApiOperation(value = "register shop")
    @Path("/newshopshifts")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shops succefully registered", response =StringResponse.class),
            @ApiResponse(code = 400, message = "Parametri errati", response =StringResponse.class),
            @ApiResponse(code = 500, message = "We messed up", response =StringResponse.class)})
    public Response registerNewShop(@Context HttpHeaders headers,@Valid @RequestMap List<ShopShift> shopShifts){
        String message;
        Response response;
        String username = headers.getRequestHeader("username").get(0);
        String sessionToken = headers.getRequestHeader("sessionToken").get(0);
        Response.Status status;

        try {
            if (!avv.isAuthorizedAndManager(username, sessionToken)) {
                message= "Not authorized!!!";
                status = Response.Status.BAD_REQUEST;
            } else {
                response = msc.registerNewShiftShop(shopShifts);
                return response;
            }
        } catch (Exception e) {
            message = "Internal server error. Please try again later1.";
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        response = responseWrapper.generateResponse(status, new StringResponse(message));
        return response;
    }
}