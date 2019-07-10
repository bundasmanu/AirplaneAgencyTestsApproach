/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuiteClass;

import Operations.Operations;
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

public class GraphCoverageSuiteCase2 {
    
    //private static AgencyManagerRemote sAgencyManager;
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

    public GraphCoverageSuiteCase2() {
        
    }
    
    @BeforeClass
    public static void setUpClass() throws NoPermissionException {
        //sAgencyManager= Operations.initRemoteReferences(Operations.getAgencyRemote());
        
        Operations.signinAsAdmin(Operations.getAgencyRemote());

        fromPlace = Operations.createFromPlace(Operations.getAgencyRemote());
        toPlace = Operations.createToPlace(Operations.getAgencyRemote());
        
        airlineDTO = Operations.createAirline(Operations.getAgencyRemote());
        planeDTO = Operations.createPlane(Operations.getAgencyRemote());
        
        tripDTO = Operations.createTrip(Operations.getAgencyRemote(), airlineDTO, fromPlace, toPlace, planeDTO, 50.0, 100);
        
        //create an user
        userDTO = Operations.createTestUser(Operations.getAgencyRemote());
        //get the user
        userDTO=Operations.getUser(Operations.getAgencyRemote(), userDTO);
        
        //accept the user
        Operations.signinAsAdmin(Operations.getAgencyRemote());
        Operations.getAgencyRemote().acceptUser(userDTO);
        
        //signin again as the accepted user
        Operations.signinAsTestUser(Operations.getAgencyRemote(), userDTO);
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
    
        Operations.signinAsTestUser(Operations.getAgencyRemote());
        
        assertTrue(Operations.getAgencyRemote().getActualPurchase() == null);
    }
    
    
    @Test
    public void T5() throws NoPermissionException{
        
        Operations.signinAsTestUser(Operations.getAgencyRemote());
        
        Operations.getAgencyRemote().depositToAccount(1000);
        
        //since the plane has limitation 10 we buy 9 seats and finish the purchase... 
        purchaseDTO = Operations.buySeatsToTrip(Operations.getAgencyRemote(), tripDTO, 1);

        TPurchaseDTO actualPurchaseTmp = Operations.getAgencyRemote().getActualPurchase();
        
        boolean result = purchaseDTO.getId().equals(actualPurchaseTmp.getId());
        
        clearAllTmpData();
        
        assertTrue(result);
    }
    
    @Test
    public void T6() throws NoPermissionException{
        
        //signin as normal user
        Operations.signinAsTestUser(Operations.getAgencyRemote());
        
        Operations.getAgencyRemote().depositToAccount(1000);
        
        //since the plane has limitation 10 we buy 1 seats and finish the purchase... 
        purchaseDTO = Operations.buySeatsToTrip(Operations.getAgencyRemote(), tripDTO, 1);

        //create the aux user
        auxUser = Operations.createUser(Operations.getAgencyRemote(), "auxuser", "123", true);
        //get the user
        auxUser=Operations.getUser(Operations.getAgencyRemote(), auxUser);
        //accept the user
        Operations.signinAsAdmin(Operations.getAgencyRemote());
        Operations.getAgencyRemote().acceptUser(auxUser);
        //signin again as aux user
        Operations.signinAsTestUser(Operations.getAgencyRemote(), auxUser);
        Operations.getAgencyRemote().depositToAccount(1000);
        //buy seats
        purchaseAuxDTO = Operations.buyAndFinishPurchaseCase2(Operations.getAgencyRemote(), tripDTO, 9);
        
        //now We signin as a normal user
        Operations.signinAsTestUser(Operations.getAgencyRemote());
        
        List<TSeatDTO> auctionedSeats = Operations.getAgencyRemote().findAllAuctionedSeats();
        TSeatDTO auctionedSeat = auctionedSeats.get(0);
        auctionedSeat.setPrice(20.0);
        
        Operations.getAgencyRemote().bidAuctionedSeat(auctionedSeat);
        
        
        TPurchaseDTO actualPurchaseTmp = Operations.getAgencyRemote().getActualPurchase();
        
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
        auxUser = Operations.createUser(Operations.getAgencyRemote(), "auxuser", "123", true);
        //get the user
        auxUser=Operations.getUser(Operations.getAgencyRemote(), auxUser);
        //accept the user
        Operations.signinAsAdmin(Operations.getAgencyRemote());
        Operations.getAgencyRemote().acceptUser(auxUser);
        //signin again as aux user
        Operations.signinAsTestUser(Operations.getAgencyRemote(), auxUser);
        Operations.getAgencyRemote().depositToAccount(1000);
        //buy seats
        purchaseAuxDTO = Operations.buyAndFinishPurchaseCase2(Operations.getAgencyRemote(), tripDTO, 9);
        
        //signin as normal user
        Operations.signinAsTestUser(Operations.getAgencyRemote());
        
        Operations.getAgencyRemote().depositToAccount(1000);
        
        List<TSeatDTO> auctionedSeats = Operations.getAgencyRemote().findAllAuctionedSeats();
        TSeatDTO auctionedSeat = auctionedSeats.get(0);
        auctionedSeat.setPrice(20.0);
        
        Operations.getAgencyRemote().bidAuctionedSeat(auctionedSeat);
        
        
        TPurchaseDTO actualPurchaseTmp = Operations.getAgencyRemote().getActualPurchase();
        
        boolean result = actualPurchaseTmp == null;
        
        clearAllTmpData();
        
        assertTrue(result);
        
    }
    
    
    private static void clearAllTmpData() throws NoPermissionException{
        
        
        if(purchaseDTO != null)
        {
            Operations.signinAsTestUser(Operations.getAgencyRemote());
            Operations.getAgencyRemote().removeSeatsOfActualPurchase(purchaseDTO, tripDTO);
            Operations.getAgencyRemote().removeActualPurchase(purchaseDTO);
            purchaseDTO = null;
        }
        
        if(purchaseAuxDTO != null)
        {
            Operations.signinAsTestUser(Operations.getAgencyRemote(),auxUser);

            Operations.getAgencyRemote().removeSeatsOfActualPurchase(purchaseAuxDTO, tripDTO);
            Operations.getAgencyRemote().removeActualPurchase(purchaseAuxDTO);
            purchaseAuxDTO = null;
        }
        
        if(auxUser != null){
            Operations.getAgencyRemote().deleteUser(auxUser);
        }
    }
    
    static void clearAllData() throws NoPermissionException{
        
        Operations.signinAsAdmin(Operations.getAgencyRemote());
        
        Operations.deleteTrip(Operations.getAgencyRemote(), tripDTO);
        Operations.deleteAirline(Operations.getAgencyRemote(), airlineDTO);
        Operations.deletePlane(Operations.getAgencyRemote(), planeDTO);
        Operations.deleteFromPlace(Operations.getAgencyRemote(), fromPlace);
        Operations.deleteToPlace(Operations.getAgencyRemote(), toPlace);
        
        tripDTO = null;
    }
}
