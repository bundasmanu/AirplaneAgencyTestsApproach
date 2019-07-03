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
public class DecisionTableCase2 {
    
    private static AgencyManagerRemote sAgencyManager;

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
        sAgencyManager= Operations.initRemoteReferences(sAgencyManager);
        
        Operations.signinAsAdmin(sAgencyManager);

        fromPlace = Operations.createFromPlace(sAgencyManager);
        toPlace = Operations.createToPlace(sAgencyManager);
        
        airlineDTO = Operations.createAirline(sAgencyManager);
        planeDTO = Operations.createPlane(sAgencyManager);
        
        tripDTO = Operations.createTrip(sAgencyManager, airlineDTO, fromPlace, toPlace, planeDTO, 50.0, 100);
        
    }

    public DecisionTableCase2(String usernameTmp, String passwordTmp, boolean isAcceptedUserTmp, boolean isOperator) {
    
        //create an user
        TUserDTO userDTO = Operations.createTestUser(sAgencyManager, usernameTmp, passwordTmp, isOperator);
        
        
        if(isAcceptedUserTmp)
        {
            //accept the user
            Operations.signinAsAdmin(sAgencyManager);
            sAgencyManager.acceptUser(userDTO);
        }
        
        Operations.signinAsTestUser(sAgencyManager, userDTO);
        
    }
    
    @Parameterized.Parameters
    public static Collection valuesToTest() {
        return Arrays.asList(new Object[][] {
                
                });
    }
    
    @Test
    public void testSeveral() throws NoPermissionException {
        boolean validUsername, validPassword;
        
        //para validar um user é fazer edit do mesmo... se este retornar true com
        //username com < 8
        
        //para ver se o consultar trips e o comprar lugares funciam ambos para o
        //cliente normal devera passar... variaveis temp 
        
        //remove the user...
        //TODO: need to implement code to remove users
        
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
        
        sAgencyManager.removeSeatsOfActualPurchase(purchaseDTO, tripDTO);
        sAgencyManager.removeActualPurchase(purchaseDTO);
        
        Operations.signinAsAdmin(sAgencyManager);
        
        Operations.deleteTrip(sAgencyManager, tripDTO);
        Operations.deleteAirline(sAgencyManager, airlineDTO);
        Operations.deletePlane(sAgencyManager, planeDTO);
        Operations.deleteFromPlace(sAgencyManager, fromPlace);
        Operations.deleteToPlace(sAgencyManager, toPlace);
        
    }
}
