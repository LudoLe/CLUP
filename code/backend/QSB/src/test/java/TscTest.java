import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.Ticket;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.ShopService;
import polimi.it.DL.services.TicketService;
import polimi.it.DL.services.UserService;
import polimi.it.QSB.LineUpComponent;
import polimi.it.QSB.TicketSchedulerComponent;
import prototypes.EnqueueData;
import responseWrapper.ResponseWrapper;
import java.util.*;



@ExtendWith(MockitoExtension.class)
public class TscTest{


    @Test
    public void noQueueTest() throws Exception {
        List<Ticket> tickets;

        TicketSchedulerComponent tsc = new TicketSchedulerComponent(threeTicketsQueue());
        tickets = tsc.buildQueue();
        assertEquals(tickets.get(0).getId(), 1);
    }

    public List<Ticket> threeTicketsQueue(){

        ArrayList<Ticket> tickets = new ArrayList<>();
        Shop shop = new Shop();
        shop.setShopCapacity(3);
        shop.setTimeslotMinutesDuration(5);

        User paolo = new User();
        User laura = new User();
        User alfo = new User();

        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket3 = new Ticket();


        Date data = new Date(); // 1 gennaio 1970 all'1 am
        Date oneMinute = new Date(60000L);
        Date fortyFiveMinutes = new Date(60000L*45);
        Date fiveMinutes = new Date(60000L*5);
        Date minutes55 = new Date(data.getTime() + 60000L*55);


        ticket1.setShop(shop);
        ticket1.setStatus("valid");
        ticket1.setId(1);
        ticket1.setArrivalTime(data);
        ticket1.setExpectedDuration(fortyFiveMinutes);
        ticket1.setTimeToReachTheShop(oneMinute);
        ticket1.setEnterTime(data);
        ticket1.setExitTime(new Date(data.getTime() + 60000L*55));
        ticket1.setScheduledEnteringTime(data);
        ticket1.setUser(paolo);

        ticket2.setShop(shop);
        ticket2.setId(2);
        ticket2.setStatus("valid");
        ticket2.setArrivalTime(data);
        ticket2.setExpectedDuration(fortyFiveMinutes);
        ticket2.setTimeToReachTheShop(oneMinute);
        ticket2.setEnterTime(data);
        ticket2.setExitTime(new Date(data.getTime() + 60000L*55));
        ticket2.setScheduledEnteringTime(data);
        ticket2.setUser(laura);

        ticket3.setShop(shop);
        ticket3.setId(3);
        ticket3.setStatus("invalid");
        ticket3.setExpectedDuration(oneMinute);
        ticket3.setTimeToReachTheShop(fortyFiveMinutes);
        ticket3.setUser(alfo);

        tickets.add(ticket1);
        tickets.add(ticket2);
        tickets.add(ticket3);

        return tickets;

    }


}
