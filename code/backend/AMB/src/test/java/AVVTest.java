import org.junit.jupiter.api.Test;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.ShopService;
import polimi.it.DL.services.TicketService;
import polimi.it.DL.services.UserService;
import testStuff.EntityManagerMock;

import javax.persistence.EntityManager;

public class AVVTest {

    UserService userService;
    ShopService shopService;
    TicketService ticketService;

    public static final int MAX_USERNAME_LENGTH = 40;
    public static final int MAX_EMAIL_LENGTH = 100;
    public static final int MAX_PWD_LENGTH = 100;
    EntityManager em;


    public void initializeDependencies() {
        em = new EntityManagerMock();
        userService = new UserService(em, true);
        shopService = new ShopService(em, true);
        ticketService = new TicketService(em, true);
    }

    @Test
    void checkRegistrationTest(){
        iniziatliazeDependencies();
    }


    public void scenario(){

        User user1 = new User();
        user1.setId(5);


    }














}
