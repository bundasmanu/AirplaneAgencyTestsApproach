
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import logic.AgencyManagerRemote;
import logic.GuestAgencyManagerRemote;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaceDTO;
import logic.TPlaneDTO;
import logic.TTripDTO;
import logic.TTripFeedbackDTO;
import logic.TripsManagement.TripsManagerLocal;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class BvaEctCase1{

    static EJBContainer container;
    static TripsManagerLocal tripsManagerLocal;
    
    TTripFeedbackDTO tripFeedbackDTO;
    TTripDTO tripDTO;
    FeedbackResult res;

    @BeforeClass
    public static void beforeTests() throws NoPermissionException, NamingException {
       
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(EJBContainer.MODULES, new File("build/jar"));
        container = EJBContainer.createEJBContainer(properties);
        System.out.println("Opening the container");
        
        
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        tripsManagerLocal = (TripsManagerLocal)container.getContext().lookup("java:global/classes/TripsManagerLocal");
    
    
        TPlaceDTO fromPlace = new TPlaceDTO();
        fromPlace.setAddress("Adress xtpo");
        fromPlace.setCity("Lisbon");
        fromPlace.setCountry("Portugal");
        
        TPlaceDTO toPlace = new TPlaceDTO();
        toPlace.setAddress("Adress xtpo");
        toPlace.setCity("Porto");
        toPlace.setCountry("Portugal");
        
        tripsManagerLocal.addPlace(toPlace, Config.adminUsername);
        tripsManagerLocal.addPlace(fromPlace, Config.adminUsername);
        
        TAirlineDTO airlineDTO = new TAirlineDTO();
        airlineDTO.setAirlineName("AirlineName1");
        airlineDTO.setPhoneNumber("939898321");
        tripsManagerLocal.addAirline(airlineDTO, Config.adminUsername);
        
        TPlaneDTO planeDTO = new TPlaneDTO();
        planeDTO.setPlaneLimit(100);
        planeDTO.setPlaneName("Plane1");
        tripsManagerLocal.addPlane(planeDTO, Config.adminUsername);
        
        
        TTripDTO tripDTO = new TTripDTO();
        tripDTO.setAirlineDTO(airlineDTO);
        tripDTO.setFromPlaceDTO(fromPlace);
        tripDTO.setToPlaceDTO(toPlace);
        tripDTO.setPlaneDTO(planeDTO);
        tripDTO.setPrice(50.0);
        tripDTO.setDatetrip(100);
        tripsManagerLocal.addTrip(tripDTO, Config.adminUsername);
        
        
    }
            
    public BvaEctCase1(int feedbackScore, boolean done, FeedbackResult res) {
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
        
        System.out.println("Testing: TripFeedback score: "+tripFeedbackDTO.getScore()+", TripDTO done: "+tripDTO.getDone()+" -> "+ res);
       
        boolean booleanResult = tripsManagerLocal.addFeedbackToTrip(tripDTO, tripFeedbackDTO, Config.adminUsername);
        
        FeedbackResult resTmp = (booleanResult == true? FeedbackResult.ValidFeedback : FeedbackResult.InvalidFeedback);
        
        assertEquals(resTmp, res);
        
    }

    @AfterClass
    public static void afterTests() {
        //planeFacade.removeAll();
        //placeFacade.removeAll();
        container.close();
    }
    
}
