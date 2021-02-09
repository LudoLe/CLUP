/*import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import polimi.it.AMB.AAVEngine;
import polimi.it.AMB.AccountManagerComponent;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.UserService;
import prototypes.Credentials;
import prototypes.RegistrationCredentials;
import responseWrapper.ResponseWrapper;
import javax.ws.rs.core.Response;

@ExtendWith(MockitoExtension.class)
public class AMCTest {

    @Mock
    UserService userService;

    @Mock
    ResponseWrapper responseWrapper;

    @Mock
    AAVEngine aavEngine;


    @Mock
    Response response;

    @Mock
    AccountManagerComponent amc;

    @Mock
    RegistrationCredentials registrationCredentials;

    @Mock
    Credentials credentials;

    @Mock
    Response.Status status, status2;

    @Mock
    User user;

    @Mock
    Argon2PasswordEncoder encoder;


    


    private static final int ARGON2_ITERATIONS = 2;
    private static final int ARGON2_MEMORY = 16384;
    private static final int ARGON2_PARALLELISM = 1;
    private static final int ARGON2_SALT_LENGTH = 64;
    private static final int ARGON2_HASH_LENGTH = 128;

    @Test
    public void checkAMC() throws Exception {
        amc= new AccountManagerComponent(aavEngine, userService, responseWrapper, encoder);

        registrationManagementTest(amc);
        getUserInfoTest(amc);
        loginManagementTest(amc);
    }

    public void getUserInfoTest(AccountManagerComponent amc) throws Exception {

        status = Response.Status.OK;
        String username="paolo";

        when(userService.findByUsername(username)).thenReturn(user);
        when(responseWrapper.generateResponse(status, user)).thenReturn(response);
        assertEquals(response,amc.getUserInfo(username));

        status  = Response.Status.INTERNAL_SERVER_ERROR;
        String message = "problems retrieving info";

        doThrow(new Exception()).when(userService).findByUsername(username);
        when(responseWrapper.generateResponse(status, message)).thenReturn(response);
        assertEquals(response,amc.getUserInfo(username));

    }

        public void loginManagementTest(AccountManagerComponent amc) throws Exception {

        String username="paolo";
        status = Response.Status.NOT_FOUND;

        when(credentials.getUsername()).thenReturn(username);
        when(userService.userExists(username)).thenReturn(null);
        when(responseWrapper.generateResponse(status, "no user with such username")).thenReturn(response);
        assertEquals(response,amc.loginManagement(credentials));

        when(userService.userExists(username)).thenReturn(user);
        when(credentials.getPassword()).thenReturn("null");
        when(credentials.getUsername()).thenReturn(username);
        when(user.getPassword()).thenReturn("null");
        when(encoder.matches(user.getPassword(), credentials.getPassword())).thenReturn(true);
        when(amc.checkCred(user.getPassword(), credentials.getPassword())).thenReturn(true);
        when(aavEngine.getNewSessionToken(credentials.getUsername())).thenReturn("void");
        status = Response.Status.OK;
        when(responseWrapper.generateResponse(status,user)).thenReturn(response);
        assertEquals(response,amc.loginManagement(credentials));

        when(encoder.matches(user.getPassword(), credentials.getPassword())).thenReturn(false);
        when(amc.checkCred(user.getPassword(), credentials.getPassword())).thenReturn(false);
        status = Response.Status.BAD_REQUEST;
        when(responseWrapper.generateResponse(status,"I dati inseriti non sono corretti!")).thenReturn(response);
        assertEquals(response,amc.loginManagement(credentials));

        doThrow(new Exception()).when(userService).userExists(anyString());
        status = Response.Status.BAD_REQUEST;
        when(responseWrapper.generateResponse(status,"I dati inseriti non sono corretti!")).thenReturn(response);
        assertEquals(response,amc.loginManagement(credentials));
    }

        public void registrationManagementTest(AccountManagerComponent amc) throws Exception {

        status= Response.Status.OK;
        status2= Response.Status.INTERNAL_SERVER_ERROR;
        String message= "internal server error";

        when(registrationCredentials.getPassword()).thenReturn("");
        when(registrationCredentials.getIsManager()).thenReturn(true);
        when(registrationCredentials.getEmail()).thenReturn("");
        when(registrationCredentials.getPhoneNumber()).thenReturn("");
        when(registrationCredentials.getUsername()).thenReturn("");

        when(userService.createUser("","","",true, "")).thenReturn(user);
        when(responseWrapper.generateResponse(status, user)).thenReturn(response);
        assertEquals(response,amc.registrationManagement(registrationCredentials));
        doThrow(new Exception()).when(userService).createUser(anyString(), anyString(), anyString(), anyBoolean(), anyString());
        when(responseWrapper.generateResponse(status2, message)).thenReturn(response);
        assertEquals(response,amc.registrationManagement(registrationCredentials));
    }
}
*/