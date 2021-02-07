package responseWrapper;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import polimi.it.DL.services.UserService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@Stateless(name= "ResponseWrapper")
public class ResponseWrapper implements Serializable {


    public ResponseWrapper()
    {}

    public Response generateResponse(String sessionToken, Response.Status status, Object o){
        Gson builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return Response.status(status).entity(builder.toJson(o)).header("session-token", sessionToken).build();
    }

}
