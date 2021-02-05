package polimi.it.AMW;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jsonObjects.Credentials;
import org.apache.commons.lang3.StringEscapeUtils;
import polimi.it.AMB.AccountManagerComponent;
import polimi.it.DL.entities.User;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/AMW")
@Api(value = "Methods")
public class Gateway {
     @EJB(name="AccountManagerService")
    AccountManagerComponent ams;


    @GET
    @ApiOperation(value = "Register a new store to CLup")
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered", response = Response.class),
            @ApiResponse(code = 400, message = "Registration failed"),
            @ApiResponse(code = 500, message = "Invalid payload/error")})
    public Response userRegistration(Credentials credentials){
        String usrn = null;
        String pwd = null;
        try {
            usrn = StringEscapeUtils.escapeJava(credentials.getEmail());
            pwd = StringEscapeUtils.escapeJava(credentials.getPassword());
            if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty()) {
                throw new Exception("Missing or empty credential value");
            }

        } catch (Exception e) {
            // for debugging only e.printStackTrace();
            String message= "Bad Request";
            return Response.status(Response.Status.BAD_REQUEST).entity(builder.toJson(message)).build();
        }
        User user;
        try {
            user = ams.manageLogin(credentials);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Bad request.");
            return;
        }

        // If the user exists, add info to the session and go to home page, otherwise
        // show login page with error message
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Incorrect credentials");
        } else {
            if(user.getBlocked() == 1) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("This user is blocked.");
                return;
            }
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("log", new Date());
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            String redirectPath = request.getServletContext().getContextPath() + (user.getAdmin() == 1 ? "/admin" : "/user");
            response.getWriter().print(redirectPath);
        }
    }
        Gson builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return Response.status(Response.Status.OK).entity(builder.toJson(message)).build();
    }

}
