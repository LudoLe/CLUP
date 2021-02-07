package polimi.it.QSW;

import Responses.BooleanResponse;
import Responses.StringResponse;
import Responses.TimeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import polimi.it.AMB.AAVEngine;
import polimi.it.QSB.LineUpComponent;
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

@Path("/QSW")
@Api(value = "Methods")
public class Gateway {

    @EJB(name = "LineUpComponent")
    LineUpComponent luc;

    @EJB(name = "ResponseWrapper")
    private ResponseWrapper responseWrapper ;

    @EJB(name = "AAVEngine")
    private AAVEngine aavEngine ;



   /* @POST
    @ApiOperation(value = "can enqueue")
    @Path("/canenqueue")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Can enqueue", response = TimeResponse.class),
            @ApiResponse(code = 400, message = "enqueuement failed", response = StringResponse.class),
            @ApiResponse(code = 500, message = "Invalid payload/error", response = StringResponse.class)})
    public Response preEnqueue(@Valid @RequestMap PreEnqueuementData enqueueData , @HeaderParam("username") String username) throws Exception {
        String message = "something wrong";
        Response response;
        Response.Status status;
        try {
            if(!luc.checkIfAlreadyEnqueued(username)) {
                return luc.managePreEnqueuement(enqueueData);
            }else{
                message = "User already Has One Ticket";
                status = Response.Status.PRECONDITION_FAILED;
                response = responseWrapper.generateResponse(null,status, message);
            }
        } catch (Exception e) {
            message = "Internal server error. Please try again later1.";
            status = Response.Status.INTERNAL_SERVER_ERROR;
            aavEngine.invalidateSessionToken(username);
            response = responseWrapper.generateResponse(null,status, message);

        }
        return response;
    }
    */



    @POST
    @ApiOperation(value = "enqueue")
    @Path("/enqueue")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully enqueued", response = Ticket.class),
            @ApiResponse(code = 400, message = "Registration failed", response = String.class),
            @ApiResponse(code = 500, message = "Invalid payload/error", response = String.class)})
    public Response enqueue(@Context HttpServletResponse httpHeader,@HeaderParam("username") String username, @HeaderParam("session-token") String sessionToken , @Valid @RequestMap EnqueueData enqueueData) throws Exception {
        Response response;
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        String message;
        httpHeader.setHeader("session-token", "");

        if(!aavEngine.isAuthorized(username, sessionToken)){
            aavEngine.invalidateSessionToken(username);
           response = responseWrapper.generateResponse( status, "not authorized, you are being logged out" );
           return response;
        }
        if(luc.corruptedData(enqueueData)){
            response = responseWrapper.generateResponse( status, "data corrupted" );
            return response;
        }
        if(!luc.noSenseTime(enqueueData).equals("OK")){
            aavEngine.invalidateSessionToken(username);
            response = responseWrapper.generateResponse( status, luc.noSenseTime(enqueueData) );
            return response;
        }
        try {
            httpHeader.setHeader("session-token", aavEngine.getNewSessionToken(username));
            response = luc.enqueue(enqueueData, username);

        } catch (Exception e) {
            message = "Internal server error. Please try again later1.";
            response=responseWrapper.generateResponse(status, new StringResponse(message));
        }
        return response;
    }


    @GET
    @ApiOperation(value = "dequeue")
    @Path("/dequeue")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully dequeued"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 500, message = "Something went wrong")})
    public Response dequeue(@HeaderParam("ticketid") int tickeid, @HeaderParam("username") String username,  @Context HttpServletResponse httpHeader) {
        String message;
        Response response;
        httpHeader.setHeader("session-token", "");

        try {
            if(!luc.checkProperty(username, tickeid)) {
                httpHeader.setHeader("session-token", aavEngine.getNewSessionToken(username));
                return luc.dequeue(tickeid);
            }else{
                message = "Not your ticket";
                aavEngine.invalidateSessionToken(username);
                response=responseWrapper.generateResponse(Response.Status.BAD_REQUEST, message);
                return response;
            }
        } catch (Exception e) {
            message = "Internal server error. Please try again later2.";
            response=responseWrapper.generateResponse(Response.Status.INTERNAL_SERVER_ERROR, message);
            return response;
        }
    }

    @GET
    @ApiOperation(value = "check if enqueued")
    @Path("/checkenqueued")
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
        httpHeader.setHeader("session-token", "");

        boolean bol;
        try {
            httpHeader.setHeader("session-token", aavEngine.getNewSessionToken(username));
            bol = luc.checkIfAlreadyEnqueued(username);
            status = Response.Status.OK;

        } catch (Exception e) {
            bol = false;
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        response = responseWrapper.generateResponse(status, new BooleanResponse(bol));
        return response;
    }

}
