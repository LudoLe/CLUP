package polimi.it.AMB;

import polimi.it.DL.services.UserService;
import prototypes.Credentials;
import prototypes.RegistrationCredentials;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name = "AAVEngine")
public class AAVEngine {

    @EJB(name = "services/UserService")
    private UserService userService;

    public static final int MAX_USERNAME_LENGTH = 40;
    public static final int MAX_EMAIL_LENGTH = 100;
    public static final int MAX_PWD_LENGTH = 100;

    public String checkRegistration(RegistrationCredentials credentials) {
        String message=null;
        if(credentials == null || credentials.getEmail() == null || credentials.getPassword() == null) {
            message = "Credentials are empty";
            return message;
        }

        if (!credentials.getPassword().contentEquals(credentials.getPassword())) {
             message = "Passwords dont match";
            return message;
        }
        if (credentials.getEmail().length() >= MAX_USERNAME_LENGTH || credentials.getEmail().length() > MAX_EMAIL_LENGTH
                || credentials.getPassword().length() > MAX_PWD_LENGTH) {
             message = "Something is too long!!!";
            return message;
        }
        return "OK";
    }

    public Boolean isEmpty(Credentials credentials){
        Boolean message=true;
        if(credentials == null || credentials.getPassword() == null) {
            return true;
           }
         return false;
    }

    public Boolean isAuthorized(String username, String sessionToken) throws Exception {
        return  userService.isAuthorized(username, sessionToken);
    }

    public Boolean isAuthorizedAndManager(String username, String sessionToken) throws Exception {
        return  userService.isAuthorizedAndManager(username, sessionToken);
    }

    public String getNewSessionToken(String username, String sessionToken) throws Exception {
        return  userService.newSessionToken(username);
    }

}
