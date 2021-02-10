import org.junit.jupiter.api.Test;
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
import polimi.it.SSB.ShopInfoComponent;
import responseWrapper.ResponseWrapper;
import javax.ws.rs.core.Response;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class SICTest {


    @Mock
    AAVEngine aavEngine;
    @Mock
    ResponseWrapper responseWrapper;
    @Mock
    UserService userService;
    @Mock
    TicketService ticketService;
    @Mock
    ShopService shopService;
    @Mock
    Ticket ticket;
    @Mock
    Shop shop;
    @Mock
    User user;
    @Mock
    List<Shop> shops;
    @Mock
    List<Ticket> tickets;

    @Test
    public void getTicketInfoTest() throws Exception {

        ShopInfoComponent sic = new ShopInfoComponent(aavEngine, shopService, responseWrapper, ticketService, userService);

        int ticketid = 1;
        Response.Status status;
        Response response;

        when(ticketService.find(ticketid)).thenReturn(null);
        status = Response.Status.NOT_FOUND;
        response = responseWrapper.generateResponse(status,"ticket not found");
        assertEquals(sic.getTicketInfo(ticketid), response);

        when(ticketService.find(ticketid)).thenReturn(ticket);
        status = Response.Status.OK;
        response = responseWrapper.generateResponse(status,ticket);
        assertEquals(sic.getTicketInfo(ticketid), response);

        doThrow(new Exception()).when(ticketService).find(ticketid);
        String message = "couldnt retrieve ticket info";
        status = Response.Status.INTERNAL_SERVER_ERROR;
        response = responseWrapper.generateResponse(status,message);
        assertEquals(sic.getTicketInfo(ticketid), response);
    }

    @Test
    public void getShopInfoTest() throws Exception {

        ShopInfoComponent sic = new ShopInfoComponent(aavEngine, shopService, responseWrapper, ticketService, userService);

        int shopid = 1;
        Response.Status status;
        Response response;

        when(shopService.find(shopid)).thenReturn(null);
        status = Response.Status.NOT_FOUND;
        response = responseWrapper.generateResponse(status,"no such shop");
        assertEquals(sic.getShopInfo(shopid), response);

        when(shopService.find(shopid)).thenReturn(shop);
        status = Response.Status.OK;
        response = responseWrapper.generateResponse(status,shop);
        assertEquals(sic.getShopInfo(shopid), response);

        doThrow(new Exception()).when(shopService).find(shopid);
        String message = "something went wrong";
        status = Response.Status.INTERNAL_SERVER_ERROR;
        response = responseWrapper.generateResponse(status,message);
        assertEquals(sic.getShopInfo(shopid), response);
    }

    @Test
    public void getShopsTest() throws Exception {

        ShopInfoComponent sic = new ShopInfoComponent(aavEngine, shopService, responseWrapper, ticketService, userService);

        Response response;
        Response.Status status;
        String username = "paolo";

        when(userService.findByUsername(username)).thenReturn(user);
        when(user.getShops()).thenReturn(null);
        status = Response.Status.OK;
        response = responseWrapper.generateResponse(status, "no shops registered at your name");
        assertEquals(sic.getShops(username), response);

        when(user.getShops()).thenReturn(shops);
        status = Response.Status.OK;
        response = responseWrapper.generateResponse(status, shops);
        assertEquals(sic.getShops(username), response);

        doThrow(new Exception()).when(userService).findByUsername(username);
        String message = "couldnt retrieve shops";
        status = Response.Status.INTERNAL_SERVER_ERROR;
        response = responseWrapper.generateResponse(status,message);
        assertEquals(sic.getShops(username), response);
    }

    @Test
    public void getTickets() throws Exception {
        ShopInfoComponent sic = new ShopInfoComponent(aavEngine, shopService, responseWrapper, ticketService, userService);

        Response response;
        Response.Status status;
        String username = "paolo";

        when(userService.findByUsername(username)).thenReturn(user);
        when(user.getTickets()).thenReturn(null);
        status = Response.Status.OK;
        response = responseWrapper.generateResponse(status, "no tickets yet");
        assertEquals(sic.getTickets(username), response);

        when(user.getTickets()).thenReturn(tickets);
        response = responseWrapper.generateResponse(status,tickets);
        assertEquals(sic.getTickets(username), response);

        doThrow(new Exception()).when(userService).findByUsername(username);
        String message = "couldnt retrieve tickets";
        status = Response.Status.INTERNAL_SERVER_ERROR;
        response = responseWrapper.generateResponse(status,message);
        assertEquals(sic.getTickets(username), response);
    }

    @Test
    public void getAllShopsTest(){
        ShopInfoComponent sic = new ShopInfoComponent(aavEngine, shopService, responseWrapper, ticketService, userService);

        Response response;
        Response.Status status;

        status = Response.Status.OK;
        response = responseWrapper.generateResponse(status, "no shops registered on clup yet");
        when(shopService.getAllShops()).thenReturn(null);
        assertEquals(sic.getAllShops(), response);

        when(shopService.getAllShops()).thenReturn(shops);
        response = responseWrapper.generateResponse(status, shops);
        assertEquals(sic.getAllShops(), response);
    }
}
