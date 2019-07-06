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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphCoverageCase2 {
    
    private static AgencyManagerRemote sAgencyManager;
    private static TUserDTO userDTO;
    
    //just an user to buy seats.. on the final of each test he is removed from db
    private static TUserDTO auxUser;
    //just a purchase to buy seats.. on the final of each test it is removed from db
    private static TPurchaseDTO purchaseAuxDTO;

    private static TTripDTO tripDTO;    
    private static TPlaceDTO fromPlace;
    private static TPlaceDTO toPlace;
    private static TAirlineDTO airlineDTO; 
    private static TPlaneDTO planeDTO;
    private static TPurchaseDTO purchaseDTO;

    public GraphCoverageCase2() {
    }
    
    @BeforeClass
    public static void setUpClass() throws NoPermissionException {
        sAgencyManager= Operations.initRemoteReferences(sAgencyManager);
        
        Operations.signinAsAdmin(sAgencyManager);

        fromPlace = Operations.createFromPlace(sAgencyManager);
        toPlace = Operations.createToPlace(sAgencyManager);
        
        airlineDTO = Operations.createAirline(sAgencyManager);
        planeDTO = Operations.createPlane(sAgencyManager);
        
        tripDTO = Operations.createTrip(sAgencyManager, airlineDTO, fromPlace, toPlace, planeDTO, 50.0, 100);
        
        //create an user
        userDTO = Operations.createTestUser(sAgencyManager);
        //get the user
        userDTO=Operations.getUser(sAgencyManager, userDTO);
        
        //accept the user
        Operations.signinAsAdmin(sAgencyManager);
        sAgencyManager.acceptUser(userDTO);
        
        //signin again as the accepted user
        Operations.signinAsTestUser(sAgencyManager, userDTO);
    }
    
    @AfterClass
    public static void tearDownClass() throws NoPermissionException {
        clearAllData();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void T3() throws NoPermissionException {
    
        Operations.signinAsTestUser(sAgencyManager);
        
        assertTrue(sAgencyManager.getActualPurchase() == null);
    }
    
    
    @Test
    public void T5() throws NoPermissionException{
        
        Operations.signinAsTestUser(sAgencyManager);
        
        sAgencyManager.depositToAccount(1000);
        
        //since the plane has limitation 10 we buy 9 seats and finish the purchase... 
        purchaseDTO = Operations.buySeatsToTrip(sAgencyManager, tripDTO, 1);

        TPurchaseDTO actualPurchaseTmp = sAgencyManager.getActualPurchase();
        
        boolean result = purchaseDTO.getId().equals(actualPurchaseTmp.getId());
        
        clearAllTmpData();
        
        assertTrue(result);
    }
    
    @Test
    public void T6() throws NoPermissionException{
        
        //signin as normal user
        Operations.signinAsTestUser(sAgencyManager);
        
        sAgencyManager.depositToAccount(1000);
        
        //since the plane has limitation 10 we buy 1 seats and finish the purchase... 
        purchaseDTO = Operations.buySeatsToTrip(sAgencyManager, tripDTO, 1);

        //create the aux user
        auxUser = Operations.createUser(sAgencyManager, "auxuser", "123", true);
        //get the user
        auxUser=Operations.getUser(sAgencyManager, auxUser);
        //accept the user
        Operations.signinAsAdmin(sAgencyManager);
        sAgencyManager.acceptUser(auxUser);
        //signin again as aux user
        Operations.signinAsTestUser(sAgencyManager, auxUser);
        sAgencyManager.depositToAccount(1000);
        //buy seats
        purchaseAuxDTO = Operations.buyAndFinishPurchaseCase2(sAgencyManager, tripDTO, 9);
        
        //now We signin as a normal user
        Operations.signinAsTestUser(sAgencyManager);
        
        List<TSeatDTO> auctionedSeats = sAgencyManager.findAllAuctionedSeats();
        TSeatDTO auctionedSeat = auctionedSeats.get(0);
        auctionedSeat.setPrice(20.0);
        
        sAgencyManager.bidAuctionedSeat(auctionedSeat);
        
        
        TPurchaseDTO actualPurchaseTmp = sAgencyManager.getActualPurchase();
        
        boolean result = purchaseDTO.getId().equals(actualPurchaseTmp.getId());
        
        clearAllTmpData();
        
        assertTrue(result);
        
    }
    /*
    @Test
    public void T7() throws NoPermissionException {
    
        clearAllTmpData();
        
        Operations.signinAsTestUser(sAgencyManager);
        sAgencyManager.depositToAccount(1000);
        
        purchaseDTO = Operations.buySeatsToTrip(sAgencyManager, tripDTO, 9);
        
        //the DTO is not updated but the db it is
        sAgencyManager.removeActualPurchase(purchaseDTO);
        
        assertTrue(sAgencyManager.getActualPurchase() == null);
    }
    */
    
    @Test
    public void T8() throws NoPermissionException{

        clearAllTmpData();

        //create the aux user
        auxUser = Operations.createUser(sAgencyManager, "auxuser", "123", true);
        //get the user
        auxUser=Operations.getUser(sAgencyManager, auxUser);
        //accept the user
        Operations.signinAsAdmin(sAgencyManager);
        sAgencyManager.acceptUser(auxUser);
        //signin again as aux user
        Operations.signinAsTestUser(sAgencyManager, auxUser);
        sAgencyManager.depositToAccount(1000);
        //buy seats
        purchaseAuxDTO = Operations.buyAndFinishPurchaseCase2(sAgencyManager, tripDTO, 9);
        
        //signin as normal user
        Operations.signinAsTestUser(sAgencyManager);
        
        sAgencyManager.depositToAccount(1000);
        
        List<TSeatDTO> auctionedSeats = sAgencyManager.findAllAuctionedSeats();
        TSeatDTO auctionedSeat = auctionedSeats.get(0);
        auctionedSeat.setPrice(20.0);
        
        sAgencyManager.bidAuctionedSeat(auctionedSeat);
        
        
        TPurchaseDTO actualPurchaseTmp = sAgencyManager.getActualPurchase();
        
        boolean result = actualPurchaseTmp == null;
        
        clearAllTmpData();
        
        assertTrue(result);
        
    }
    
    
    private static void clearAllTmpData() throws NoPermissionException{
        
        
        if(purchaseDTO != null)
        {
            Operations.signinAsTestUser(sAgencyManager);
            sAgencyManager.removeSeatsOfActualPurchase(purchaseDTO, tripDTO);
            sAgencyManager.removeActualPurchase(purchaseDTO);
            purchaseDTO = null;
        }
        
        if(purchaseAuxDTO != null)
        {
            Operations.signinAsTestUser(sAgencyManager,auxUser);

            sAgencyManager.removeSeatsOfActualPurchase(purchaseAuxDTO, tripDTO);
            sAgencyManager.removeActualPurchase(purchaseAuxDTO);
            purchaseAuxDTO = null;
        }
        
        if(auxUser != null){
            sAgencyManager.deleteUser(auxUser);
        }
    }
    
    static void clearAllData() throws NoPermissionException{
        
        Operations.signinAsAdmin(sAgencyManager);
        
        Operations.deleteTrip(sAgencyManager, tripDTO);
        Operations.deleteAirline(sAgencyManager, airlineDTO);
        Operations.deletePlane(sAgencyManager, planeDTO);
        Operations.deleteFromPlace(sAgencyManager, fromPlace);
        Operations.deleteToPlace(sAgencyManager, toPlace);
        
        tripDTO = null;
    }
}
