package polimi.it.AMW;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ejb.EJB;
import javax.net.ssl.SSLEngineResult;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.security.Timestamp;

@Path("/AMW")
public class Gateway {

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register()
    {
        String message="ciau";
        Gson builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return Response.status(Response.Status.OK).entity(builder.toJson(message)).build();
    }

}
