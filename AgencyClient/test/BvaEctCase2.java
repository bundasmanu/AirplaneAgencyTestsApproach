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
    
    int horaAtual;
    
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
        this.horaAtual=hourLeft;
        this.trip.setDatetrip(200);
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
            {"Porto", "Madrid", 200, 6 , FeedbackResult.InvalidFeedback}, /*FALHA 20--> O SISTEMA DEIXA COMPRAR BILHETE, MSM DPS DE ADQUIRIR DA VIAGEM TER TERMINADO*/
            {"Madrid", "Porto", 200, 7 , FeedbackResult.InvalidFeedback}, /*FALHA 21--> O SISTEMA DEIXA COMPRAR BILHETE, MSM DPS DE ADQUIRIR DA VIAGEM TER TERMINADO*/
            {"Madrid", "Madrid", 200, 9 , FeedbackResult.InvalidFeedback}, /*FALHA 22--> DEIXA COMPRAR BILHETES COM O MSM DESTINO E PARTIDA*/
            {"Porto", "Porto", 50 , 5 , FeedbackResult.InvalidFeedback}, /*FALHA 23--> DEIXA COMPRAR BILHETES COM O MSM DESTINO E PARTIDA*/
            {"Porto", "Madrid", 50, 6 , FeedbackResult.ValidFeedback},
            {"Madrid", "Porto", 50, 8 , FeedbackResult.ValidFeedback},
            {"Madrid", "Madrid", 50, 9 , FeedbackResult.InvalidFeedback}, /*FALHA 26--> DEIXA COMPRAR BILHETES COM O MSM DESTINO E PARTIDA*/
//            /*WEAK ROBUST*/
            {"Moscovo", "Porto", 200 , 10 , FeedbackResult.InvalidFeedback}, /*DA BEM POR O LIMITE DE COMPRA É SUPERIOR, PORQUE O TEMPO ESTA A VALIDAR MAL*/
            {"Porto", "Moscovo", 50 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 28--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE*/
//            {"Madrid", "Porto", 50, -10 , FeedbackResult.InvalidFeedback},/*TESTE ESTUPIDO DE SER REALIZADO--> COMPRAR BILHETES NEGATIVOS??*/
            {"Porto", "Madrid", 200, 11 , FeedbackResult.InvalidFeedback}, /*ESTA A VALIDAR BEM, CONTUDO VALIDA BEM PORQUE O SE TENTA COMPRAR MAIS BILHETES DO QUE O PERMITIDO, CONTUDO NÃO ESTA A SER BEM VALIDADO O FACTO DA VIAGEM ESTAR JÁ CONCLUÍDA*/
            /*STRONG ROBUST*/
            {"Moscovo", "Porto", 200 , 10 , FeedbackResult.InvalidFeedback}, /*ESTA A TESTAR BEM O FACTO DE TENTAR COMPRAR MAIS BILHETES DO QUE OS PERMITIDOS (DAÍ O TESTE) ESTAR A FAZER BEM, MAS TEM O PROBLEMA DE NAO VALIDAR CIDADES COM MSM DESTINO E O FACTO DA VIAGEM JÁ TER TERMINADO, NAO DEVERIA ACEITAR MAIS COMPRAS*/
            {"Moscovo", "Porto", 200 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 31--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE, PARA ALEM DISSO A VIAGEM JÁ FOI CONCLUIDA, NAO DEVIA DEIXAR COMPRAR MAIS BILHETES*/
            {"Moscovo", "Porto", 200 , 5 , FeedbackResult.InvalidFeedback}, /*FALHA 32--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE, PARA ALEM DISSO A VIAGEM JÁ FOI CONCLUIDA, NAO DEVIA DEIXAR COMPRAR MAIS BILHETES*/
            {"Moscovo", "Madrid", 200 , 10 , FeedbackResult.InvalidFeedback}, /*ESTA A TESTAR BEM O FACTO DE TENTAR COMPRAR MAIS BILHETES DO QUE OS PERMITIDOS (DAÍ O TESTE) ESTAR A FAZER BEM, MAS TEM O PROBLEMA DE NAO VALIDAR CIDADES COM MSM DESTINO E O FACTO DA VIAGEM JÁ TER TERMINADO, NAO DEVERIA ACEITAR MAIS COMPRAS*/
            {"Moscovo", "Madrid", 200 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 34--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE, PARA ALEM DISSO A VIAGEM JÁ FOI CONCLUIDA, NAO DEVIA DEIXAR COMPRAR MAIS BILHETES*/
            {"Moscovo", "Madrid", 200 , 5 , FeedbackResult.InvalidFeedback}, /*FALHA 35--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE, PARA ALEM DISSO A VIAGEM JÁ FOI CONCLUIDA, NAO DEVIA DEIXAR COMPRAR MAIS BILHETES*/
            {"Moscovo", "Porto", 50 , 10 , FeedbackResult.InvalidFeedback}, /*ESTA A TESTAR BEM O FACTO DE TENTAR COMPRAR MAIS BILHETES DO QUE OS PERMITIDOS (DAÍ O TESTE) ESTAR A FAZER BEM, MAS TEM O PROBLEMA DE NAO VALIDAR CIDADES COM MSM DESTINO, NAO DEVERIA ACEITAR MAIS COMPRAS*/
            {"Moscovo", "Porto", 50 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 37--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE*/
            {"Moscovo", "Porto", 50 , 5 , FeedbackResult.InvalidFeedback}, /*FALHA 38--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE*/
            {"Moscovo", "Madrid", 50 , 10 , FeedbackResult.InvalidFeedback}, /*ESTA A TESTAR BEM O FACTO DE TENTAR COMPRAR MAIS BILHETES DO QUE OS PERMITIDOS (DAÍ O TESTE) ESTAR A FAZER BEM, MAS TEM O PROBLEMA DE NAO VALIDAR CIDADES COM MSM DESTINO, NAO DEVERIA ACEITAR MAIS COMPRAS*/
            {"Moscovo", "Madrid", 50 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 40--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE*/
            {"Moscovo", "Madrid", 50 , 5 , FeedbackResult.InvalidFeedback}, /*FALHA 41--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE*/
            {"Porto", "Moscovo", 200 , 10 , FeedbackResult.InvalidFeedback}, /*ESTA A TESTAR BEM O FACTO DE TENTAR COMPRAR MAIS BILHETES DO QUE OS PERMITIDOS (DAÍ O TESTE) ESTAR A FAZER BEM, MAS TEM O PROBLEMA DE NAO VALIDAR CIDADES COM MSM DESTINO E O FACTO DA VIAGEM JÁ TER TERMINADO, NAO DEVERIA ACEITAR MAIS COMPRAS*/
            {"Porto", "Moscovo", 200 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 43--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE, PARA ALEM DISSO A VIAGEM JÁ FOI CONCLUIDA, NAO DEVIA DEIXAR COMPRAR MAIS BILHETES*/
            {"Porto", "Moscovo", 200 , 5 , FeedbackResult.InvalidFeedback}, /*FALHA 44--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE, PARA ALEM DISSO A VIAGEM JÁ FOI CONCLUIDA, NAO DEVIA DEIXAR COMPRAR MAIS BILHETES*/
            {"Madrid", "Moscovo", 200 , 10 , FeedbackResult.InvalidFeedback}, /*ESTA A TESTAR BEM O FACTO DE TENTAR COMPRAR MAIS BILHETES DO QUE OS PERMITIDOS (DAÍ O TESTE) ESTAR A FAZER BEM, MAS TEM O PROBLEMA DE NAO VALIDAR CIDADES COM MSM DESTINO E O FACTO DA VIAGEM JÁ TER TERMINADO, NAO DEVERIA ACEITAR MAIS COMPRAS*/
            {"Madrid", "Moscovo", 200 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 46--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE, PARA ALEM DISSO A VIAGEM JÁ FOI CONCLUIDA, NAO DEVIA DEIXAR COMPRAR MAIS BILHETES*/
            {"Madrid", "Moscovo", 200 , 5 , FeedbackResult.InvalidFeedback}, /*FALHA 47--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE, PARA ALEM DISSO A VIAGEM JÁ FOI CONCLUIDA, NAO DEVIA DEIXAR COMPRAR MAIS BILHETES*/
            {"Porto", "Moscovo", 50 , 10 , FeedbackResult.InvalidFeedback}, /*ESTA A TESTAR BEM O FACTO DE TENTAR COMPRAR MAIS BILHETES DO QUE OS PERMITIDOS (DAÍ O TESTE) ESTAR A FAZER BEM, MAS TEM O PROBLEMA DE NAO VALIDAR CIDADES COM MSM DESTINO, NAO DEVERIA ACEITAR MAIS COMPRAS*/
            {"Porto", "Moscovo", 50 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 49--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE*/
            {"Porto", "Moscovo", 50 , 5 , FeedbackResult.InvalidFeedback}, /*FALHA 50--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE*/
            {"Madrid", "Moscovo", 50 , 10 , FeedbackResult.InvalidFeedback}, /*ESTA A TESTAR BEM O FACTO DE TENTAR COMPRAR MAIS BILHETES DO QUE OS PERMITIDOS (DAÍ O TESTE) ESTAR A FAZER BEM, MAS TEM O PROBLEMA DE NAO VALIDAR CIDADES COM MSM DESTINO, NAO DEVERIA ACEITAR MAIS COMPRAS*/
            {"Madrid", "Moscovo", 50 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 52--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE*/
            {"Madrid", "Moscovo", 50 , 5 , FeedbackResult.InvalidFeedback}, /*FALHA 53--> TESTE IMPOSSIVEL DE TESTAR CÓDIGO--> NAO É POSSÍVEL TESTAR A COMPRA DE UMA VIAGEM COM UMA CIDADE QUE NAO EXISTE*/
//            {"Porto", "Porto", 200 , -10 , FeedbackResult.InvalidFeedback}, /*TESTES IMPOSSIVEIS--> IMPOSSIVEL VERIFICAR TER UM AVIAO COM SEATS NEGATIVOS, ALEM DE QUE O DESTINO E PARTIDA SAO OS MESMOS E TAMBEM JA PARTIU NAO DA PARA COMPRAR MAIS*/
//            {"Porto", "Madrid", 200 , -10 , FeedbackResult.InvalidFeedback}, /*TESTES IMPOSSIVEIS--> IMPOSSIVEL VERIFICAR TER UM AVIAO COM SEATS NEGATIVOS E TAMBEM JA PARTIU NAO DA PARA COMPRAR MAIS*/
//            {"Madrid", "Porto", 200 , -10 , FeedbackResult.InvalidFeedback}, /*TESTES IMPOSSIVEIS--> IMPOSSIVEL VERIFICAR TER UM AVIAO COM SEATS NEGATIVOS E TAMBEM JA PARTIU NAO DA PARA COMPRAR MAIS*/
//            {"Madrid", "Madrid", 200 , -10 , FeedbackResult.InvalidFeedback}, /*TESTES IMPOSSIVEIS--> IMPOSSIVEL VERIFICAR TER UM AVIAO COM SEATS NEGATIVOS, ALEM DE QUE O DESTINO E PARTIDA SAO OS MESMOS E TAMBEM JA PARTIU NAO DA PARA COMPRAR MAIS*/
//            {"Porto", "Porto", 50 , -10 , FeedbackResult.InvalidFeedback}, /*TESTES IMPOSSIVEIS--> IMPOSSIVEL VERIFICAR TER UM AVIAO COM SEATS NEGATIVOS, ALEM DE QUE O DESTINO E PARTIDA SAO OS MESMOS*/
//            {"Porto", "Madrid", 50 , -10 , FeedbackResult.InvalidFeedback}, /*TESTES IMPOSSIVEIS--> IMPOSSIVEL VERIFICAR TER UM AVIAO COM SEATS NEGATIVOS*/
//            {"Madrid", "Porto", 50 , -10 , FeedbackResult.InvalidFeedback}, /*TESTES IMPOSSIVEIS--> IMPOSSIVEL VERIFICAR TER UM AVIAO COM SEATS NEGATIVOS */
//            {"Madrid", "Madrid", 50 , -10 , FeedbackResult.InvalidFeedback}, /*TESTES IMPOSSIVEIS--> IMPOSSIVEL VERIFICAR TER UM AVIAO COM SEATS NEGATIVOS, ALEM DE QUE O DESTINO E PARTIDA SAO OS MESMOS*/
            {"Porto", "Porto", 200 , 20 , FeedbackResult.InvalidFeedback}, /*ESTA A FAZER BEM O TESTE, MAS SO FAZ BEM POR CAUSA DA TENTATIVA DE COMPRAR MAIS BILHETES QUE O ESPERADO (O SISTEMA FAZ ISSO BEM), NAO VERIFICA SE SAO AS MSM CIDADE PARTIDA E DESTINO, NEM SE A VIAGEM JA ESTA CONCLUIDA*/
            {"Porto", "Madrid", 200 , 11 , FeedbackResult.InvalidFeedback}, /*ESTA A FAZER BEM O TESTE, MAS SO FAZ BEM POR CAUSA DA TENTATIVA DE COMPRAR MAIS BILHETES QUE O ESPERADO (O SISTEMA FAZ ISSO BEM), NAO VERIFICA SE A VIAGEM JA ESTA CONCLUIDA*/
            {"Madrid", "Porto", 200 , 11 , FeedbackResult.InvalidFeedback}, /*ESTA A FAZER BEM O TESTE, MAS SO FAZ BEM POR CAUSA DA TENTATIVA DE COMPRAR MAIS BILHETES QUE O ESPERADO (O SISTEMA FAZ ISSO BEM), NAO VERIFICA SE A VIAGEM JA ESTA CONCLUIDA*/
            {"Madrid", "Madrid", 200 , 12 , FeedbackResult.InvalidFeedback}, /*ESTA A FAZER BEM O TESTE, MAS SO FAZ BEM POR CAUSA DA TENTATIVA DE COMPRAR MAIS BILHETES QUE O ESPERADO (O SISTEMA FAZ ISSO BEM), NAO VERIFICA SE SAO AS MSM CIDADE PARTIDA E DESTINO, NEM SE A VIAGEM JA ESTA CONCLUIDA*/
            {"Porto", "Porto", 50 , 12 , FeedbackResult.InvalidFeedback}, /*ESTA A FAZER BEM O TESTE, MAS SO FAZ BEM POR CAUSA DA TENTATIVA DE COMPRAR MAIS BILHETES QUE O ESPERADO (O SISTEMA FAZ ISSO BEM), NAO VERIFICA SE SAO AS MSM CIDADE PARTIDA E DESTINO*/
            {"Porto", "Madrid", 50 , 11 , FeedbackResult.InvalidFeedback}, 
            {"Madrid", "Porto", 50 , 11 , FeedbackResult.InvalidFeedback}, 
            {"Madrid", "Madrid", 50 , 15 , FeedbackResult.InvalidFeedback}, /*ESTA A FAZER BEM O TESTE, MAS SO FAZ BEM POR CAUSA DA TENTATIVA DE COMPRAR MAIS BILHETES QUE O ESPERADO (O SISTEMA FAZ ISSO BEM), NAO VERIFICA SE SAO AS MSM CIDADE PARTIDA E DESTINO*/
            /*WEAK NORMAL HIBRIDO*/
            /*7 É A MEDIA ENTRE O Nº DE LUGARES VAGOS (5) E A LOTACAO DO AVIAO (10), E PARTE AOS 200*/
            {"Porto", "Madrid", 0, 7 , FeedbackResult.ValidFeedback}, /*NAO DEVE FALHAR*/
            {"Porto", "Madrid", 1, 7 , FeedbackResult.ValidFeedback}, /*NAO DEVE FALHAR*/
            {"Porto", "Madrid", 100, 7 , FeedbackResult.ValidFeedback}, /*NAO DEVE FALHAR*/
            {"Porto", "Madrid", 198, 7 , FeedbackResult.ValidFeedback}, /*NAO DEVE FALHAR*/
            {"Porto", "Madrid", 199, 7 , FeedbackResult.ValidFeedback}, /*NAO DEVE FALHAR*/
            {"Porto", "Madrid", 100, 5 , FeedbackResult.ValidFeedback}, /*NAO DEVE FALHAR*/
            {"Porto", "Madrid", 100, 6 , FeedbackResult.ValidFeedback}, /*NAO DEVE FALHAR*/
            {"Porto", "Madrid", 100, 8 , FeedbackResult.ValidFeedback}, /*NAO DEVE FALHAR*/
            {"Porto", "Madrid", 100, 9 , FeedbackResult.ValidFeedback}, /*NAO DEVE FALHAR*/
            {"Madrid", "Porto", 200 , 10 , FeedbackResult.InvalidFeedback}, /*DEVE FALHAR--> FALHA PORQUE O Nº DE LUGARES JA ESTA COMPLETAMENTE CHEIO, CASO ISSO NAO ACONTECE O TESTE NAO FAZIA O QUE SE PRETENDIA, VISTO QUE O SISTEMA NÃO ESTA A VALIDAR A HORA*/
            {"Madrid", "Porto", 201 , 10 , FeedbackResult.InvalidFeedback}, /*DEVE FALHAR--> FALHA PORQUE O Nº DE LUGARES JA ESTA COMPLETAMENTE CHEIO, CASO ISSO NAO ACONTECE O TESTE NAO FAZIA O QUE SE PRETENDIA, VISTO QUE O SISTEMA NÃO ESTA A VALIDAR A HORA*/
            {"Madrid", "Porto", (Integer.MAX_VALUE+200)/2 , 10 , FeedbackResult.InvalidFeedback}, /*DEVE FALHAR--> FALHA PORQUE O Nº DE LUGARES JA ESTA COMPLETAMENTE CHEIO, CASO ISSO NAO ACONTECE O TESTE NAO FAZIA O QUE SE PRETENDIA, VISTO QUE O SISTEMA NÃO ESTA A VALIDAR A HORA*/
            {"Madrid", "Porto", Integer.MAX_VALUE-2 , 10 , FeedbackResult.InvalidFeedback}, /*DEVE FALHAR--> FALHA PORQUE O Nº DE LUGARES JA ESTA COMPLETAMENTE CHEIO, CASO ISSO NAO ACONTECE O TESTE NAO FAZIA O QUE SE PRETENDIA, VISTO QUE O SISTEMA NÃO ESTA A VALIDAR A HORA*/
            {"Madrid", "Porto", Integer.MAX_VALUE-1, 10 , FeedbackResult.InvalidFeedback}, /*DEVE FALHAR--> FALHA PORQUE O Nº DE LUGARES JA ESTA COMPLETAMENTE CHEIO, CASO ISSO NAO ACONTECE O TESTE NAO FAZIA O QUE SE PRETENDIA, VISTO QUE O SISTEMA NÃO ESTA A VALIDAR A HORA*/
            {"Madrid", "Porto", 0 , 0 , FeedbackResult.ValidFeedback}, /*NAO DEVE FALHAR*/
            {"Madrid", "Porto", 1 , 0 , FeedbackResult.ValidFeedback}, /*NAO DEVE FALHAR*/
            {"Madrid", "Porto", 100 , 0 , FeedbackResult.ValidFeedback}, /*NAO DEVE FALHAR*/
            {"Madrid", "Porto", 198, 0 , FeedbackResult.ValidFeedback}, /*NAO DEVE FALHAR*/
            {"Madrid", "Porto", 199 , 0 , FeedbackResult.ValidFeedback}, /*NAO DEVE FALHAR*/
            /*WEAK ROBUST HIBRIDO*/
            {"Moscovo", "Porto", 200 , 10 , FeedbackResult.InvalidFeedback}, /*DEVE FALHAR, MAS SO FALHA POR CAUSA DE JÁ TER TODOS OS LUGARES CHEIOS, A DATA ESTA MAL VALIDADA*/
            {"Moscovo", "Porto", 201 , 10 , FeedbackResult.InvalidFeedback}, /*DEVE FALHAR, MAS SO FALHA POR CAUSA DE JÁ TER TODOS OS LUGARES CHEIOS, A DATA ESTA MAL VALIDADA*/
            {"Moscovo", "Porto", (Integer.MAX_VALUE+200)/2 , 10 , FeedbackResult.InvalidFeedback}, /*DEVE FALHAR, MAS SO FALHA POR CAUSA DE JÁ TER TODOS OS LUGARES CHEIOS, A DATA ESTA MAL VALIDADA*/
            {"Moscovo", "Porto", Integer.MAX_VALUE-2 , 10 , FeedbackResult.InvalidFeedback}, /*DEVE FALHAR, MAS SO FALHA POR CAUSA DE JÁ TER TODOS OS LUGARES CHEIOS, A DATA ESTA MAL VALIDADA*/
            {"Moscovo", "Porto", Integer.MAX_VALUE-1 , 10 , FeedbackResult.InvalidFeedback}, /*DEVE FALHAR, MAS SO FALHA POR CAUSA DE JÁ TER TODOS OS LUGARES CHEIOS, A DATA ESTA MAL VALIDADA*/
            {"Porto", "Moscovo", 0 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 86--> DEVIA SER INVALIDO, MAS É IMPOSSÍVEL TESTAR CIDADES QUE NAO EXISTAM LOGO, O TESTE VAI FALHAR, VISTO QUE DEIXA INSERIR BILHETES*/
            {"Porto", "Moscovo", 1 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 87--> DEVIA SER INVALIDO, MAS É IMPOSSÍVEL TESTAR CIDADES QUE NAO EXISTAM LOGO, O TESTE VAI FALHAR, VISTO QUE DEIXA INSERIR BILHETES*/
            {"Porto", "Moscovo", 100 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 88--> DEVIA SER INVALIDO, MAS É IMPOSSÍVEL TESTAR CIDADES QUE NAO EXISTAM LOGO, O TESTE VAI FALHAR, VISTO QUE DEIXA INSERIR BILHETES*/
            {"Porto", "Moscovo", 198 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 89--> DEVIA SER INVALIDO, MAS É IMPOSSÍVEL TESTAR CIDADES QUE NAO EXISTAM LOGO, O TESTE VAI FALHAR, VISTO QUE DEIXA INSERIR BILHETES*/
            {"Porto", "Moscovo", 199 , 0 , FeedbackResult.InvalidFeedback}, /*FALHA 90--> DEVIA SER INVALIDO, MAS É IMPOSSÍVEL TESTAR CIDADES QUE NAO EXISTAM LOGO, O TESTE VAI FALHAR, VISTO QUE DEIXA INSERIR BILHETES*/
//            {"Madrid", "Porto", 50 , Integer.MIN_VALUE , FeedbackResult.InvalidFeedback}, /*IMPOSSIVEL TESTAR LUGARES NEGATIVOS*/
//            {"Madrid", "Porto", 50 , Integer.MIN_VALUE+1 , FeedbackResult.InvalidFeedback}, /*IMPOSSIVEL TESTAR LUGARES NEGATIVOS*/
//            {"Madrid", "Porto", 50 , Integer.MIN_VALUE/2 , FeedbackResult.InvalidFeedback}, /*IMPOSSIVEL TESTAR LUGARES NEGATIVOS*/
//            {"Madrid", "Porto", 50 , -2 , FeedbackResult.InvalidFeedback}, /*IMPOSSIVEL TESTAR LUGARES NEGATIVOS*/
//            {"Madrid", "Porto", 50 , -1 , FeedbackResult.InvalidFeedback}, /*IMPOSSIVEL TESTAR LUGARES NEGATIVOS*/
            {"Porto", "Madrid", 200 , 6 , FeedbackResult.InvalidFeedback}, /*FALHA 91--> TESTE VAI FALHAR, PORQUE A VALIDACAO DO TEMPO NAO ESTA FEITA*/
            {"Porto", "Madrid", 200 , 7 , FeedbackResult.InvalidFeedback}, /*FALHA 92--> TESTE VAI FALHAR, PORQUE A VALIDACAO DO TEMPO NAO ESTA FEITA*/
            /*3 ULTIMOS IMPOSSIVEIS DE TESTAR PORQUE IA SER CRIADO UM ARRAY COM MILHARES DE ELEMENTOS, IA ESTOIRAR TUDO*/
//            {"Porto", "Madrid", 200 , (Integer.MAX_VALUE+5)/2 , FeedbackResult.InvalidFeedback}, /*TESTE NAO FALHA, APENAS POR CAUSA DO Nº DE LUGARES SER SUPERIOR AOS PERMITIDOS, CONTUDO A VALIDACAO DO TEMPO NAO ESTA FEITA*/
////            {"Porto", "Madrid", 200 , Integer.MAX_VALUE-1 , FeedbackResult.InvalidFeedback}, /*TESTE NAO FALHA, APENAS POR CAUSA DO Nº DE LUGARES SER SUPERIOR AOS PERMITIDOS, CONTUDO A VALIDACAO DO TEMPO NAO ESTA FEITA*/
////            {"Porto", "Madrid", 200 , Integer.MAX_VALUE-2 , FeedbackResult.InvalidFeedback}, /*TESTE NAO FALHA, APENAS POR CAUSA DO Nº DE LUGARES SER SUPERIOR AOS PERMITIDOS, CONTUDO A VALIDACAO DO TEMPO NAO ESTA FEITA*/
        });
    }
    
    @Test
    public void testCase2(){
        
        try{
            
           /*LOGIN COMO OPERADOR--> COLOCAR NA BD OS PARAMETROS ATUALIZADOS, PASSADOS NO CONSTRUTOR*/
           Operations.signinAsAdmin(sAgencyManager);
           sAgencyManager.setDate(this.horaAtual);
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

            TPurchaseDTO newPurchase = null;
            if (tpurchase != null || limitNumberTrip == 0) {

                /*TENTATIVA DE FAZER MAIS UMA COMPRA*/
                Operations.signinAsTestUser(sAgencyManager);

                newPurchase = Operations.buyAndFinishPurchaseCase2(sAgencyManager, trip, 1);
            }
           
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
            assertFalse("", true);
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
