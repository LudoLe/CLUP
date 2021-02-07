package polimi.it.SSB;

import Responses.StringResponse;
import polimi.it.DL.entities.User;
import polimi.it.DL.services.ShopService;
import polimi.it.DL.services.ShopShiftService;
import polimi.it.DL.services.UserService;
import prototypes.ShopProto;
import prototypes.ShopShift;
import responseWrapper.ResponseWrapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless(name="ManageShopComponent" )
public class ManageShopComponent{

    public ManageShopComponent(){}

    @EJB(name= "ResponseWrapper")
    ResponseWrapper responseWrapper;

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

    public Response registerNewShop(ShopProto shop, String username) {
        User manager = null;
        try {
            manager = userService.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            shopService.createShop(shop.getDescription(), shop.getShopCapacity(), shop.getName(), manager ,shop.getImage(), shop.getMaxEnteringClientInATimeslot(), shop.getPosition(), shop.getTimeslotMinutesDuration());
            String newToken = userService.newSessionToken(username);
            return responseWrapper.generateResponse(newToken, Response.Status.OK, new StringResponse("Successfully registered shop!"));

        }catch (Exception e){
            return responseWrapper.generateResponse(null, Response.Status.INTERNAL_SERVER_ERROR, new StringResponse("Something wernt wrong while registering shifts!"));
        }
    }

    public Response registerNewShiftShop(List<ShopShift> shifts) throws Exception {

        for (ShopShift shift: shifts
             ) {
            try {
                shopShiftService.create(shift.getShop(), shift.getClosingTime(), shift.getOpeningTime(), shift.getDay());
            }catch (Exception e){
                return responseWrapper.generateResponse(null,Response.Status.INTERNAL_SERVER_ERROR, new StringResponse("Something wernt wrong while registering shifts!"));

            }
        }
        return responseWrapper.generateResponse(null,Response.Status.OK, new StringResponse("Successfully updated shifts!"));
    }

}
