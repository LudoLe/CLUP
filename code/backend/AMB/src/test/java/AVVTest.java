/*import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import polimi.it.AMB.AAVEngine;
import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.Ticket;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.ShopService;
import polimi.it.DL.services.TicketService;
import polimi.it.DL.services.UserService;
import prototypes.Credentials;
import prototypes.RegistrationCredentials;


@ExtendWith(MockitoExtension.class)
public class AVVTest {



    @Mock
    RegistrationCredentials credentials;

    @Mock
    Credentials credentials2;

    @Mock
    UserService userService;

    @Mock
    ShopService shopService;

    @Mock
    TicketService ticketService;

    @Mock
    Ticket ticket;

    @Mock
    Shop shop;

    @Mock
    User manager;

    @Mock
    User user, user2;

    @Test
    void AVVEngineTest() throws Exception {

        AAVEngine avvEngine = new AAVEngine(userService, shopService, ticketService);
        checkAuthorizationsTest(avvEngine);
        checkRegistrationTest(avvEngine);
        checkOtherStuff(avvEngine);

    }

    void checkOtherStuff(AAVEngine avvEngine) throws Exception {
        String username="1";
        when(userService.newSessionToken(username)).thenReturn("1");
        assertEquals("1", (avvEngine.getNewSessionToken(username)));

        when(userService.findByUsername(username)).thenReturn(user2);
        when(user2.getSessionToken()).thenReturn("1");
        assertEquals("1", (avvEngine.getSessionToken(username)));

        when(credentials2.getPassword()).thenReturn(null);
        assertTrue(avvEngine.isEmpty(credentials2));

        when(credentials2.getPassword()).thenReturn("1");
        when(credentials2.getUsername()).thenReturn("1");
        assertFalse(avvEngine.isEmpty(credentials2));

    }

        void checkAuthorizationsTest(AAVEngine avvEngine) throws Exception {

        int shopid = 0;
        int ticketid = 12;

        String username = "paolo";
        String token = "1111";

        when(ticketService.find(ticketid)).thenReturn(ticket);
        doReturn(user).when(ticket).getUser();
        when(user.getUsername()).thenReturn(username);

        assertTrue(avvEngine.isAuthorizedToAccessTicket(ticketid, username));
        assertFalse(avvEngine.isAuthorizedToAccessTicket(ticketid, "alessia"));
        assertFalse(avvEngine.isAuthorizedToAccessTicket(ticketid, "alessia"));

        when(shopService.find(shopid)).thenReturn(shop);
        doReturn(manager).when(shop).getManager();
        when(shop.getManager()).thenReturn(manager);
        when(manager.getUsername()).thenReturn(username);

        assertTrue(avvEngine.isAuthorizedToAccessShop(0, username));
        assertFalse(avvEngine.isAuthorizedToAccessShop(0, "alessia"));


        when(userService.isAuthorizedAndManager(username, token)).thenReturn(true);
        assertTrue(avvEngine.isAuthorizedAndManager(username, token));

        when(userService.isAuthorized(username, token)).thenReturn(false);
        assertFalse(avvEngine.isAuthorized(username, token));
    }

    void checkRegistrationTest(AAVEngine avvEngine){
        when(credentials.getEmail()).thenReturn(null);
        assertEquals("Credentials are empty", avvEngine.checkRegistration(credentials));

        when(credentials).thenReturn(null);
        assertEquals("Credentials are empty", avvEngine.checkRegistration(credentials));

        when(credentials.getPassword()).thenReturn(null);
        assertEquals("Credentials are empty", avvEngine.checkRegistration(credentials));


        when(credentials.getEmail()).thenReturn("null");
        when(credentials.getPassword()).thenReturn("null");
        when(credentials.getPassword2()).thenReturn("noll");
        assertEquals("Passwords dont match", avvEngine.checkRegistration(credentials));

        when(credentials.getPassword()).thenReturn("null");
        when(credentials.getPassword2()).thenReturn("null");
        when(credentials.getUsername()).thenReturn("Il ricorso a uno o due comuni è stato già abbastanza");
        assertEquals("Something is too long!!!", avvEngine.checkRegistration(credentials));

        when(credentials.getPassword()).thenReturn("null");
        when(credentials.getPassword2()).thenReturn("null");
        when(credentials.getUsername()).thenReturn("ciao");
        assertEquals("OK", avvEngine.checkRegistration(credentials));
    }




    }










*/





