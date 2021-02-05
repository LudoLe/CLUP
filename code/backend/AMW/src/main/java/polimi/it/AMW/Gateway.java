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
    @ApiOperation(value = "LogIn")
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered", response = Credentials.class),
            @ApiResponse(code = 400, message = "Registration failed"),
            @ApiResponse(code = 500, message = "Invalid payload/error")})
    public Response userLogin(@Valid @RequestMap Credentials credentials) {
        String message= "something wrong";
        try {
            if(!avv.isEmpty(credentials)) {
                   if(ams.loginManagement(credentials)) {
                       message = "Successfully logged in!";
                       return generateResponse(Response.Status.OK, message);
                   }
            }
        } catch (Exception e){
            message = "Internal server error. Please try again later1.";
        }
        return generateResponse(Response.Status.INTERNAL_SERVER_ERROR, message);
    }




    @POST
    @ApiOperation(value = "Register a new user to CLup")
    @Path("/registration")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered", response = RegistrationCredentials.class),
            @ApiResponse(code = 400, message = "Registration failed"),
            @ApiResponse(code = 500, message = "Invalid payload/error")})
    public Response userRegistration(@Valid @RequestMap RegistrationCredentials credentials) {
        String message= "something wrong";
        try {
            message = avv.checkRegistration(credentials);
        } catch (Exception e){
            message = "Internal server error. Please try again later1.";
        }

        if (message.equals("OK")){
            try {
              if(ams.registrationManagement(credentials)){
                  message="Successfully registered!";
                  return generateResponse(Response.Status.OK, message);
              }else throw new Exception();
            }
             catch (Exception e) {
                message = "Internal server error. Please try again later2.";
            }
        }
        return generateResponse(Response.Status.INTERNAL_SERVER_ERROR, message);

    }



 public Response generateResponse(Response.Status status, Object o){
     Gson builder = new GsonBuilder().create();
     return Response.status(status).entity(builder.toJson(o)).build();
 }

}