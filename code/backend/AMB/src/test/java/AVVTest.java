import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.servlet.Registration;
import org.mockito.Mock;
import polimi.it.AMB.AAVEngine;
import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.ShopService;
import polimi.it.DL.services.TicketService;
import polimi.it.DL.services.UserService;
import prototypes.RegistrationCredentials;

@ExtendWith(MockitoExtension.class)
public class AVVTest {



    @Mock
    RegistrationCredentials credentials;

    @Mock
    UserService userService;

    @Mock
    ShopService shopService;

    @Mock
    TicketService ticketService;

    @Mock
    EntityManager em;

    @Mock
    Shop shop;

    @Mock
    User manager;

    @Test
    void checkRegistrationTest() throws Exception {

        AAVEngine avvEngine = new AAVEngine(userService, shopService, ticketService);
        int shopid = 0;
        String username = "paolo";
        String token = "1111";


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
        



    }




    }
















