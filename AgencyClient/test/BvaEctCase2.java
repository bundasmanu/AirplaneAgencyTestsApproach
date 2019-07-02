/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;
import java.util.Collection;
import logic.AgencyManagerRemote;
import logic.*;
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
public class BvaEctCase2 {
    
    private static AgencyManagerRemote sAgencyManager;
    
    static TUserDTO userDTO;
    
    static TPlaceDTO fromPlace;
    static TPlaceDTO toPlace;
    
    static TPlaneDTO planeTrip;
    static TAirlineDTO airlineTrip;
    static TTripDTO trip;
    
    int limitNumberTrip;
    
    FeedbackResult res;
    
    @BeforeClass
    public static void setUpClass() throws NoPermissionException {
        
        logicOfTests();
        
    }
    
    @AfterClass
    public static void tearDownClass() throws NoPermissionException {
        
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
    
    public BvaEctCase2(String fromPlace, String toPlace, int hourLeft, int limitNumberPersons, FeedbackResult feedback) {
        this.fromPlace.setCity(fromPlace);
        this.toPlace.setCity(toPlace);
        this.trip.setDatetrip(hourLeft);
        this.limitNumberTrip=limitNumberPersons;
        this.res=feedback;
    }

    @Parameterized.Parameters
    public static Collection valuesToTest() {
        return Arrays.asList(new Object[][] {
            /*WEAK NORMAL*/
            {"Porto", "Madrid", 50, 5 , FeedbackResult.ValidFeedback},
            {"Madrid", "Porto", 200, 10 , FeedbackResult.InvalidFeedback},
            {"Madrid", "Porto", 50, 0 , FeedbackResult.ValidFeedback},
            /*STRONG NORMAL*/
            {"Porto", "Porto", 200 , 10 , FeedbackResult.InvalidFeedback},
            {"Porto", "Madrid", 200, 10 , FeedbackResult.InvalidFeedback},
            {"Madrid", "Porto", 200, 10 , FeedbackResult.InvalidFeedback},
            {"Madrid", "Madrid", 200, 10 , FeedbackResult.InvalidFeedback},
            {"Porto", "Porto", 50 , 10 , FeedbackResult.InvalidFeedback},
            {"Porto", "Madrid", 50, 10 , FeedbackResult.InvalidFeedback},
            {"Madrid", "Porto", 50, 10 , FeedbackResult.InvalidFeedback},
            {"Madrid", "Madrid", 50, 10 , FeedbackResult.InvalidFeedback},
            {"Porto", "Porto", 200 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 11--> O SISTEMA DEIXA COMPRAR BILHETE, MSM DPS DE ADQUIRIR DA VIAGEM TER TERMINADO E NAO VALIDADO MESMO DESTINO E PARTIDA*/
            {"Porto", "Madrid", 200, 0 , FeedbackResult.InvalidFeedback}, /*FALHA 12--> O SISTEMA DEIXA COMPRAR BILHETE, MSM DPS DE ADQUIRIR DA VIAGEM TER TERMINADO*/
            {"Madrid", "Porto", 200, 0 , FeedbackResult.InvalidFeedback}, /*FALHA 13--> O SISTEMA DEIXA COMPRAR BILHETE, MSM DPS DE ADQUIRIR DA VIAGEM TER TERMINADO*/
            {"Madrid", "Madrid", 200, 0 , FeedbackResult.InvalidFeedback}, /*FALHA 14--> O SISTEMA DEIXA COMPRAR BILHETE, MSM DPS DE ADQUIRIR DA VIAGEM TER TERMINADO E NAO VALIDADO MESMO DESTINO E PARTIDA*/
            {"Porto", "Porto", 50 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 15--> DEIXA COMPRAR BILHETES COM O MSM DESTINO E PARTIDA*/
            {"Porto", "Madrid", 50, 0 , FeedbackResult.ValidFeedback},
            {"Madrid", "Porto", 50, 0 , FeedbackResult.ValidFeedback},
            {"Madrid", "Madrid", 50, 0 , FeedbackResult.ValidFeedback},
            {"Porto", "Porto", 200 , 5 , FeedbackResult.InvalidFeedback}, /*FALHA 19--> O SISTEMA DEIXA COMPRAR BILHETE, MSM DPS DE ADQUIRIR DA VIAGEM TER TERMINADO E NAO VALIDADO MESMO DESTINO E PARTIDA*/
            {"Porto", "Madrid", 200, 6 , FeedbackResult.ValidFeedback},
            {"Madrid", "Porto", 200, 7 , FeedbackResult.ValidFeedback},
            {"Madrid", "Madrid", 200, 9 , FeedbackResult.InvalidFeedback}, /*FALHA 22--> DEIXA COMPRAR BILHETES COM O MSM DESTINO E PARTIDA*/
            {"Porto", "Porto", 50 , 5 , FeedbackResult.InvalidFeedback},
            {"Porto", "Madrid", 50, 6 , FeedbackResult.ValidFeedback},
            {"Madrid", "Porto", 50, 8 , FeedbackResult.ValidFeedback},
            {"Madrid", "Madrid", 50, 9 , FeedbackResult.InvalidFeedback}, /*FALHA 26--> DEIXA COMPRAR BILHETES COM O MSM DESTINO E PARTIDA*/
            /*WEAK ROBUST*/
            {"Moscovo", "Porto", 200 , 10 , FeedbackResult.InvalidFeedback}, /*NAO FALHA, MAS DEVIA FALHAR 27--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM SEM 2 CIDADES DE DESTINO*/
            {"Porto", "Moscovo", 50 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 28--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM SEM 2 CIDADES DE DESTINO*/
            {"Madrid", "Porto", 50, -10 , FeedbackResult.InvalidFeedback},
            {"Porto", "Madrid", 200, 11 , FeedbackResult.InvalidFeedback}
        });
    }
    
    @Test
    public void testCase2(){
        
        try{
            
           /*LOGIN COMO OPERADOR--> COLOCAR NA BD OS PARAMETROS ATUALIZADOS, PASSADOS NO CONSTRUTOR*/
           Operations.signinAsAdmin(sAgencyManager);
           
           sAgencyManager.editPlace(fromPlace);
           sAgencyManager.editPlace(toPlace);
           sAgencyManager.editPlane(planeTrip);
           
           /*INSERCAO DE BILHETES NA TRIP*/
           
           Operations.signinAsTestUser(sAgencyManager);
           TPurchaseDTO tpurchase=null;
           if(this.limitNumberTrip!=0){
                tpurchase=Operations.buyAndFinishPurchaseCase2(sAgencyManager, trip, this.limitNumberTrip);
           }
           
           Operations.signinAsAdmin(sAgencyManager);
           
           sAgencyManager.editTrip(trip);
           trip=sAgencyManager.findTrip(trip.getId());
           
           /*TENTATIVA DE FAZER MAIS UMA COMPRA*/
           Operations.signinAsTestUser(sAgencyManager);
           
           TPurchaseDTO newPurchase=null;
           
           newPurchase=Operations.buyAndFinishPurchaseCase2(sAgencyManager, trip, 1);
           
            if(newPurchase!=null && this.res==FeedbackResult.ValidFeedback){
                this.limpaDados(tpurchase, newPurchase);
                assertTrue("", true);
            }
            else if(newPurchase==null && this.res==FeedbackResult.InvalidFeedback){
                this.limpaDados(tpurchase, newPurchase);
                assertTrue("", true);
            }   
            else{
                this.limpaDados(tpurchase, newPurchase);
                assertFalse("", true);
            }
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    public void limpaDados(TPurchaseDTO tpurchase, TPurchaseDTO newPurchase) throws NoPermissionException{
        if (tpurchase != null) {
            sAgencyManager.removeSeatsOfActualPurchase(tpurchase, trip);
            sAgencyManager.removeActualPurchase(tpurchase);
        }
        if (newPurchase != null) {
            sAgencyManager.removeSeatsOfActualPurchase(newPurchase, trip);
            sAgencyManager.removeActualPurchase(newPurchase);
        }
    }
    
    public static void logicOfTests() throws NoPermissionException{
        
        sAgencyManager=Operations.initRemoteReferences(sAgencyManager);
        
        /*Login do admin*/
        Operations.signinAsAdmin(sAgencyManager);
        
        /*Adicao dos places*/
        fromPlace=Operations.createFromPlace(sAgencyManager);
        toPlace=Operations.createToPlace(sAgencyManager);
        
        /*CRIACAO AIRLINE*/
        airlineTrip=Operations.createAirline(sAgencyManager);
        
        /*CRIACAO DO PLANE*/
        planeTrip=Operations.createPlane(sAgencyManager);
        
        /*CRIACAO DA TRIP*/
        trip=Operations.createTrip(sAgencyManager, airlineTrip, fromPlace, toPlace, planeTrip, 50, 100);
        
        /*CRIACAO DO UTILIZADOR*/
        userDTO = Operations.createTestUser(sAgencyManager);
        sAgencyManager.acceptUser(userDTO);
        
        /*LOGIN DO UTILIZADOR*/
        Operations.signinAsTestUser(sAgencyManager); 
        
        /*USER QUE ESTA LOGADO, DEPOSITAR DINHEIRO*/
        sAgencyManager.depositToAccount(50000);
        
    }
    
}
