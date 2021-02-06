package polimi.it.SSW;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import polimi.it.AMB.AAVEngine;
import polimi.it.AMB.AccountManagerComponent;
import polimi.it.SSB.ShopInfoComponent;
import responseWrapper.ResponseWrapper;

import javax.ejb.EJB;
import javax.faces.annotation.RequestMap;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


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
}