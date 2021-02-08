package responseWrapper;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import java.io.Serializable;


@Stateless(name= "ResponseWrapper")
public class ResponseWrapper implements Serializable {



    public ResponseWrapper()
    {}

    public Response generateResponse(Response.Status status, Object o){
        Gson builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return Response.status(status).entity(builder.toJson(o)).build();
    }


}
