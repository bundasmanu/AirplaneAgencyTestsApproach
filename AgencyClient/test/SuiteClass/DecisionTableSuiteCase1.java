package SuiteClass;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Operations.Operations;
import Operations.FeedbackResult;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
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

/**
 *
 * @author gustavo
 */
@RunWith(Parameterized.class)
public class DecisionTableSuiteCase1 {
    
    //private static AgencyManagerRemote sAgencyManager;
    
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
        
        /*LIMPEZA DE TODOS OS DADOS QUE FORAM CRIADOS AO LONGO DOS TESTES REALIZADOS*/
        Operations.signinAsAdmin(Operations.getAgencyRemote());
        
        Operations.deleteTrip(Operations.getAgencyRemote(), trip);
        Operations.deleteAirline(Operations.getAgencyRemote(), airlineTrip);
        Operations.deletePlane(Operations.getAgencyRemote(), planeTrip);
        Operations.deleteFromPlace(Operations.getAgencyRemote(), fromPlace);
        Operations.deleteToPlace(Operations.getAgencyRemote(), toPlace);
   
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
        
    }
    
    public DecisionTableSuiteCase1(boolean isOperator, int moneyInAccount, boolean TripIsFinished, int numberSeatsBought ,FeedbackResult feedback) {
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
        Operations.getAgencyRemote().depositToAccount(n);
    }
    
    @Test
    public void testCase() throws NoPermissionException{
        
        /*CASO DE TESTE, VALIDACAO DAS VARIAS REGRAS IDENTIFICADAS, NA REALIZAÇÃO DA TABELA DE DECISAO*/
        try {
            Operations.getAgencyRemote().setDate(this.randomDateValue(finishedTrip));/*ALTERACAO DA HORA*/

            /*COMPRA DE BILHETES*/
            Operations.signinAsTestUser(Operations.getAgencyRemote());
            depositMoneyClientAccount(20000);/*PARA TER DINHEIRO PARA COMPRAR AS VIAGENS INICIAIS*/
            TPurchaseDTO tpurchase = null;
            if (numberSeats != 0) {
                tpurchase = Operations.buyAndFinishPurchaseCase2(Operations.getAgencyRemote(), trip, numberSeats);
            }

            /*CASO SEJA CLIENTE EFETUA TODAS AS VALIDACOES, QUE ESTAO NO INTERIOR DO CICLO*/
            if (isOo == false) {
                /*LOGIN COM UTILIZADOR, PARA DEPOSITAR DINHEIRO*/
                
                userDTO.setBalance(0.0);
                Operations.editTestUser(Operations.getAgencyRemote(), userDTO);
                Operations.getAgencyRemote().depositToAccount(monInAccount);
            } else {/*CASO SEJA OPERADOR FACO LOGIN COM ESTE*/
                Operations.signinAsAdmin(Operations.getAgencyRemote());
            }

            /*MEDIANTE O TIPO DE UTILIZADOR QUE É VERIFICAR SE PERMITE A COMPRA DE UM BILHETE*/
            TPurchaseDTO newPurchase = null;

            newPurchase = Operations.buyAndFinishPurchaseCase2(Operations.getAgencyRemote(), trip, 1);

            
            if (newPurchase != null && res == FeedbackResult.CompraComSucesso) {
                this.limpaDados(tpurchase, newPurchase);
                assertTrue("Compra Sucesso", true);
            } else if (newPurchase == null && res == FeedbackResult.CompraImpossivel) {
                this.limpaDados(tpurchase, newPurchase);
                assertTrue("Compra Insucesso", true);
            } else {
                this.limpaDados(tpurchase, newPurchase);
                assertFalse("", true);
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void limpaDados(TPurchaseDTO tpurchase, TPurchaseDTO newPurchase) throws NoPermissionException{
        Operations.signinAsTestUser(Operations.getAgencyRemote());
        if (tpurchase != null) {
            Operations.getAgencyRemote().removeSeatsOfActualPurchase(tpurchase, trip);
            Operations.getAgencyRemote().removeActualPurchase(tpurchase);
        }
        if (newPurchase != null) {
            Operations.getAgencyRemote().removeSeatsOfActualPurchase(newPurchase, trip);
            Operations.getAgencyRemote().removeActualPurchase(newPurchase);
        }
        Operations.signinAsAdmin(Operations.getAgencyRemote());
    }
    
    public static void logicOfTests() throws NoPermissionException{
        
        /*DESCRICAO DE TODO O CONTEUDO INICIAL, QUE PERMITE VERIFICAR SE UM DETERMINADO TIPO DE UTILIZADOR PODE EFETUAR UMA COMPRA NO SISTEMA*/
        
        /*OBTENCAO DA REFERENCIA PARA O OBJETO REMOTO*/
        //Operations.sAgencyManager=Operations.initRemoteReferences(Operations.sAgencyManager);
        
        /*Login do admin*/
        Operations.signinAsAdmin(Operations.getAgencyRemote());
        
        /*CRIACAO DE UMA PARTIDA E DE UM DESTINO--> VALORES POR DEFEITOS, INCLUIDOS NA DEFINICAO DO METODO*/
        fromPlace=Operations.createFromPlace(Operations.getAgencyRemote());
        toPlace=Operations.createToPlace(Operations.getAgencyRemote());
        
        /*CRIACAO DA COMPANHIA*/
        airlineTrip=Operations.createAirline(Operations.getAgencyRemote());
        
        /*CRIACAO DO PLANE*/
        planeTrip=Operations.createPlane(Operations.getAgencyRemote());/*LIMITE DO AVIAO SAO 10 LUGARES*/
        
        /*CRIACAO DA TRIP, TENDO EM CONTA O DESTINO, PARTIDA, COMPANHIA E O SEU AVIAO*/
        trip=Operations.createTrip(Operations.getAgencyRemote(), airlineTrip, fromPlace, toPlace, planeTrip, 50, 100);/*PRECO DO BILHETE SAO 10, E A HORA DE PARTIDA É 100*/
        
        /*CRIACAO DE UM UTILIZADOR*/
        userDTO = Operations.createTestUser(Operations.getAgencyRemote());
        Operations.getAgencyRemote().acceptUser(userDTO);
        
        /*OBTENCAO DO DTO DO USER COMPLETO*/
        userDTO=Operations.getUser(Operations.getAgencyRemote(), userDTO);
        
    }
    
}
