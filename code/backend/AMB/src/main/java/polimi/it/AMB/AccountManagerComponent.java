package polimi.it.AMB;


import Exceptions.CredentialsException;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.UserService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import prototypes.RegistrationCredentials;


@Stateless(name = "AccountManagerService")
public class AccountManagerComponent {

    @EJB(name = "services/UserService")
    private UserService userService;

    public AccountManagerComponent()
    {}

    private String generateSessionToken() {
        return RandomStringUtils.random(255);
    }

    public User registrationManagement(RegistrationCredentials credentials){
        try {
            User user=null;
            user = userService.createUser(credentials.getUsername(), credentials.getEmail(), credentials.getPassword());
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    }





