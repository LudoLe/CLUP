package polimi.it.QSW;

import Responses.BooleanResponse;
import Responses.StringResponse;
import Responses.TimeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import polimi.it.QSB.LineUpComponent;
import prototypes.*;
import responseWrapper.ResponseWrapper;

import javax.ejb.EJB;
import javax.faces.annotation.RequestMap;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/QSW")
@Api(value = "Methods")
public class Gateway {

    @EJB(name = "LineUpComponent")
    LineUpComponent luc;

    @EJB(name = "ResponseWrapper")
    private ResponseWrapper responseWrapper ;



    @POST
    @ApiOperation(value = "can enqueue")
    @Path("/canenqueue")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Can enqueue", response = TimeResponse.class),
            @ApiResponse(code = 400, message = "enqueuement failed", response = StringResponse.class),
            @ApiResponse(code = 500, message = "Invalid payload/error", response = StringResponse.class)})
    public Response preEnqueue(@Valid @RequestMap PreEnqueuementData enqueueData , @HeaderParam("username") String username) {
        String message = "something wrong";
        Response response;
        Response.Status status;
        try {
            if(!luc.checkIfAlreadyEnqueued(username)) {
                return luc.managePreEnqueuement(enqueueData);
            }else{
                message = "User already Has One Ticket";
                status = Response.Status.OK;
            }
        } catch (Exception e) {
            message = "Internal server error. Please try again later1.";
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        response = responseWrapper.generateResponse(status, message);
        return response;
    }


    @POST
    @ApiOperation(value = "enqueue confirmation")
    @Path("/confirmEnqueuement")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully enqueued", response = StringResponse.class),
            @ApiResponse(code = 400, message = "Registration failed", response = StringResponse.class),
            @ApiResponse(code = 500, message = "Invalid payload/error", response = StringResponse.class)})
    public Response confirmEnqueuement(@Valid @RequestMap EnqueueData enqueueData) {
        Response response;
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        String message;
        try {
              response = luc.enqueue(enqueueData);

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
    public Response dequeue(@HeaderParam("ticketid") int tickeid, @HeaderParam("username") String username) {
        String message;
        Response response;
        try {
            if(!luc.checkProperty(username, tickeid)) {
                return luc.dequeue(tickeid);
            }else{
                message = "Not your ticket";
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
    public Response checkIfAlreadyEnqueued(@HeaderParam("username") String username) throws Exception {
        String message = "something wrong";
        Response response;
        Response.Status status;
        boolean bol;
        try {
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
