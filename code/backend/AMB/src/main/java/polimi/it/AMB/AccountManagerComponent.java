package polimi.it.AMB;


import Exceptions.CredentialsException;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.UserService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.lang3.RandomStringUtils;
import prototypes.Credentials;
import org.apache.commons.validator.routines.EmailValidator;


@Stateless(name = "AccountManagerService")
public class AccountManagerComponent {

    @EJB(name = "services/UserService")
    private UserService userService;

    public AccountManagerComponent()
    {}

    private String generateSessionToken() {
        return RandomStringUtils.random(255);
    }

    public static boolean isValidMailAddress(String email){
        return EmailValidator.getInstance().isValid(email);
    }

    public User manageLogin(Credentials c) throws CredentialsException {
        try
        {
            return userService.checkCredentials(c.getEmail(), c.getPassword());
        }
        catch(Exception e)
        {
            throw new CredentialsException("Invalid credentials");
        }
    }



}
