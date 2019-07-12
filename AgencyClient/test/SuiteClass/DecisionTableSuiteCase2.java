package SuiteClass;

import Operations.Operations;
import Operations.Config;
import Operations.FeedbackResult;
import java.util.Arrays;
import java.util.Collection;
import logic.AgencyManagerRemote;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaceDTO;
import logic.TPlaneDTO;
import logic.TPurchaseDTO;
import logic.TTripDTO;
import logic.TUserDTO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class DecisionTableSuiteCase2 {
    
    //private static AgencyManagerRemote sAgencyManager;

    private static TUserDTO userDTO;
    private static TTripDTO tripDTO;
    private FeedbackResult res;

    
    private static TPlaceDTO fromPlace;
    private static TPlaceDTO toPlace;
    private static TAirlineDTO airlineDTO; 
    private static TPlaneDTO planeDTO;
    private static TPurchaseDTO purchaseDTO;
    
    @BeforeClass
    public static void setUpClass() throws NoPermissionException {
       
        //sAgencyManager= Operations.initRemoteReferences(sAgencyManager);
        
        Operations.signinAsAdmin(Operations.sAgencyManager);

        fromPlace = Operations.createFromPlace(Operations.sAgencyManager);
        toPlace = Operations.createToPlace(Operations.sAgencyManager);
        
        airlineDTO = Operations.createAirline(Operations.sAgencyManager);
        planeDTO = Operations.createPlane(Operations.sAgencyManager);
        
        tripDTO = Operations.createTrip(Operations.sAgencyManager, airlineDTO, fromPlace, toPlace, planeDTO, 50.0, 1000);
        
        //create an user
        userDTO = Operations.createTestUser(Operations.sAgencyManager);
        //get the user
        userDTO=Operations.getUser(Operations.sAgencyManager, userDTO);
        
        
        //accept the user
        Operations.signinAsAdmin(Operations.sAgencyManager);
        Operations.sAgencyManager.acceptUser(userDTO);
        
        //signin again as the accepted user
        Operations.signinAsTestUser(Operations.sAgencyManager, userDTO);
        
        
        
    }

    public DecisionTableSuiteCase2(String usernameTmp, String passwordTmp, boolean isAcceptedUserTmp, boolean isOperator, FeedbackResult res) {
        
        //edit the user
        this.userDTO.setUsername(usernameTmp);
        this.userDTO.setPassword(passwordTmp);
        this.userDTO.setAccepted(isAcceptedUserTmp);
        if(!isOperator){
            this.userDTO.setUsertype(logic.Config.CLIENT);
            this.userDTO.setClientName("Client Name");
            this.userDTO.setBalance(10000.0);
        }
        else
        {
            this.userDTO.setUsertype(logic.Config.OPERATOR);
            this.userDTO.setClientName("");
        }
        
        Operations.editTestUser(Operations.sAgencyManager, userDTO);
        userDTO=Operations.getUser(Operations.sAgencyManager, userDTO);
        //login from the normal user
        Operations.signinAsTestUser(Operations.sAgencyManager, userDTO);
        userDTO=Operations.getUser(Operations.sAgencyManager, userDTO);

        this.res = res;
    }
    
    @Parameterized.Parameters
    public static Collection valuesToTest() {
        return Arrays.asList(new Object[][] {
            
            //String usernameTmp, String passwordTmp, boolean isAcceptedUserTmp, boolean isOperator, FeedbackResult res
            {"abc", "12345689", false, false , FeedbackResult.InvalidUser},
            {"abcdefghi", "123", false, false , FeedbackResult.InvalidUser},
            {"abcdefghi", "12345689", false, false , FeedbackResult.UserWithNoPermissions},
            {"abcdefghi", "12345689", true, false , FeedbackResult.MayBuySeatsToTrip},
            {"abcdefghi", "12345689", true, false , FeedbackResult.MayConsultTrips},
            {"abcdefghi", "12345689", true, true , FeedbackResult.MayConsultTrips}
            
        });
    }
    
    @Test
    public void testSeveral() throws NoPermissionException {
        boolean allowedInvalidUsername = false, allowedInvalidPassword = false;
        boolean noPermissionsNotAcceptedUser = false;
        boolean noPermissionsIsOperator = false;
        boolean mayConsultTrips = false;
        boolean mayBuySeatsToTrips = false;
        
        
        allowedInvalidUsername = (userDTO.getUsername().length() < 8);
        
        allowedInvalidPassword = (userDTO.getPassword().length() < 8);
        
        mayConsultTrips = (Operations.sAgencyManager.findAllTrips().isEmpty()? false: true);
            
        purchaseDTO = Operations.buyAndFinishPurchaseCase2(Operations.sAgencyManager, tripDTO, 1);
        mayBuySeatsToTrips = (purchaseDTO == null? false: true);

        //the user did not had permissions...
        if(mayBuySeatsToTrips == false)
        {
            //if isn't a accept user cannot consult trips neither buy seats
            if(!userDTO.getAccepted())
                noPermissionsNotAcceptedUser = true;
            //if has no permissions because the user is an operator
            else if(userDTO.getUsertype() == logic.Config.OPERATOR)
                noPermissionsIsOperator = true;
        }
        
        //R1
        if(allowedInvalidUsername && res == FeedbackResult.InvalidUser)
        {
            clearTmpData();
            assertFalse("Username Invalido",true);
        }
        //R2
        else if(allowedInvalidPassword && res == FeedbackResult.InvalidUser)
        {
            clearTmpData();
            assertFalse("Password Invalido",true);
        }
        //R3
        else if(noPermissionsNotAcceptedUser && res == FeedbackResult.UserWithNoPermissions)
        {
            clearTmpData();
            assertTrue(true);
        }
        //R4
        else if(userDTO.getUsertype() == logic.Config.CLIENT && mayBuySeatsToTrips && res == FeedbackResult.MayBuySeatsToTrip
                || userDTO.getUsertype() == logic.Config.CLIENT && mayConsultTrips && res == FeedbackResult.MayConsultTrips)
        {
            clearTmpData();
            assertTrue(true);
        }
        //R4
        else if(userDTO.getUsertype() == logic.Config.OPERATOR && mayConsultTrips && res == FeedbackResult.MayConsultTrips)
        {
            clearTmpData();
            assertTrue(true);
        }
        else
        {
            clearTmpData();
            assertFalse(true);
        }
        
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

    static void clearAllData() throws NoPermissionException{
        
        Operations.signinAsAdmin(Operations.getAgencyRemote());
        Operations.getAgencyRemote().deleteAllDataByFind();
    }

    private void clearTmpData() throws NoPermissionException {
        if(purchaseDTO != null)
        {
            Operations.sAgencyManager.removeSeatsOfActualPurchase(purchaseDTO, tripDTO);
            Operations.sAgencyManager.removeActualPurchase(purchaseDTO);
        }
        //put the user as like he was before the test
        //userDTO = Operations.createTestUser(sAgencyManager);
        userDTO=Operations.getUser(Operations.sAgencyManager, userDTO);
        userDTO.setUsername(Config.TEST_USERNAME);
        userDTO.setPassword(Config.TEST_PASS);
        userDTO.setClientName("Client Name");
        userDTO.setUsertype(logic.Config.CLIENT);
        Operations.editTestUser(Operations.sAgencyManager, userDTO);
       
    }
}
