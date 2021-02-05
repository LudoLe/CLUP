package polimi.it.AMB;


import Exceptions.CredentialsException;
import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.Ticket;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.UserService;
import polimi.it.UTILS.*;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import prototypes.Credentials;
import prototypes.RegistrationCredentials;
import prototypes.UserInfo;
import responseWrapper.ResponseWrapper;

import java.util.List;

@Stateless(name = "AccountManagerService")
public class AccountManagerComponent {

    @EJB(name = "services/UserService")
    private UserService userService;

    @EJB(name = "ResponseWrapper")
    private ResponseWrapper responseWrapper ;

    public AccountManagerComponent()
    {}

    public Boolean registrationManagement(RegistrationCredentials credentials) throws Exception{
            return userService.createUser(credentials.getUsername(), credentials.getPassword(), credentials.getEmail(), credentials.getIsManager(), credentials.getPhoneNumber() );
  }
    public Boolean loginManagement(Credentials credentials) throws Exception{
        return userService.checkCredentials(credentials.getUsername(), credentials.getPassword());
    }

    public void logoutManagement(String username) throws Exception{
       userService.logOut(username);
       return;
    }

    public Response getUserInfo(String username) throws Exception{
        Response.Status status;
       try {
           User user = userService.findByUsername(username);
           return responseWrapper.generateResponse(Response.Status.OK, user);

       }catch (Exception e){
           status  = Response.Status.INTERNAL_SERVER_ERROR;
           String message = "problems retrieving info";
           return responseWrapper.generateResponse(status,message);
       }
    }


    }





