package polimi.it.AMW;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringEscapeUtils;
import polimi.it.AMB.AAVEngine;
import polimi.it.AMB.AccountManagerComponent;
import polimi.it.DL.entities.User;
import prototypes.*;
import utility.Utility;

import javax.ejb.EJB;
import javax.faces.annotation.RequestMap;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/AMW")
@Api(value = "Methods")
public class Gateway {

     @EJB(name="AccountManagerService")
     AccountManagerComponent ams;

     @EJB(name="AVVEngine")
     AAVEngine avv;


    @POST
    @ApiOperation(value = "Register a new store to CLup")
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered", response = Credentials.class),
            @ApiResponse(code = 400, message = "Registration failed"),
            @ApiResponse(code = 500, message = "Invalid payload/error")})
    public Response userRegistration(@Valid @RequestMap Credentials credentials){
        String message= "Bad Request";
        try {
            if (credentials== null || credentials.getEmail() == null || credentials.getPassword() == null) {
                throw new Exception("Missing or empty credential value");
            }
        } catch (Exception e) {
            return   generateResponse(Response.Status.BAD_REQUEST, message);
        }
        User user;
        try {
           // user = ams.manageLogin(credentials);
        } catch (Exception e) {
            return generateResponse(Response.Status.BAD_REQUEST, message);
        }

        Credentials cred= new Credentials();
        cred.setEmail("muh");
        cred.setPassword("muh");
        generateResponse(Response.Status.OK, cred);

        return         generateResponse(Response.Status.OK, cred);

    }




    @POST
    @ApiOperation(value = "Register a new store to CLup")
    @Path("/registration")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered", response = Credentials.class),
            @ApiResponse(code = 400, message = "Registration failed"),
            @ApiResponse(code = 500, message = "Invalid payload/error")})
    public Response userRegistration(@Valid @RequestMap RegistrationCredentials credentials) {
        String message= "something wrong";
        try {
            message = avv.checkRegistration(credentials);
        } catch (Exception e) {
            message = "Internal server error. Please try again later.";
            return generateResponse(Response.Status.INTERNAL_SERVER_ERROR, message);
        }

        if (message.equals("OK")) {
            User user = null;
            try {
                user = ams.registrationManagement(credentials);
            } catch (Exception e) {
                message = "Internal server error. Please try again later.";
                return generateResponse(Response.Status.INTERNAL_SERVER_ERROR, message);
            }
        }
        return generateResponse(Response.Status.INTERNAL_SERVER_ERROR, message);

    }



 public Response generateResponse(Response.Status status, Object o){
     Gson builder = new GsonBuilder().create();
     return Response.status(status).entity(builder.toJson(o)).build();
 }

}