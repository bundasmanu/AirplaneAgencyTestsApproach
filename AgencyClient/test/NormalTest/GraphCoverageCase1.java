package NormalTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Operations.Operations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import logic.AgencyManagerRemote;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaceDTO;
import logic.TPlaneDTO;
import logic.TPurchaseDTO;
import logic.TSeatDTO;
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
public class GraphCoverageCase1 {
    
    /*DADOS NECESSARIOS PARA A EXECUCAO DOS TESTES*/
    
    /*REFERENCIA DO OBJETO REMOTO*/
    private static AgencyManagerRemote sAgencyManager;
    
    /*DADOS DE UM USER*/
    static TUserDTO  user;
    
    /*DADOS QUE PERMITEM A CRIACAO DE UMA TRIP--> A SER PASSADA COMO ARGUMENTO NO METODO A SER TESTADO*/
    static TPlaceDTO fromPlace;
    static TPlaceDTO toPlace;
    
    static TPlaneDTO planeTrip;
    static TAirlineDTO airlineTrip;
    static TTripDTO trip;
    
    /*VARIAVEIS DE CONTROLO DE CADA REQUISITO DE TESTE*/
    static int numberOfSeats;
    static int auctionedSeats;
    static int nonAuctionedSeats;
    static boolean result;
    
    static TUserDTO  auxTuser;
    
    @BeforeClass
    public static void setUpClass() throws NoPermissionException {
        
        logicOfTests();
        
    }
    
    @AfterClass
    public static void tearDownClass() throws NoPermissionException {
        
        Operations.signinAsAdmin(sAgencyManager);
        
        sAgencyManager.deleteAllDataByFind();
        /*Operations.deleteTrip(sAgencyManager, trip);
        Operations.deleteAirline(sAgencyManager, airlineTrip);
        Operations.deletePlane(sAgencyManager, planeTrip);
        Operations.deleteFromPlace(sAgencyManager, fromPlace);
        Operations.deleteToPlace(sAgencyManager, toPlace);
        sAgencyManager.deleteUser(auxTuser);*/
        
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
        
    }
    
    public GraphCoverageCase1(int nSeats, int auctSeats, int nonAuctSeats, boolean r) {
        numberOfSeats=nSeats;
        auctionedSeats=auctSeats;
        nonAuctionedSeats=nonAuctSeats;
        result=r;
    }
    
    @Parameterized.Parameters
    public static Collection valuesToTest() {
        return Arrays.asList(new Object[][] {
            {0,0,0,true}, /*T1 --> FAZ BEM*/
            {10,1,1,true}, /*T5--> FAZ BEM*/
            {10,3,0,true}, /*T7--> FAZ BEM*/
            {20,0,2,true} /*T6--> STRESS AQUI QUANDO SE COMPRA DOIS SEATS AUCTIONED, SO FUNCIONA PARA O 1*/ 
            
        });
    }
    
    @Test
    public void testCase(){
        
        try{
            
            Operations.signinAsAdmin(sAgencyManager);
            
            trip=sAgencyManager.findTrip(trip.getId());
            
            /*ALTERACAO DO LIMIT PLANE*/
            if(numberOfSeats!=10){
                planeTrip.setPlaneLimit(numberOfSeats);
                sAgencyManager.editPlane(planeTrip);
                planeTrip=sAgencyManager.findPlane(planeTrip.getId());
            }
            
            TPurchaseDTO purchase = null;
            /*SE O NUMERO DE SEATS FOR DIFERENTE DO AUCTIONED, É SINAL QUE NECESSITO QUE O USER AUXILIAR EFETUE COMPRAS*/
            if(numberOfSeats!=auctionedSeats){
                
                /*FAZER SIGN IN COM O UTILIZADOR AcUXILIAR*/
                Operations.signinAsTestUser(sAgencyManager, auxTuser);
                
                /*REALIZACAO DAS COMPRAS NECESSARIAS PREVIAMENTE ÀS COMPRAS DO USER*/
                purchase = null;
                if(auctionedSeats!=0 && nonAuctionedSeats!=0){
                    purchase = Operations.buyAndFinishPurchaseCase3(sAgencyManager, trip, (numberOfSeats-auctionedSeats)-1);
                }
                if(auctionedSeats==0 && nonAuctionedSeats!=0){
                    purchase = Operations.buyAndFinishPurchaseCase3(sAgencyManager, trip, (numberOfSeats-nonAuctionedSeats));
                }

            }
            
            /*LOGIN COM O USER, DE MODO A QUE SEJA POSSÍVEL CRIAR OS SEATS E A PURCHASE*/
            Operations.signinAsTestUser(sAgencyManager);
            
            double clientInitialMoney=user.getBalance();
            
            /*COMPRA DOS SEATS*/
            TPurchaseDTO purchases=null;
            if(auctionedSeats!=0){
                purchases=Operations.buyAndFinishPurchaseCase3(sAgencyManager, trip, auctionedSeats);
            }
            
            /*COMPRA EVENTUAL DE LUGARES LEILOADOS*/
            List<TSeatDTO> allAuctionedSeatsIWant = new ArrayList<TSeatDTO>();
            if (nonAuctionedSeats != 0) {
                List<TSeatDTO> listOfAuctionedSeats = sAgencyManager.findAllAuctionedSeats();
                if (listOfAuctionedSeats.isEmpty() == false) {
                    for (TSeatDTO t : listOfAuctionedSeats) {
                        t.setPrice(10.0);
                        allAuctionedSeatsIWant.add(t);
                    }
                }
                
                /*FINALIZACAO DO BID DAS SEATS*/
                boolean retBid;
                for(TSeatDTO t : allAuctionedSeatsIWant){
                    retBid=sAgencyManager.bidAuctionedSeat(t);
                } 
            }
            
            /*SIGIN OPERADOR*/
            Operations.signinAsAdmin(sAgencyManager);
            
            /*CHAMADA DO MÉTODO A TESTAR*/
            boolean resultCancelTrip=sAgencyManager.cancelTrip2(trip);
            
            /*VOLTAR A ATUALIZAR TRIP*/
            trip.setCanceled(false);
            sAgencyManager.editTrip(trip);
            trip=sAgencyManager.findTrip(trip.getId());
            //trip=Operations.createTrip(sAgencyManager, airlineTrip, fromPlace, toPlace, planeTrip, 50, 4000);
            
            /*VOLTAR A FAZER SIGIN COM O UTILIZADOR*/
            Operations.signinAsTestUser(sAgencyManager);
            
            /*OBTENCAO DA NOVA REFERENCIA DO UTILIZADOR*/
            user=Operations.getUser(sAgencyManager, user);
            
            if(resultCancelTrip==true && result==true && user.getBalance()==clientInitialMoney){
                limpaDados(purchase,purchases);
                assertTrue("",true);
            }
            else if(resultCancelTrip==false && result==false && user.getBalance()==clientInitialMoney){
                limpaDados(purchase, purchases);
                assertTrue("", true);
            }
            else{
                limpaDados(purchase, purchases);
                assertFalse("", true);
            }
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    public void limpaDados(TPurchaseDTO tp, TPurchaseDTO tp2){
        
        boolean ret;
        try{
            int jafoi=0;
            if(tp2!=null){
                //Operations.signinAsTestUser(sAgencyManager);
                sAgencyManager.removeSeatsOfActualPurchase(tp2, trip);
                sAgencyManager.removeActualPurchase(tp2);
            }
            if(tp!=null){
                Operations.signinAsTestUser(sAgencyManager, auxTuser);
                sAgencyManager.removeSeatsOfActualPurchase(tp, trip);
                sAgencyManager.removeActualPurchase(tp);
            }
            
            /*APAGAR DA BD OS AUCTIONED SEATS*/
            Operations.signinAsAdmin(sAgencyManager);
            ret=sAgencyManager.removeAuctionedSeatsUser(trip);
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    public static void logicOfTests() throws NoPermissionException{
        
        /*REPRESENTA TODA A LOGICA A SER INSTANCIADA ANTES DE DAR INICIO AOS TESTES*/
        
        /*OBTENCAO DA REFERENCIA PARA O OBJETO REMOTO*/
        sAgencyManager=Operations.initRemoteReferences(sAgencyManager);
        
        /*FAZER LOGIN COM O ADMIN--> PARA CRIAR OS OBJETOS*/
        Operations.signinAsAdmin(sAgencyManager);
        
        /*CRIACAO DE UMA PARTIDA E DE UM DESTINO--> VALORES POR DEFEITOS, INCLUIDOS NA DEFINICAO DO METODO*/
        fromPlace=Operations.createFromPlace(sAgencyManager);
        toPlace=Operations.createToPlace(sAgencyManager);
        
        /*CRIACAO DA COMPANHIA*/
        airlineTrip=Operations.createAirline(sAgencyManager);
        
        /*CRIACAO DO PLANE*/
        planeTrip=Operations.createPlane(sAgencyManager);/*LIMITE DO AVIAO SAO 10 LUGARES*/
        
        /*CRIACAO DA TRIP, TENDO EM CONTA O DESTINO, PARTIDA, COMPANHIA E O SEU AVIAO*/
        trip=Operations.createTrip(sAgencyManager, airlineTrip, fromPlace, toPlace, planeTrip, 50, 4000);/*PRECO DO BILHETE SAO 10, E A HORA DE PARTIDA É 4000*/
        
        /*CRIACAO DE UM UTILIZADOR*/
        user = Operations.createTestUser(sAgencyManager);
        sAgencyManager.acceptUser(user);
        
        /*LOGIN COM UTILIZADOR NORMAL*/
        Operations.signinAsTestUser(sAgencyManager);
        
        /*USER QUE ESTA LOGADO, DEPOSITAR DINHEIRO*/
        sAgencyManager.depositToAccount(50000);
        
        /*OBTENCAO DA REFERENCIA PARA O USER, JÁ COM O BALANCE ATUALIZADO*/
        user=Operations.getUser(sAgencyManager, user);
        
        /*SIGIN NOVAMENTE COM O ADMINISTRADOR*/
        Operations.signinAsAdmin(sAgencyManager);
        
        /*CRIACAO DE UM USER AUXILIAR*/
        auxTuser=Operations.createUser(sAgencyManager, "Joao", "joao", true);
        sAgencyManager.acceptUser(auxTuser);
        
        /*LOGIN COM UTILIZADOR NORMAL*/
        Operations.signinAsTestUser(sAgencyManager,auxTuser);
        
        /*USER QUE ESTA LOGADO, DEPOSITAR DINHEIRO*/
        sAgencyManager.depositToAccount(5000);
        
        /*ATUALIZACAO DA REFERENCIA DO OBJETO, JA ATUALIZA COM O SEU BALANCE*/
        auxTuser=Operations.getUser(sAgencyManager, auxTuser);
        
        /*VOLTAR NOVAMENTE A EFETUAR LOGIN COM O ADMINISTRADOR*/
        //Operations.signinAsAdmin(sAgencyManager);
        
    }
            
}