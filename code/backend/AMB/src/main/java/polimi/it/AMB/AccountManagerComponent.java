package polimi.it.AMB;

import Responses.StringResponse;
import Responses.UserResponse;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.UserService;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import prototypes.Credentials;
import prototypes.RegistrationCredentials;
import responseWrapper.ResponseWrapper;


@Stateless(name = "AccountManagerService")
public class AccountManagerComponent {

    @EJB(name = "services/UserService")
    private UserService userService;

    @EJB(name = "AAVEngine")
    private AAVEngine avv;

    @EJB(name = "ResponseWrapper")
    private ResponseWrapper responseWrapper ;

    public AccountManagerComponent()
    {}

    public Response registrationManagement(RegistrationCredentials credentials) throws Exception{
        Response response;
        Response.Status status;
        User user;
        try {
            user = userService.createUser(credentials.getUsername(), credentials.getPassword(), credentials.getEmail(), credentials.getIsManager(), credentials.getPhoneNumber() );
            status = Response.Status.OK;
            response=responseWrapper.generateResponse(status, user);
        }catch (Exception e){
            status = Response.Status.BAD_REQUEST;
            String message = "Esiste già un utente con questo username";
            response=responseWrapper.generateResponse(status, message);
        }
        return response;
  }
    public Response loginManagement(Credentials credentials) throws Exception{
        Response response;
        Response.Status status;
        User user;
        try {
            user = userService.checkCredentials(credentials.getUsername(), credentials.getPassword());
            status = Response.Status.OK;
            response=responseWrapper.generateResponse(status, user);
        }catch (Exception e){
            status = Response.Status.BAD_REQUEST;
            String message = "I dati inseriti non sono corretti!";
            response=responseWrapper.generateResponse(status, message);
        }
        return response;
    }

    public void logoutManagement(String username) throws Exception{
       userService.logOut(username);
    }

    public Response getUserInfo(String username){
       Response.Status status;
       try {
           status= Response.Status.OK;
           User user = userService.findByUsername(username);
           //return responseWrapper.generateResponse(avv.getNewSessionToken(username),status, new UserResponse(user, user.getSessionToken()));
           return responseWrapper.generateResponse(status, user);

       }catch (Exception e){
           status  = Response.Status.INTERNAL_SERVER_ERROR;
           String message = "problems retrieving info";
           //return responseWrapper.generateResponse(null,status, new StringResponse(message));
           return responseWrapper.generateResponse(status, message);

       }
    }


    }





