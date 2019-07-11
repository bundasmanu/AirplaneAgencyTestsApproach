package SuiteClass;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Operations.Operations;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import SuiteClass.*;

/**
 *
 * @author gustavo
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({BvaEctSuiteCase1.class,BvaEctSuiteCase2.class,DecisionTableSuiteCase1.class, DecisionTableSuiteCase2.class, GraphCoverageSuiteCase1.class, GraphCoverageSuiteCase2.class})
public class SuiteTests {

    @BeforeClass
    public static void setUpClass() throws Exception {
        Operations.sAgencyManager=Operations.initRemoteReferences(Operations.sAgencyManager);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        
    }

    @Before
    public void setUp() throws Exception {
        
    }

    @After
    public void tearDown() throws Exception {
        
    }
    
}
