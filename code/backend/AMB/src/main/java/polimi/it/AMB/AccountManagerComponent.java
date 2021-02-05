package polimi.it.AMB;


import Exceptions.CredentialsException;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.UserService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import prototypes.Credentials;
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

    public Boolean registrationManagement(RegistrationCredentials credentials) throws Exception{
            return userService.createUser(credentials.getUsername(), credentials.getPassword(), credentials.getEmail(), credentials.getIsManager(), credentials.getPhoneNumber() );
  }
    public Boolean loginManagement(Credentials credentials) throws Exception{
        return userService.checkCredentials(credentials.getUsername(), credentials.getPassword());
    }

    }





