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
import static org.junit.Assert.assertEquals;
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
        initRemoteReferences();
        
        //create an user
        TUserDTO userDTO = Operations.createTestUser(sAgencyManager);
        
        //accept the user
        Operations.signinAsAdmin(sAgencyManager);
        sAgencyManager.acceptUser(userDTO);
        
        TPlaceDTO fromPlace = Operations.createFromPlace(sAgencyManager);
        TPlaceDTO toPlace = Operations.createToPlace(sAgencyManager);

        sAgencyManager.addPlace(fromPlace);        
        sAgencyManager.addPlace(toPlace);
        
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
    
    private static void initRemoteReferences() {
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
        
        } catch (NamingException e) {
           System.out.println(e.getMessage());
           e.printStackTrace();
        }
    }
    
}