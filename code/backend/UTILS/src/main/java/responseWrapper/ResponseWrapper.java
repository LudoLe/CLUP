package responseWrapper;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import polimi.it.DL.services.UserService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless(name= "ResponseWrapper")
public class ResponseWrapper implements Serializable {



    public ResponseWrapper()
    {}

    public Response generateResponse(Response.Status status, Object o){
        Gson builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return Response.status(status).entity(builder.toJson(o)).build();
    }


}
