import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
    
    public static void signinAsTestUser(AgencyManagerRemote sAgencyManager, TUserDTO userDTO){
        sAgencyManager.logout();
        sAgencyManager.signIn(userDTO.getUsername(), userDTO.getPassword());
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
    
    public static TUserDTO createUser(AgencyManagerRemote sAgencyManager, String username, String password, boolean isClient){
        TUserDTO userDTO = new TUserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        if(isClient)
        {
            userDTO.setClientName("Client Name");
            userDTO.setUsertype(logic.Config.CLIENT);
        }
        else
            userDTO.setUsertype(logic.Config.OPERATOR);

        sAgencyManager.signUp(userDTO);
        return userDTO;
    }
    
    public static boolean editTestUser(AgencyManagerRemote sAgencyManager, TUserDTO tu){
        return sAgencyManager.editUser(tu);
    }
    
    public static TUserDTO getUser(AgencyManagerRemote sAgencyManager, TUserDTO tu){
        return sAgencyManager.getTUserDTO(tu.getUsername());
    }
    
    public static TPlaceDTO createFromPlace(AgencyManagerRemote sAgencyManager) throws NoPermissionException {
        TPlaceDTO fromPlace = new TPlaceDTO();
        fromPlace.setAddress("Adress xtpo");
        fromPlace.setCity("Lisbon");
        fromPlace.setCountry("Portugal");
        sAgencyManager.addPlace(fromPlace);  
        return sAgencyManager.findAllPlaces().get(0);
    }

    public static TPlaceDTO createToPlace(AgencyManagerRemote sAgencyManager) throws NoPermissionException {
        TPlaceDTO toPlace = new TPlaceDTO();
        toPlace.setAddress("Adress xtpo");
        toPlace.setCity("Porto");
        toPlace.setCountry("Portugal");
        sAgencyManager.addPlace(toPlace);
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
        planeDTO.setPlaneLimit(10);
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
        boolean ret=sAgencyManager.addTrip(tripDTO);
        return sAgencyManager.findAllTrips().get(0);
    }
    
    public static void deleteFromPlace(AgencyManagerRemote sAgencyManager, TPlaceDTO fromPlace) throws NoPermissionException{
        
        sAgencyManager.removePlace(fromPlace);
        
    }
    
    public static void deleteToPlace(AgencyManagerRemote sAgencyManager, TPlaceDTO toPlace) throws NoPermissionException{
        
        sAgencyManager.removePlace(toPlace);
        
    }
    
    public static void deletePlane(AgencyManagerRemote sAgencyManager, TPlaneDTO plane) throws NoPermissionException{
        
        sAgencyManager.removePlane(plane);
        
    }
    
    public static void deleteTrip(AgencyManagerRemote sAgencyManager, TTripDTO trip) throws NoPermissionException{
        
        sAgencyManager.cancelTrip(trip);
        sAgencyManager.removeTrip(trip);
        
    }
    
    public static void deleteAirline(AgencyManagerRemote sAgencyManager, TAirlineDTO airline) throws NoPermissionException{
        
        sAgencyManager.removeAirline(airline);
        
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
    
    public static TPurchaseDTO buyAndFinishPurchaseCase2(AgencyManagerRemote sAgencyManager, TTripDTO tripDTO, int numberSeats) throws NoPermissionException {
        //buy seats
        try {
            List<TSeatDTO> seatDTOList = new ArrayList();

            for (int i = 0; i < numberSeats; i++) { 
                seatDTOList.add(new TSeatDTO());  
            }

            boolean retorno=sAgencyManager.buySeatsToTrip(tripDTO, seatDTOList);
            
            if(retorno==false){
                return null;
            }
            
            //finish the purchase
            TPurchaseDTO purchaseDTO = sAgencyManager.getActualPurchase();

            boolean ret=sAgencyManager.finishActualPurchase(purchaseDTO);
            
            if(ret==false && purchaseDTO!=null){
                sAgencyManager.removeSeatsOfActualPurchase(purchaseDTO, tripDTO);
                sAgencyManager.removeActualPurchase(purchaseDTO);
                return null;
            }
            purchaseDTO.setDone(ret);
            return purchaseDTO;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public static TPurchaseDTO buyAndFinishPurchaseCase3(AgencyManagerRemote sAgencyManager, TTripDTO tripDTO, int numberSeats) throws NoPermissionException {
        //buy seats
        try {
            List<TSeatDTO> seatDTOList = new ArrayList();

            for (int i = 0; i < numberSeats; i++) { 
                seatDTOList.add(new TSeatDTO());  
            }

            boolean retorno=sAgencyManager.buySeatsToTrip(tripDTO, seatDTOList);
            
            if(retorno==false){
                return null;
            }
            
            //finish the purchase
            TPurchaseDTO purchaseDTO = sAgencyManager.getActualPurchase();

            boolean ret=sAgencyManager.finishActualPurchase(purchaseDTO);
            
            return purchaseDTO;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public static TPurchaseDTO buySeatsToTrip(AgencyManagerRemote sAgencyManager, TTripDTO tripDTO, int numberSeats) throws NoPermissionException {
        //buy seats
        try {
            List<TSeatDTO> seatDTOList = new ArrayList();

            for (int i = 0; i < numberSeats; i++) { 
                seatDTOList.add(new TSeatDTO());  
            }

            boolean retorno=sAgencyManager.buySeatsToTrip(tripDTO, seatDTOList);
            
            if(retorno==false){
                return null;
            }
            
            //finish the purchase
            TPurchaseDTO purchaseDTO = sAgencyManager.getActualPurchase();
            
            return purchaseDTO;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    
    
    public static AgencyManagerRemote initRemoteReferences(AgencyManagerRemote sAgencyManager) {
        Properties prop = new Properties();

        prop.setProperty("java.naming.factory.initial",
                "com.sun.enterprise.naming.SerialInitContextFactory");

        prop.setProperty("java.naming.factory.url.pkgs",
                "com.sun.enterprise.naming");

        prop.setProperty("java.naming.factory.state",
                "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

        prop.setProperty("org.omg.CORBA.ORBInitialHost", "192.168.56.175");
        prop.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

        InitialContext ctx = null;
        try {
           ctx = new InitialContext(prop);
        
        } catch (NamingException e) {
           System.out.println(e.getMessage());
           System.exit(1);
        }

        String agencyManagerClassName = "java:global/Agency/Agency-ejb/AgencyManager!logic.AgencyManagerRemote";
        String guestAgencyManagerClassName = "java:global/Agency/Agency-ejb/GuestAgencyManager!logic.GuestAgencyManagerRemote";

        try {
           sAgencyManager = (AgencyManagerRemote) ctx.lookup(agencyManagerClassName);
           return sAgencyManager;
        
        } catch (NamingException e) {
           System.out.println(e.getMessage());
           e.printStackTrace();
           return null;
        }
    }

    
}
