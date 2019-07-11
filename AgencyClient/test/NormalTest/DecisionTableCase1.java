package NormalTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
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
import Operations.*;

/**
 *
 * @author gustavo
 */
@RunWith(Parameterized.class)
public class DecisionTableCase1 {
    
    private static AgencyManagerRemote sAgencyManager;
    
    static TUserDTO userDTO; /*NECESSARIO PARA EFETUAR TESTES COM UM UTILIZADOR*/
    
    /*OBJETOS NECESSÁRIOS, PARA REALIZAR UMA COMPRA NO SISTEMA*/
    static TPlaceDTO fromPlace;
    static TPlaceDTO toPlace;
    
    static TPlaneDTO planeTrip;
    static TAirlineDTO airlineTrip;
    static TTripDTO trip;
    
    /*VARIAVEIS DE CONTROLO DE CADA REQUISITO DE TESTE*/
    private static boolean finishedTrip;
    private static boolean isOo;
    private static int monInAccount;
    private static  int numberSeats;
    
    static FeedbackResult res;
    
    @BeforeClass
    public static void setUpClass() throws NoPermissionException {
        
        logicOfTests();
        
    }
    
    @AfterClass
    public static void tearDownClass() throws NoPermissionException {
        
        Operations.signinAsAdmin(sAgencyManager);
        sAgencyManager.deleteAllDataByFind();
   
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
        
    }
    
    public DecisionTableCase1(boolean isOperator, int moneyInAccount, boolean TripIsFinished, int numberSeatsBought ,FeedbackResult feedback) {
        isOo=isOperator;
        monInAccount=moneyInAccount;
        finishedTrip=TripIsFinished;
        numberSeats=numberSeatsBought;
        res=feedback;
    }
    
    @Parameterized.Parameters
    public static Collection valuesToTest() {
        return Arrays.asList(new Object[][] {
            /*ESTES 4 TESTES PARA A REGRA 1--> PARA MOSTRAR MSM QUE NAO DEIXA COMPRAR BILHETES*/
            {true, 0, true, 10 , FeedbackResult.CompraImpossivel},
            {true, 0, true, 0 , FeedbackResult.CompraImpossivel},
            {true, 0, false, 10 , FeedbackResult.CompraImpossivel},/*COMPRA IMPOSSIVEL POR DOIS MOTIVOS LIMITE DE LUGARES E É OPERADOR*/
            {true, 0, false, 0 , FeedbackResult.CompraImpossivel},
            /*REGRA 2*/
            {false, 10, false, 2 , FeedbackResult.CompraImpossivel},/*NAO TEM DINHEIRO*/
            {false, 10, true, 10 , FeedbackResult.CompraImpossivel},/*NAO TEM DINHEIRO, E JA FOI CONCLUIDA E ESTA CHEIA*/
            {false, 10, false, 10 , FeedbackResult.CompraImpossivel},/*NAO TEM DINHEIRO, E ESTA CHEIA*/
            {false, 10, true, 2 , FeedbackResult.CompraImpossivel},/*NAO TEM DINHEIRO E JA FOI CONCLUIDA*/
            /*REGRA 3*/
            {false, 100, true, 2 , FeedbackResult.CompraImpossivel},/*FALHA 8-->VAI FALHAR PORQUE O TEMPO NAO ESTA A SER VALIDADO*/
            {false, 100, true, 10 , FeedbackResult.CompraImpossivel},/*DA BEM POR CAUSA DO AVIAO JA ESTAR CHEIO, E A VALIDACAO DE COMPRA DE BILHETES A MAIS DO QUE OS PERMITIDOS ESTAR FEITA, SENAO O TESTE FALHAVA*/
            /*REGRA 4*/
            {false, 100, false, 10 , FeedbackResult.CompraImpossivel},
            /*REGRA 5*/
            {false, 1000, false, 2 , FeedbackResult.CompraComSucesso},
        });
    }
    
    public int randomDateValue(boolean tripIsFinished){
        
        Random random = new Random();
        int value;
        
        if(tripIsFinished==true){
            value = (int) (Math.random() * (100 - 0)) + 0;
        }
        else{
            value = (int) (Math.random() * (100 - 1000)) + 100;
        }
        
        return value;
    }
    
    public void depositMoneyClientAccount(int n){
        sAgencyManager.depositToAccount(n);
    }
    
    @Test
    public void testCase() throws NoPermissionException{
        
        /*CASO DE TESTE, VALIDACAO DAS VARIAS REGRAS IDENTIFICADAS, NA REALIZAÇÃO DA TABELA DE DECISAO*/
        try {
            sAgencyManager.setDate(this.randomDateValue(finishedTrip));/*ALTERACAO DA HORA*/

            /*COMPRA DE BILHETES*/
            Operations.signinAsTestUser(sAgencyManager);
            depositMoneyClientAccount(20000);/*PARA TER DINHEIRO PARA COMPRAR AS VIAGENS INICIAIS*/
            TPurchaseDTO tpurchase = null;
            if (numberSeats != 0) {
                tpurchase = Operations.buyAndFinishPurchaseCase2(sAgencyManager, trip, numberSeats);
            }

            /*CASO SEJA CLIENTE EFETUA TODAS AS VALIDACOES, QUE ESTAO NO INTERIOR DO CICLO*/
            if (isOo == false) {
                /*LOGIN COM UTILIZADOR, PARA DEPOSITAR DINHEIRO*/
                
                userDTO.setBalance(0.0);
                Operations.editTestUser(sAgencyManager, userDTO);
                sAgencyManager.depositToAccount(monInAccount);
            } else {/*CASO SEJA OPERADOR FACO LOGIN COM ESTE*/
                Operations.signinAsAdmin(sAgencyManager);
            }

            /*MEDIANTE O TIPO DE UTILIZADOR QUE É VERIFICAR SE PERMITE A COMPRA DE UM BILHETE*/
            TPurchaseDTO newPurchase = null;

            newPurchase = Operations.buyAndFinishPurchaseCase2(sAgencyManager, trip, 1);

            
            if (newPurchase != null && res == FeedbackResult.CompraComSucesso) {
                this.limpaDados(tpurchase, newPurchase);
                assertTrue("", true);
            } else if (newPurchase == null && res == FeedbackResult.CompraImpossivel) {
                this.limpaDados(tpurchase, newPurchase);
                assertTrue("", true);
            } else {
                this.limpaDados(tpurchase, newPurchase);
                assertFalse("", true);
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void limpaDados(TPurchaseDTO tpurchase, TPurchaseDTO newPurchase) throws NoPermissionException{
        Operations.signinAsTestUser(sAgencyManager);
        if (tpurchase != null) {
            sAgencyManager.removeSeatsOfActualPurchase(tpurchase, trip);
            sAgencyManager.removeActualPurchase(tpurchase);
        }
        if (newPurchase != null) {
            sAgencyManager.removeSeatsOfActualPurchase(newPurchase, trip);
            sAgencyManager.removeActualPurchase(newPurchase);
        }
        Operations.signinAsAdmin(sAgencyManager);
    }
    
    public static void logicOfTests() throws NoPermissionException{
        
        sAgencyManager= Operations.initRemoteReferences(sAgencyManager);
        
        Operations.signinAsAdmin(sAgencyManager);

        fromPlace = Operations.createFromPlace(sAgencyManager);
        toPlace = Operations.createToPlace(sAgencyManager);
        
        airlineTrip = Operations.createAirline(sAgencyManager);
        planeTrip = Operations.createPlane(sAgencyManager);
                System.out.println("\nEspera criar trip\n");

        trip = Operations.createTrip(sAgencyManager, airlineTrip, fromPlace, toPlace, planeTrip, 50.0, 1000);
                System.out.println("\nCriada trip: "+trip.toString()+"\n");

        //create an user
        userDTO = Operations.createTestUser(sAgencyManager);
        //get the user
        userDTO=Operations.getUser(sAgencyManager, userDTO);
        
        
        //accept the user
        Operations.signinAsAdmin(sAgencyManager);
        sAgencyManager.acceptUser(userDTO);
        
        //signin again as the accepted user
        Operations.signinAsTestUser(sAgencyManager, userDTO);
    }
    
}