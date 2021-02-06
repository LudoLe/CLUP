package responseWrapper;


import Responses.ShopResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import prototypes.Shop;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@Stateless(name= "ResponseWrapper")
public class ResponseWrapper implements Serializable {

    public ResponseWrapper()
    {}

    public Response generateResponse(Response.Status status, Object o){
        Gson builder = new GsonBuilder().create();
        return Response.status(status).entity(builder.toJson(o)).build();
    }

}
