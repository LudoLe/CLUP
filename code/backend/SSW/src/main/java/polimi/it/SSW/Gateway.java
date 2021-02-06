package polimi.it.SSW;

import com.sun.net.httpserver.HttpContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import polimi.it.AMB.AAVEngine;
import polimi.it.AMB.AccountManagerComponent;
import polimi.it.DL.entities.Shop;
import polimi.it.SSB.ShopInfoComponent;
import responseWrapper.ResponseWrapper;

import javax.ejb.EJB;
import javax.faces.annotation.RequestMap;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;


@Path("/SSW")
@Api(value = "Methods")
public class Gateway {

    @EJB(name = "ResponseWrapper")
    private ResponseWrapper responseWrapper ;

    @EJB(name = "AVVEngine")
    AAVEngine avv;

    @EJB(name = "SIC")
    ShopInfoComponent sic;

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
            @ApiResponse(code = 200, message = "Shops succefully registered"),
            @ApiResponse(code = 400, message = "Parametri errati"),
            @ApiResponse(code = 500, message = "We messed up")})
    public Response registerNewShop(@Context HttpHeaders headers,@Valid @RequestMap Shop shop){
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
}