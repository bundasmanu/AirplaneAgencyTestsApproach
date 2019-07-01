import javax.ejb.EJB;
import logic.TripsManagement.TripsManagerLocal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class BvaEctCase1{

    @EJB
    TripsManagerLocal tripsManagerLocal;
    
    float a, b, c;
    FeedbackResult res;

    public TriangleTest(float a, float b, float c, TriangleType res) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.res = res;
    }

    @Parameterized.Parameters
    public static Collection valuesToTest() {
        return Arrays.asList(new Object[][] {
                {-2, -1, 12, TriangleType.INVALID_INPUTS}

        });
    }


    @Test
    public void testSeveral() {
        System.out.println("Testing "+a+","+b+","+c+" -> "+res);
        Triangle instance = new Triangle(a,b,c);
        assertEquals(res, instance.getType());
    }

}