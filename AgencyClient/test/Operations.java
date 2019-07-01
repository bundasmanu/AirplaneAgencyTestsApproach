import java.util.ArrayList;
import java.util.List;
import logic.AgencyManagerRemote;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaceDTO;
import logic.TPlaneDTO;
import logic.TPurchaseDTO;
import logic.TSeatDTO;
import logic.TTripDTO;
import logic.TUserDTO;


public class Operations {
    public static void signinAsAdmin(AgencyManagerRemote sAgencyManager){
        sAgencyManager.logout();
        sAgencyManager.signIn(Config.ADMIN_USERNAME, Config.ADMIN_PASS);
    
    }
    
    public static void signinAsTestUser(AgencyManagerRemote sAgencyManager){
        sAgencyManager.logout();
        sAgencyManager.signIn(Config.TEST_USERNAME, Config.TEST_PASS);
    }
    
    
    public static TUserDTO createTestUser(AgencyManagerRemote sAgencyManager){
        TUserDTO userDTO = new TUserDTO();
        userDTO.setUsername(Config.TEST_USERNAME);
        userDTO.setPassword(Config.TEST_PASS);
        userDTO.setClientName("Client Name");
        userDTO.setUsertype(logic.Config.CLIENT);
        sAgencyManager.signUp(userDTO);
        return userDTO;
    }

    public static TPlaceDTO createFromPlace(AgencyManagerRemote sAgencyManager) {
        TPlaceDTO fromPlace = new TPlaceDTO();
        fromPlace.setAddress("Adress xtpo");
        fromPlace.setCity("Lisbon");
        fromPlace.setCountry("Portugal");
        return sAgencyManager.findAllPlaces().get(0);
    }

    public static TPlaceDTO createToPlace(AgencyManagerRemote sAgencyManager) {
        TPlaceDTO toPlace = new TPlaceDTO();
        toPlace.setAddress("Adress xtpo");
        toPlace.setCity("Porto");
        toPlace.setCountry("Portugal");
        return sAgencyManager.findAllPlaces().get(1);
    }

    public static TAirlineDTO createAirline(AgencyManagerRemote sAgencyManager) throws NoPermissionException {
        TAirlineDTO airlineDTO = new TAirlineDTO();
        airlineDTO.setAirlineName("AirlineName1");
        airlineDTO.setPhoneNumber("939898321");
        sAgencyManager.addAirline(airlineDTO);
        return sAgencyManager.findAllAirlines().get(0);
    }

    public static TPlaneDTO createPlane(AgencyManagerRemote sAgencyManager) throws NoPermissionException {
        TPlaneDTO planeDTO = new TPlaneDTO();
        planeDTO.setPlaneLimit(100);
        planeDTO.setPlaneName("Plane1");
        sAgencyManager.addPlane(planeDTO);
        return sAgencyManager.findAllPlanes().get(0);
    }

    public static TTripDTO createTrip(AgencyManagerRemote sAgencyManager, TAirlineDTO airlineDTO, TPlaceDTO fromPlace, TPlaceDTO toPlace, TPlaneDTO planeDTO, double balance, int datetrip) throws NoPermissionException {
        TTripDTO tripDTO = new TTripDTO();
        tripDTO.setAirlineDTO(airlineDTO);
        tripDTO.setFromPlaceDTO(fromPlace);
        tripDTO.setToPlaceDTO(toPlace);
        tripDTO.setPlaneDTO(planeDTO);
        tripDTO.setPrice(balance);
        tripDTO.setDatetrip(datetrip);
        sAgencyManager.addTrip(tripDTO);
        return sAgencyManager.findAllTrips().get(0);
    }

    public static TPurchaseDTO buyAndFinishPurchase(AgencyManagerRemote sAgencyManager, TTripDTO tripDTO) throws NoPermissionException {
        //buy seats
        List<TSeatDTO> seatDTOList = new ArrayList();
        seatDTOList.add(new TSeatDTO());
        sAgencyManager.buySeatsToTrip(tripDTO, seatDTOList);
        
        //finish the purchase
        TPurchaseDTO purchaseDTO = sAgencyManager.getActualPurchase();
        
        sAgencyManager.finishActualPurchase(purchaseDTO);
        
        return purchaseDTO;
    }

    
}
