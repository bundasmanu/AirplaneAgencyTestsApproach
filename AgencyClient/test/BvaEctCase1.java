/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import static junit.framework.Assert.assertEquals;
import logic.AgencyManagerRemote;
import logic.GuestAgencyManagerRemote;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaceDTO;
import logic.TPlaneDTO;
import logic.TPurchaseDTO;
import logic.TSeatDTO;
import logic.TTripDTO;
import logic.TTripFeedbackDTO;
import logic.TUserDTO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class BvaEctCase1{

    private static AgencyManagerRemote sAgencyManager;

    static TTripFeedbackDTO tripFeedbackDTO;
    static TTripDTO tripDTO;
    FeedbackResult res;
    
    @BeforeClass
    public static void beforeTests() throws NoPermissionException, NamingException {
        sAgencyManager= Operations.initRemoteReferences(sAgencyManager);
        
        //create an user
        TUserDTO userDTO = Operations.createTestUser(sAgencyManager);
        
        //accept the user
        Operations.signinAsAdmin(sAgencyManager);
        sAgencyManager.acceptUser(userDTO);
        
        TPlaceDTO fromPlace = Operations.createFromPlace(sAgencyManager);
        TPlaceDTO toPlace = Operations.createToPlace(sAgencyManager);
        
        TAirlineDTO airlineDTO = Operations.createAirline(sAgencyManager);
        TPlaneDTO planeDTO = Operations.createPlane(sAgencyManager);
        
        tripDTO = Operations.createTrip(sAgencyManager, airlineDTO, fromPlace, toPlace, planeDTO, 50.0, 100);
        
        
        //login from the normal user
        Operations.signinAsTestUser(sAgencyManager);
        
        //deposit money
        sAgencyManager.depositToAccount(1000);
        
        TPurchaseDTO purchaseDTO = Operations.buyAndFinishPurchase(sAgencyManager, tripDTO);
        
        //TODO: dev:
        //  -loginAsAdmin
        //  -loginAsOperator
        
    }
            
    public BvaEctCase1(int feedbackScore, boolean done, FeedbackResult res) {
        
        //need to create a trip feedback
        this.tripFeedbackDTO = new TTripFeedbackDTO();
        this.tripFeedbackDTO.setScore(feedbackScore);
        
        this.tripDTO.setDone(done);
        this.res = res;
    }
    
    @Parameterized.Parameters
    public static Collection valuesToTest() {
        return Arrays.asList(new Object[][] {
            {4, true, FeedbackResult.ValidFeedback},
            {6, false, FeedbackResult.InvalidFeedback}
        });
    }


    @Test
    public void testSeveral() throws NoPermissionException {
        
        Operations.signinAsAdmin(sAgencyManager);
        
        System.out.println("Testing: TripFeedback score: "+tripFeedbackDTO.getScore()+", TripDTO done: "+tripDTO.getDone()+" -> "+ res);
       
        sAgencyManager.editTrip(tripDTO);
        
        Operations.signinAsTestUser(sAgencyManager);
        
        boolean booleanResult = sAgencyManager.addFeedbackToTrip(tripDTO, tripFeedbackDTO);
        
        FeedbackResult resTmp = (booleanResult == true? FeedbackResult.ValidFeedback : FeedbackResult.InvalidFeedback);
        
        assertEquals(resTmp, res);
        
    }

    @AfterClass
    public static void afterTests() {
        //planeFacade.removeAll();
        //placeFacade.removeAll();

        //todo: remove the indexes
        
    }
   
    
}