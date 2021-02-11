package polimi.it.QSW;

import Responses.BooleanResponse;
import Responses.StringResponse;
import Responses.TimeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import polimi.it.AMB.AAVEngine;
import polimi.it.DL.entities.Shop;
import polimi.it.QSB.LineUpComponent;
import polimi.it.QSB.QueueInfo;
import prototypes.*;
import responseWrapper.ResponseWrapper;

import javax.ejb.EJB;
import javax.faces.annotation.RequestMap;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/QSW")
@Api(value = "Methods")
public class Gateway {

    @EJB(name = "LineUpComponent")
    LineUpComponent luc;

    @EJB(name = "ResponseWrapper")
    private ResponseWrapper responseWrapper ;

    @EJB(name = "AAVEngine")
    private AAVEngine aavEngine ;

    @EJB(name = "QueueInfo")
    private QueueInfo qi ;



    /**
     * this method allows a client to enqueue in a shop
     * @param username used to validate the request and to enqueue the client
     * @param sessionToken used to validate the request along with the username
     * @param enqueueData object that contains necessary data to perform the enqueuement correctly such as the permanence
     *                    time in the shop and the time to get to the shop from the user position
     * @param httpHeader used to set custom headers in the response
     * @return a response object
     * */
    @POST
    @ApiOperation(value = "enqueue")
    @Path("/enqueue")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully enqueued", response = Ticket.class),
            @ApiResponse(code = 400, message = "enqueuement failed", response = String.class),
            @ApiResponse(code = 500, message = "Invalid payload/error", response = String.class)})
    public Response enqueue(@Context HttpServletResponse httpHeader,@HeaderParam("username") String username, @HeaderParam("session-token") String sessionToken , @Valid @RequestMap EnqueueData enqueueData) throws Exception {
        Response response;
        String message;

        if(!aavEngine.isAuthorized(username, sessionToken)){
            aavEngine.invalidateSessionToken(username);
           response = responseWrapper.generateResponse(Response.Status.UNAUTHORIZED, "not authorized, you are being logged out" );
           return response;
        }
        if(aavEngine.isManager(sessionToken)){
            aavEngine.invalidateSessionToken(username);
            response = responseWrapper.generateResponse( Response.Status.UNAUTHORIZED, "not authorized, you are being logged out" );
            return response;
        }
        if(luc.corruptedData(enqueueData)){
            response = responseWrapper.generateResponse( Response.Status.BAD_REQUEST, "data corrupted" );
            return response;
        }
        if(!luc.noSenseTime(enqueueData).equals("OK")){
            response = responseWrapper.generateResponse(Response.Status.BAD_REQUEST, luc.noSenseTime(enqueueData) );
            return response;
        }

        try {

            response = luc.enqueue(enqueueData, username);

        } catch (Exception e) {
            message = "Internal server error. Please try again later";
            response=responseWrapper.generateResponse(Response.Status.INTERNAL_SERVER_ERROR, new StringResponse(message));
        }
        return response;
    }

    @GET
    @ApiOperation(value = "queueInfo")
    @Path("/queueInfo/{shopid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shop Info Retrieved", response = Date.class),
            @ApiResponse(code = 401, message = "Non autorizzato", response =  String.class),
            @ApiResponse(code = 400, message = "Parametri errati", response =  String.class),
            @ApiResponse(code = 500, message = "We messed up", response = String.class)})
    public Response getQueueInfo(@HeaderParam("username") String username, @HeaderParam("session-token") String sessionToken, @PathParam("shopid") int shopid){
        String message;
        Response response;
        Response.Status status;
        try {
            if (!aavEngine.isAuthorized(username, sessionToken)) {
                message= "Not authorized";
                status = Response.Status.BAD_REQUEST;
                aavEngine.invalidateSessionToken(username);
            } else {
                response = qi.getQueueDuration(shopid);
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
    @ApiOperation(value = "dequeue")
    @Path("/dequeue{ticketid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully dequeued"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 400, message = "bad request"),
            @ApiResponse(code = 500, message = "Something went wrong")})
    public Response dequeue(@HeaderParam("session-token") String sessionToken, @PathParam("ticketid") int tickeid, @HeaderParam("username") String username,  @Context HttpServletResponse httpHeader) {
        String message;
        Response response;
        try {
            if (!aavEngine.isAuthorized(username, sessionToken)) {
                message= "Not authorized";
                aavEngine.invalidateSessionToken(username);
                response=responseWrapper.generateResponse(Response.Status.UNAUTHORIZED, message);
                return response;
            }
            if (!luc.checkIfAlreadyEnqueued(username)) {
                message= "you have no tickets!";
                response=responseWrapper.generateResponse(Response.Status.BAD_REQUEST, message);
                return response;
            }
            if(luc.checkProperty(username, tickeid)) {
                return luc.dequeue(tickeid);
            }else{
                message = "Not your ticket";
                aavEngine.invalidateSessionToken(username);
                response=responseWrapper.generateResponse(Response.Status.UNAUTHORIZED, message);
                return response;
            }
        } catch (Exception e) {
            message = "Internal server error. Please try again later.";
            response=responseWrapper.generateResponse(Response.Status.INTERNAL_SERVER_ERROR, message);
            return response;
        }
    }




    @GET
    @ApiOperation(value = "check if enqueued")
    @Path("/checkEnqueued")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message= "everything fine",response = BooleanResponse.class),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Something went wrong")})
    public Response checkIfAlreadyEnqueued( @Context HttpServletResponse httpHeader, @HeaderParam("username") String username) throws Exception {
        String message = "something wrong";
        Response response;
        Response.Status status;
       // httpHeader.setHeader("session-token", "");


        boolean bol;
        try {
          //  httpHeader.setHeader("session-token", aavEngine.getNewSessionToken(username));
            bol = luc.checkIfAlreadyEnqueued(username);
            status = Response.Status.OK;

        } catch (Exception e) {
            bol = false;
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        response = responseWrapper.generateResponse(status, new BooleanResponse(bol));
        return response;
    }


@GET
@ApiOperation(value = "check if enqueued")
@Path("/scanTicket/{ticketid}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApiResponses(value = {
        @ApiResponse(code = 200, message= "everything fine",response = BooleanResponse.class),
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 500, message = "Something went wrong")})
public Response scanTicket( @PathParam("ticketid") int ticketid, @HeaderParam("session-token") String sessionToken, @HeaderParam("username") String username) throws Exception {
    Response response;
    Response.Status status;
    String message;


    if (!aavEngine.isAuthorized(username, sessionToken)){
        message= "Not authorized";
        aavEngine.invalidateSessionToken(username);
        response=responseWrapper.generateResponse(Response.Status.UNAUTHORIZED, message);
        return response;
    }
    if (!luc.checkIfAlreadyEnqueued(username)) {
        message= "you have no tickets!";
        response=responseWrapper.generateResponse(Response.Status.BAD_REQUEST, message);
        return response;
    }
    if(luc.checkProperty(username, ticketid)) {
        return qi.scanTicket(ticketid);
    }
    else return responseWrapper.generateResponse(Response.Status.BAD_REQUEST,"not your ticket");

}

}


