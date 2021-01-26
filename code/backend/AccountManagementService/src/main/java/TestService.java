
import javax.ws.rs.*;

@Path("/hello-world")
public class TestService {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}