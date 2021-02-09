import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import polimi.it.AMB.AAVEngine;
import polimi.it.AMB.AccountManagerComponent;
import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.ShopService;
import polimi.it.DL.services.ShopShiftService;
import polimi.it.DL.services.UserService;
import polimi.it.SSB.ManageShopComponent;
import prototypes.Credentials;
import prototypes.RegistrationCredentials;
import prototypes.ShopProto;
import prototypes.ShopShiftProto;
import responseWrapper.ResponseWrapper;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@ExtendWith(MockitoExtension.class)
public class MSCTest{

    @Mock
    ShopProto shopProto;
    @Mock
    ShopService shopService;
    @Mock
    UserService userService;
    @Mock
    AAVEngine aavEngine;
    @Mock
    ShopShiftService shopShiftService;
    @Mock
    ResponseWrapper responseWrapper;
    @Mock
    ShopShiftProto shopShiftProto;
    @Mock
    Shop shop;

    @Test
    public void MSCTest(){

      // checkIfCorruptedDataShiftTest(msc);

    }

    @Test
    public void checkIfCorruptedDataShiftTest() throws Exception {
        ManageShopComponent msc = new ManageShopComponent(responseWrapper, aavEngine, shopShiftService, shopService, userService);

        Date date;
        Date date2;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 8);
        cal.set(Calendar.DATE, 24);
        cal.set(Calendar.YEAR, 2013);
        cal.set(Calendar.HOUR,13);
        cal.set(Calendar.MINUTE,45);
        cal.set(Calendar.SECOND,52);
        date = cal.getTime();

        cal.set(Calendar.MONTH, 8);
        cal.set(Calendar.DATE, 24);
        cal.set(Calendar.YEAR, 2013);
        cal.set(Calendar.HOUR,12);
        cal.set(Calendar.MINUTE,45);
        cal.set(Calendar.SECOND,52);
        date2 = cal.getTime();

        when(shopShiftProto.getOpeningTime()).thenReturn(date);
        when(shopShiftProto.getClosingTime()).thenReturn(date2);
        assertTrue(msc.checkIfCorruptedDataShift(shopShiftProto));

        cal.set(Calendar.MONTH, 8);
        cal.set(Calendar.DATE, 24);
        cal.set(Calendar.YEAR, 2013);
        cal.set(Calendar.HOUR,15);
        cal.set(Calendar.MINUTE,45);
        cal.set(Calendar.SECOND,52);
        date2 = cal.getTime();

        when(shopShiftProto.getClosingTime()).thenReturn(date2);
        when(shopShiftProto.getDay()).thenReturn(0);
        assertTrue(msc.checkIfCorruptedDataShift(shopShiftProto));

        int shopid = 0;
        when(shopShiftProto.getDay()).thenReturn(3);
        when(shopShiftProto.getShopid()).thenReturn(shopid);
        when(shopService.find(shopid)).thenReturn(null);
        assertTrue(msc.checkIfCorruptedDataShift(shopShiftProto));

        when(shopService.find(shopid)).thenReturn(shop);
        assertFalse(msc.checkIfCorruptedDataShift(shopShiftProto));











    }

    @Test
    public void checkIfCorruptedDataTest(){
        ManageShopComponent msc = new ManageShopComponent(responseWrapper, aavEngine, shopShiftService, shopService, userService);

        String name = "name";
        String position = "position";

        when(shopProto.getShopCapacity()).thenReturn(-5);
        assertTrue(msc.checkIfCorruptedData(shopProto));

        when(shopProto.getShopCapacity()).thenReturn(5);
        when(shopProto.getMaxEnteringClientInATimeslot()).thenReturn(-5);
        assertTrue(msc.checkIfCorruptedData(shopProto));

        when(shopProto.getMaxEnteringClientInATimeslot()).thenReturn(5);
        when(shopProto.getTimeslotMinutesDuration()).thenReturn(-5);
        assertTrue(msc.checkIfCorruptedData(shopProto));

        when(shopProto.getTimeslotMinutesDuration()).thenReturn(5);
        when(shopProto.getName()).thenReturn(name);
        when(shopProto.getPosition()).thenReturn(position);
        when(shopService.existsWithThatNameAndPosition(shopProto.getName(), shopProto.getPosition())).thenReturn(true);
        assertTrue(msc.checkIfCorruptedData(shopProto));

    }
}
