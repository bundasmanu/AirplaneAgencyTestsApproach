/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;
import java.util.Collection;
import logic.AgencyManagerRemote;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaceDTO;
import logic.TPlaneDTO;
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
    private static boolean isOperator;
    private static int moneyInAccount;
    private static  int numberSeats;
    
    static FeedbackResult res;
    
    @BeforeClass
    public static void setUpClass() throws NoPermissionException {
        
        logicOfTests();
        
    }
    
    @AfterClass
    public static void tearDownClass() throws NoPermissionException {
        
        /*LIMPEZA DE TODOS OS DADOS QUE FORAM CRIADOS AO LONGO DOS TESTES REALIZADOS*/
        Operations.signinAsAdmin(sAgencyManager);
        
        Operations.deleteTrip(sAgencyManager, trip);
        Operations.deleteAirline(sAgencyManager, airlineTrip);
        Operations.deletePlane(sAgencyManager, planeTrip);
        Operations.deleteFromPlace(sAgencyManager, fromPlace);
        Operations.deleteToPlace(sAgencyManager, toPlace);
   
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
        
    }
    
    public DecisionTableCase1(boolean isOperator, int moneyInAccount, boolean TripIsFinished, int numberSeatsBought ,FeedbackResult feedback) {
        this.isOperator=isOperator;
        this.moneyInAccount=moneyInAccount;
        this.finishedTrip=TripIsFinished;
        this.numberSeats=numberSeatsBought;
        res=feedback;
    }
    
    @Parameterized.Parameters
    public static Collection valuesToTest() {
        return Arrays.asList(new Object[][] {
            /*ESTES 4 TESTES PARA O CASO 1--> PARA MOSTRAR MSM QUE NAO DEIXA COMPRAR BILHETES*/
            {true, 0, true, true , FeedbackResult.CompraImpossivel},
            {true, 0, true, false , FeedbackResult.CompraImpossivel},
            {true, 0, false, true , FeedbackResult.CompraImpossivel},
            {true, 0, false, false , FeedbackResult.CompraImpossivel},
        });
    }
    
    @Test
    public void testCase(){
        
        /*CASO DE TESTE, VALIDACAO DAS VARIAS REGRAS IDENTIFICADAS, NA REALIZAÇÃO DA TABELA DE DECISAO*/
        
        /*CASO SEJA CLIENTE EFETUA TODAS AS VALIDACOES, QUE ESTAO NO INTERIOR DO CICLO*/
        if(isOperator=false){
            if(moneyInAccount!=0){
                userDTO=Operations.createTestUser(sAgencyManager);
            }
        }
        
    }
    
    public static void logicOfTests() throws NoPermissionException{
        
        /*DESCRICAO DE TODO O CONTEUDO INICIAL, QUE PERMITE VERIFICAR SE UM DETERMINADO TIPO DE UTILIZADOR PODE EFETUAR UMA COMPRA NO SISTEMA*/
        
        /*OBTENCAO DA REFERENCIA PARA O OBJETO REMOTO*/
        sAgencyManager=Operations.initRemoteReferences(sAgencyManager);
        
        /*Login do admin*/
        Operations.signinAsAdmin(sAgencyManager);
        
        /*CRIACAO DE UMA PARTIDA E DE UM DESTINO--> VALORES POR DEFEITOS, INCLUIDOS NA DEFINICAO DO METODO*/
        fromPlace=Operations.createFromPlace(sAgencyManager);
        toPlace=Operations.createToPlace(sAgencyManager);
        
        /*CRIACAO DA COMPANHIA*/
        airlineTrip=Operations.createAirline(sAgencyManager);
        
        /*CRIACAO DO PLANE*/
        planeTrip=Operations.createPlane(sAgencyManager);
        
        /*CRIACAO DA TRIP, TENDO EM CONTA O DESTINO, PARTIDA, COMPANHIA E O SEU AVIAO*/
        trip=Operations.createTrip(sAgencyManager, airlineTrip, fromPlace, toPlace, planeTrip, 50, 100);
        
    }
    
}
