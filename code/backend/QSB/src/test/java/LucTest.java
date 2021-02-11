import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import polimi.it.DL.services.ShopService;
import polimi.it.DL.services.TicketService;
import polimi.it.DL.services.UserService;
import polimi.it.QSB.LineUpComponent;
import prototypes.EnqueueData;
import responseWrapper.ResponseWrapper;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class LucTest{

    @Mock 
    private ResponseWrapper responseWrapper;
    @Mock 
    private ShopService shopService;
    @Mock 
    private TicketService ticketService;
    @Mock
    private UserService userService;
    @Mock
    private EnqueueData enqueueData;

    @Test
    public void checkIfAlreadyEnqueuedTest() throws Exception {
        LineUpComponent luc = new LineUpComponent(true,responseWrapper, shopService, ticketService, userService);

        String username = "paolo";
        when(ticketService.alreadyHasTicket(username)).thenReturn(true);
        assertTrue(luc.checkIfAlreadyEnqueued(username));

    }

    @Test
    public void checkPropertyTest() throws Exception {
        LineUpComponent luc = new LineUpComponent(true,responseWrapper, shopService, ticketService, userService);

        String username = "paolo";
        int tickeid = 1;

        when(ticketService.itsTicketOf(username,tickeid)).thenReturn(true);
        assertTrue(luc.checkProperty(username, tickeid));

    }

    @Test
    public void noSenseTimeTest() throws Exception {
        LineUpComponent luc = new LineUpComponent(true,responseWrapper, shopService, ticketService, userService);

        Date date = new Date(3600001L); // tempo di permanenza poco piu di un'ora
        when(enqueueData.getPermanence()).thenReturn(date);
        assertEquals("permanence time too long", luc.noSenseTime(enqueueData));

        Date newMinute = new Date(5999L*15);// tempo di permanenca poco meno di un'ora
        when(enqueueData.getPermanence()).thenReturn(newMinute);
        assertEquals("permanence time too little", luc.noSenseTime(enqueueData));

        newMinute = new Date(60001L*15);// tempo di permanenca poco meno di un'ora
        Date newMinute2 = new Date(3600001L*5);// tempo per arrivare al negozio ampiamente piu di un'ora
        when(enqueueData.getTimeToGetToTheShop()).thenReturn(newMinute2);
        when(enqueueData.getPermanence()).thenReturn(newMinute);
        assertEquals("time to get to the shop  too long", luc.noSenseTime(enqueueData));

        newMinute = new Date(5999L);// tempo per arrivare al negozio ampiamente piu di un'ora
        when(enqueueData.getTimeToGetToTheShop()).thenReturn(newMinute);
        assertEquals("time to get to the shop  too little", luc.noSenseTime(enqueueData));

        newMinute2 = new Date(60001L*5);
        date = new Date(1600001L);
        when(enqueueData.getPermanence()).thenReturn(date);
        when(enqueueData.getTimeToGetToTheShop()).thenReturn(newMinute2);
        assertEquals("OK", luc.noSenseTime(enqueueData));

    }

    @Test
    public void corruptedDataTest() throws Exception {
        LineUpComponent luc = new LineUpComponent(true,responseWrapper, shopService, ticketService, userService);

        assertTrue(luc.corruptedData(null));

        when(enqueueData.getShopid()).thenReturn(0);
        when(shopService.find(0)).thenReturn(null);
        assertTrue(luc.corruptedData(enqueueData));


    }

    @Test
    public void dequeueTest(){
        LineUpComponent luc = new LineUpComponent(true,responseWrapper, shopService, ticketService, userService);

    }

    @Test
    public void enqueueTest(){
        LineUpComponent luc = new LineUpComponent(true,responseWrapper, shopService, ticketService, userService);

    }

}
