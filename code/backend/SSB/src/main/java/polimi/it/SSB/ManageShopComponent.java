package polimi.it.SSB;

import Responses.StringResponse;
import polimi.it.DL.services.ShopService;
import polimi.it.DL.services.ShopShiftService;
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

    public Response registerNewShop(ShopProto shop){
        String manager = shop.getManager();
        try {
            System.out.println("before creating a shop");

            shopService.createShop(shop.getDescription(), shop.getShopCapacity(), shop.getName(), manager ,shop.getImage(), shop.getMaxEnteringClientInATimeslot(), shop.getPosition(), shop.getTimeslotMinutesDuration());

            System.out.println("after creating a shop");

            return responseWrapper.generateResponse(Response.Status.OK, new StringResponse("Successfully registered shop!"));
        }catch (Exception e){
            return responseWrapper.generateResponse(Response.Status.INTERNAL_SERVER_ERROR, new StringResponse("Something wernt wrong while registering shifts!"));

        }
    }

    public Response registerNewShiftShop(List<ShopShift> shifts) throws Exception {

        for (ShopShift shift: shifts
             ) {
            try {
                shopShiftService.create(shift.getShop(), shift.getClosingTime(), shift.getOpeningTime(), shift.getDay());
            }catch (Exception e){
                return responseWrapper.generateResponse(Response.Status.INTERNAL_SERVER_ERROR, new StringResponse("Something wernt wrong while registering shifts!"));

            }
        }
        return responseWrapper.generateResponse(Response.Status.OK, new StringResponse("Successfully updated shifts!"));
    }

}
