package polimi.it.AMB;

import prototypes.Credentials;
import prototypes.RegistrationCredentials;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

@Stateless(name = "AAVEngine")
public class AAVEngine {

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
}
