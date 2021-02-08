package polimi.it.AMB;

import Responses.StringResponse;
import Responses.UserResponse;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
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

    private static final int ARGON2_ITERATIONS = 2;
    private static final int ARGON2_MEMORY = 16384;
    private static final int ARGON2_PARALLELISM = 1;
    private static final int ARGON2_SALT_LENGTH = 64;
    private static final int ARGON2_HASH_LENGTH = 128;

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

  /*this method check whether user
  -exists --> if it doesnt exists, the server notifies the client
  -provides the correct credentials --> if not, notifies the client
                                    --> if yes, access is allowd
  * */
    public Response loginManagement(Credentials credentials){
        Response.Status status;
        User user;
        try {
            user = userService.userExists(credentials.getUsername());
            if(user==null){
                status = Response.Status.NOT_FOUND;
                return responseWrapper.generateResponse(status, "no user with such username");
            }else if(!checkCred(user.getPassword(), credentials.getPassword())){
                status = Response.Status.UNAUTHORIZED;
                return responseWrapper.generateResponse(status, "credentials provided are not correct");
            }else{
                avv.getNewSessionToken(credentials.getUsername());
                status = Response.Status.OK;
                return responseWrapper.generateResponse(status, user);
            }

        }catch (Exception e){
            status = Response.Status.BAD_REQUEST;
            String message = "I dati inseriti non sono corretti!";
            return responseWrapper.generateResponse(status, message);
        }

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

    //check if credentials provided are correct
    private boolean checkCred(String userPassword, String passwordProvided){
        boolean passed;
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(ARGON2_SALT_LENGTH, ARGON2_HASH_LENGTH, ARGON2_PARALLELISM, ARGON2_MEMORY, ARGON2_ITERATIONS);
        passed = encoder.matches(passwordProvided, userPassword);
        return passed;
    }

    }





