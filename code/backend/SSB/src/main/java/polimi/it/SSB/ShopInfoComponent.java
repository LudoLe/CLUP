package polimi.it.SSB;

import polimi.it.DL.entities.Ticket;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.UserService;
import responseWrapper.ResponseWrapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless(name = "SIC")
public class ShopInfoComponent {

    @EJB(name = "services/UserService")
    private UserService userService;

    @EJB(name = "ResponseWrapper")
    private ResponseWrapper responseWrapper ;


    public Response getTickets(String username){
       Response response;
       Response.Status status;

        try{
            User user= userService.findByUsername(username);
            List<Ticket> tickets = user.getTickets();
            status = Response.Status.OK;
            response = responseWrapper.generateResponse(status,tickets);
            return response;

        }catch(Exception e){
            String message = "couldnt retrieve tickets";
            status = Response.Status.INTERNAL_SERVER_ERROR;
            response = responseWrapper.generateResponse(status,message);
            return response;

        }

    }


}
