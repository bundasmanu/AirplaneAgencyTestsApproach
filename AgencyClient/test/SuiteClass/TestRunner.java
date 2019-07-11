package SuiteClass;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

    public static void main(String[] args) {
        
        Result result = JUnitCore.runClasses(SuiteTests.class);

        int testes_a_testar=result.getRunCount();
        int testes_falharam=result.getFailureCount();
        
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        
        double res=100-((double)testes_falharam/testes_a_testar)*100;
        int tempo= (int) (result.getRunTime()/1000);
        
        System.err.println("Percentagem de acertos:"+res+"%");
        System.err.println("Tempo gasto: "+tempo);
        System.out.println(result.wasSuccessful());
        
    }
    
}
