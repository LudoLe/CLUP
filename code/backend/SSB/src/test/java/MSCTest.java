import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import polimi.it.AMB.AAVEngine;
import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.ShopShift;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.ShopService;
import polimi.it.DL.services.ShopShiftService;
import polimi.it.DL.services.UserService;
import polimi.it.SSB.ManageShopComponent;
import prototypes.ShopProto;
import prototypes.ShopShiftProto;
import responseWrapper.ResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class MSCTest{

    @Mock
    ShopProto shopProto, shopProto2;
    @Mock
    ShopShift shopShift;
    @Mock
    Response response;
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

    @Mock
    User manager;

    //@Mock
    List<ShopShiftProto> shopShiftProtos;

    @Mock
    List<ShopShift> newShift;

    @Mock
    HttpServletRequest request;

    @Test
    public void MSCTest(){

      // checkIfCorruptedDataShiftTest(msc);

    }


    @Test
    public void registerNewShopTest() throws Exception {
      
        ManageShopComponent msc = new ManageShopComponent(responseWrapper, aavEngine, shopShiftService, shopService, userService);

        String username = "paolo";
        String username2 = "federino";
        String username3 = "federinounexisting";


        when(responseWrapper.generateResponse(Response.Status.INTERNAL_SERVER_ERROR, "Something went wrong while registering shop")).thenReturn(response);
        doThrow(new Exception()).when(userService).findByUsername(username);
        assertEquals(response, msc.registerNewShop(shopProto2, username, request));

        String string = "string";
        int in = 3;

        when(userService.findByUsername(username2)).thenReturn(manager);
        when(shopProto2.getDescription()).thenReturn(string);
        when(shopProto2.getShopCapacity()).thenReturn(in);
        when(shopProto2.getName()).thenReturn(string);
        when(shopProto2.getImage()).thenReturn(string);
        when(shopProto2.getMaxEnteringClientInATimeslot()).thenReturn(in);
        when(shopProto2.getPosition()).thenReturn(string);
        when(shopProto2.getTimeslotMinutesDuration()).thenReturn(in);
        when(userService.newSessionToken(username2)).thenReturn(string);
        when(shopService.createShop(string, in, string, manager, string, in, string, in)).thenReturn(shop);
        when(responseWrapper.generateResponse(Response.Status.OK, shop)).thenReturn(response);
        assertEquals(response, msc.registerNewShop(shopProto2, username2, request));
        when(userService.findByUsername(username3)).thenReturn(manager);
        when(responseWrapper.generateResponse(Response.Status.INTERNAL_SERVER_ERROR, "Something went wrong while registering shop")).thenReturn(response);
        doThrow(new Exception()).when(shopService).createShop(string, in, string, manager, string, in, string, in);
        assertEquals(response, msc.registerNewShop(shopProto2, username3, request));
    }

    @Test
    public void registerNewShiftShopTest() throws Exception {


        Date date;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 8);
        cal.set(Calendar.DATE, 24);
        cal.set(Calendar.YEAR, 2013);
        cal.set(Calendar.HOUR,13);
        cal.set(Calendar.MINUTE,45);
        cal.set(Calendar.SECOND,52);
        date = cal.getTime();

        ManageShopComponent msc = new ManageShopComponent(responseWrapper, aavEngine, shopShiftService, shopService, userService);
        int id = 1;

        String username ="paolo";
        List<ShopShiftProto> shopShiftProtos = new ArrayList<>();
        List < ShopShift > shiftsnew = new ArrayList < > ();



        when(shopShiftProto.getShopid()).thenReturn(1);
        when(shopShiftProto.getClosingTime()).thenReturn(date);
        when(shopShiftProto.getOpeningTime()).thenReturn(date);
        when(shopShiftProto.getDay()).thenReturn(1);

        shopShiftProtos.add(shopShiftProto);

        when(shopService.find(id)).thenReturn(null);
        when(aavEngine.isAuthorizedToAccessShop(id, username)).thenReturn(true);
        when(responseWrapper.generateResponse(Response.Status.UNAUTHORIZED, "unauthorize, you are being logged out")).thenReturn(response);

        assertEquals(response, msc.registerNewShiftShop(shopShiftProtos, username));

        when(shopService.find(id)).thenReturn(shop);
        when(shopShiftService.create(shopShiftProto.getShopid(), shopShiftProto.getClosingTime(), shopShiftProto.getOpeningTime(), shopShiftProto.getDay())).thenReturn(shopShift);
        shiftsnew.add(shopShift);
        when(responseWrapper.generateResponse(Response.Status.OK, shiftsnew)).thenReturn(response);
        assertEquals(response, msc.registerNewShiftShop(shopShiftProtos, username));


         doThrow(new Exception()).when(shopShiftService).create(id, date, date, id);
         when(responseWrapper.generateResponse(Response.Status.INTERNAL_SERVER_ERROR, "Something went wrong while registering shifts!")).thenReturn(response);
         assertEquals(response, msc.registerNewShiftShop(shopShiftProtos, username));
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

            when(shopShiftProto.getShopid()).thenReturn(3);
            doThrow(new Exception()).when(shopService).find(3);
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

       /* doThrow(new Exception()).when(shopService).existsWithThatNameAndPosition(anyString(), anyString());
        assertFalse(msc.checkIfCorruptedData(shopProto));*/
    }
}
