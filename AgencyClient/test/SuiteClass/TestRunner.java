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
        
        System.out.println("Nº total de testes efetuados:"+testes_a_testar);
        System.out.println("Nº total de testes que falharam:"+testes_falharam);
        System.out.println("Percentagem de acertos:"+res+"%");
        System.out.println("Tempo gasto: "+tempo);
        System.out.println("Sucesso nos testes: " + result.wasSuccessful());
        
    }
    
}
