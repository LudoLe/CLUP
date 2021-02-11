package polimi.it.SSB;

import org.apache.commons.lang3.RandomStringUtils;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Stateless(name = "ManageShopComponent")
public class ManageShopComponent {



    @EJB(name = "ResponseWrapper")
    ResponseWrapper responseWrapper;

    @EJB(name = "AAVEngine")
    AAVEngine aavEngine;

    @EJB(name = "services/ShopShiftService")
    ShopShiftService shopShiftService;

    @EJB(name = "services/ShopService")
    ShopService shopService;

    @EJB(name = "services/UserService")
    UserService userService;


    /**
     * this method checks whether the data inserted to register a new shop are consistent and
     * have sense
     * @param shopProto contains the data to be checked from the request
     * @return boolean
     * */
    public boolean checkIfCorruptedData(ShopProto shopProto) {
        if (shopProto.getShopCapacity() <= 0) return true;
        if (shopProto.getMaxEnteringClientInATimeslot() <= 0) return true;
        if (shopProto.getTimeslotMinutesDuration() <= 0) return true;
        boolean bol;
        try {
            bol = shopService.existsWithThatNameAndPosition(shopProto.getName(), shopProto.getPosition());
            return bol;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * this method checks whether the data inserted to register a new shift for a shop are consistent and
     * have sense
     * @param shift contains the data to be checked
     * @return boolean
     * */
    public boolean checkIfCorruptedDataShift(ShopShiftProto shift) {
        if (shift.getClosingTime().before(shift.getOpeningTime())) return true;
        if (shift.getDay() < 0 || shift.getDay() > 7) return true;
        try {
            Shop shop = shopService.find(shift.getShopid());
            return shop == null;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * this method create a new shop and match it to the manager who is creating it
     * @param shop the shop to be registered with all of the needed data
     * @param username to match the shop to a manager
     * @return http response
     * */
    public Response registerNewShop(ShopProto shop, String username, HttpServletRequest request) throws ServletException, IOException  {
        User manager;
        try {
            manager = userService.findByUsername(username);
        } catch (Exception e) {
            return responseWrapper.generateResponse(Response.Status.INTERNAL_SERVER_ERROR, "Something went wrong while registering shop");
        }
            Part part = request.getPart("image");
            if(part == null) {
                return responseWrapper.generateResponse(Response.Status.INTERNAL_SERVER_ERROR, "Something went wrong while registering shop");
            }
            // gets absolute path of the web application
            String applicationPath = request.getServletContext().getInitParameter("uploadsLocation");

            // constructs path of the directory to save uploaded file
            String uploadFilePath = applicationPath + request.getServletContext().getInitParameter("campaignImagesFolder");


            String fileName = part.getSubmittedFileName();
            String contentType = part.getContentType();
            String generatedString = RandomStringUtils.randomAlphabetic(80);
            String ext = getFileExtension(fileName);

            String savedFileName = generatedString + "." + ext;

            // allows only JPEG HEIC and PNG files to be uploaded
            if (!contentType.equalsIgnoreCase("image/jpeg") && !contentType.equalsIgnoreCase("image/png") && !contentType.equalsIgnoreCase("image/heic")) {
                return responseWrapper.generateResponse(Response.Status.BAD_REQUEST, "Invalid image format");
            }
            File fileToSave = new File(uploadFilePath, savedFileName);
            int i = 0;
            while(fileToSave.exists()) {
                savedFileName = generatedString + i + "." + ext;
                fileToSave = new File(uploadFilePath, savedFileName);
                i++;
            }
            try{
                Shop newShop = shopService.createShop(shop.getDescription(), shop.getShopCapacity(), shop.getName(), manager, savedFileName, shop.getMaxEnteringClientInATimeslot(), shop.getPosition(), shop.getTimeslotMinutesDuration());
                        try (InputStream input = part.getInputStream()) {
                            Files.copy(input, fileToSave.toPath());
                        }
                return responseWrapper.generateResponse(Response.Status.OK, newShop.getId());

            }
         catch (Exception e) {
            return responseWrapper.generateResponse(Response.Status.INTERNAL_SERVER_ERROR, "Something went wrong while registering shop");
        }
    }


    /**
     * this method create a new list of shifts to be associated to a shop
     * @param shifts the list of shifts provided by the user
     * @param username to check if the user is allowed to do such action
     * @return http response
     * */
    public Response registerNewShiftShop(List < ShopShiftProto > shifts, String username) throws Exception {

        List < ShopShift > shiftsnew = new ArrayList < > ();

        for (ShopShiftProto shift: shifts) {
            try {
                if (aavEngine.isAuthorizedToAccessShop(shift.getShopid(), username) && (!checkIfCorruptedDataShift(shift))) {
                    shiftsnew.add(shopShiftService.create(shift.getShopid(), shift.getClosingTime(), shift.getOpeningTime(), shift.getDay()));

                } else {
                    userService.invalidateSessionToken(username);
                    return responseWrapper.generateResponse(Response.Status.UNAUTHORIZED, "unauthorize, you are being logged out");
                }
            } catch (Exception e) {
                userService.invalidateSessionToken(username);
                return responseWrapper.generateResponse(Response.Status.INTERNAL_SERVER_ERROR, "Something went wrong while registering shifts!");
            }
        }
        return responseWrapper.generateResponse(Response.Status.OK, shiftsnew);
    }

    public static String getFileExtension(String fileName){
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }


    public ManageShopComponent(ResponseWrapper responseWrapper, AAVEngine aavEngine, ShopShiftService shopShiftService, ShopService shopService, UserService userService) {
        this.shopService = shopService;
        this.userService = userService;
        this.responseWrapper = responseWrapper;
        this.aavEngine = aavEngine;
        this.shopShiftService = shopShiftService;
    }

    public ManageShopComponent() {}



}