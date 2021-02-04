package polimi.it.AMB;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("login")
public class RegistrationUserHandler {
    @POST
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}
