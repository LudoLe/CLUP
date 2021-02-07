package polimi.it.SSB;

import Responses.ShopResponse;
import Responses.StringResponse;
import polimi.it.AMB.AAVEngine;
import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.ShopShift;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.ShopService;
import polimi.it.DL.services.ShopShiftService;
import polimi.it.DL.services.UserService;
import prototypes.ShopProto;
import prototypes.ShopShiftProto;
import responseWrapper.ResponseWrapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Stateless(name="ManageShopComponent" )
public class ManageShopComponent{

    public ManageShopComponent(){}

    @EJB(name= "ResponseWrapper")
    ResponseWrapper responseWrapper;

    @EJB(name= "AAVEngine")
    AAVEngine aavEngine;

    @EJB(name= "services/ShopShiftService")
    ShopShiftService shopShiftService;

    @EJB(name= "services/ShopService")
    ShopService shopService;

    @EJB(name= "services/UserService")
    UserService userService;



    public boolean checkIfCorruptedData(ShopProto shopProto){
        if(shopProto.getShopCapacity()<=0)return true;
        if(shopProto.getMaxEnteringClientInATimeslot()<=0)return true;
        if(shopProto.getTimeslotMinutesDuration()<=0)return true;

        boolean bol;
       try {
             bol = shopService.existsWithThatNameAndPosition(shopProto.getName(), shopProto.getPosition());
             System.out.println("corrupte existing already");

           return bol;
       }catch (Exception e ){
           e.printStackTrace();
       }
       return false;
    }

    public boolean checkIfCorruptedDataShift(ShopShiftProto shift){
       if(shift.getClosingTime().before(shift.getOpeningTime())) return true;
       if(shift.getDay()<0 || shift.getDay()>7 ) return true;
        try {
            Shop shop = shopService.find(shift.getShopid());
            if(shop != null) {
                System.out.println("fine we found the shop to update yey");
                return false;
            }else return true;

        }catch (Exception e ){
            e.printStackTrace();
            return false;
        }
    }



    public Response registerNewShop(ShopProto shop, String username) {
        User manager = null;
        try {
            manager = userService.findByUsername(username);
        } catch (Exception e) {
            userService.invalidateSessionToken(username);
            e.printStackTrace();
        }
        try {
            Shop newShop = shopService.createShop(shop.getDescription(), shop.getShopCapacity(), shop.getName(), manager ,shop.getImage(), shop.getMaxEnteringClientInATimeslot(), shop.getPosition(), shop.getTimeslotMinutesDuration());
            String newToken = userService.newSessionToken(username);
            System.out.println(newToken);
            return responseWrapper.generateResponse(newToken, Response.Status.OK, newShop);

        }catch (Exception e){
            userService.invalidateSessionToken(username);
            return responseWrapper.generateResponse(null, Response.Status.INTERNAL_SERVER_ERROR, "Something wernt wrong while registering shifts!");
        }
    }

    public Response registerNewShiftShop(List<ShopShiftProto> shifts, String username) throws Exception {

        List<ShopShift> shiftsnew=new ArrayList<>();

        for (ShopShiftProto shift: shifts
             ) {
            try {
                if(aavEngine.isAuthorizedToAccessShop(shift.getShopid(), username)&&(!checkIfCorruptedDataShift(shift))) {
                    shiftsnew.add(shopShiftService.create(shift.getShopid(), shift.getClosingTime(), shift.getOpeningTime(), shift.getDay()));

                }else{
                    System.out.println("not authorized to add this shift, you are being logged out");
                    userService.invalidateSessionToken(username);
                    return responseWrapper.generateResponse(null,Response.Status.INTERNAL_SERVER_ERROR, "Something wernt wrong while registering shifts!");
                }
            }catch (Exception e){
                System.out.println("not authorized to add this shift, you are being logged out");
                userService.invalidateSessionToken(username);
                return responseWrapper.generateResponse(null,Response.Status.INTERNAL_SERVER_ERROR, "Something wernt wrong while registering shifts!");
            }
        }
        return responseWrapper.generateResponse(userService.newSessionToken(username),Response.Status.OK, shiftsnew);
    }

}
