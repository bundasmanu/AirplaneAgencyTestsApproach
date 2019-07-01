package agencyclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import logic.AgencyManagerRemote;
import logic.Config;
import logic.Log;
import logic.NoPermissionException;
import logic.SignInValue;
import logic.TAirlineDTO;
import logic.TPlaceDTO;
import logic.TPlaceFeedbackDTO;
import logic.TPlaneDTO;
import logic.TPurchaseDTO;
import logic.TSeatDTO;
import logic.TTripDTO;
import logic.TTripFeedbackDTO;
import logic.TUserDTO;
import logic.GuestAgencyManagerRemote;

public class Main {
    
    private static AgencyManagerRemote sAgencyManager;
    private static GuestAgencyManagerRemote sGuestAgencyManager;
    
    public static void main(String[] args) {
        initRemoteReferences();
        
        doListenCommands();
    }
    
    private static void doListenCommands() {
        Scanner sc = new Scanner(System.in);
        boolean listenCommand = true;

        while (listenCommand) {
            try{
                System.out.println("Insert command:");
                System.out.print("> ");
                
                String input = sc.nextLine();
                String command = input.split(" ")[0];
                
                switch (command) {
                    case Command.SIGNIN:
                        processSignIn();
                        break;
                    case Command.SIGNUP:
                        processSignUp();
                        break;
                    case Command.LOGOUT:
                        processLogout();
                        break;
                    case Command.DEPOSITTOACCOUNT:
                        processDepositToAccount();
                        break;
                    case Command.FINDALLUSERS:
                        processUsersFindAll();
                        break;
                    case Command.HELP:
                        printCommandList();
                        break;
                    case Command.CLS:
                        processCLS();
                        break;
                    case Command.EXIT:
                        listenCommand = false;
                        break;
                    case Command.ACCEPTUSER:
                        processAcceptUser();
                        break;
                    case Command.FINDALLPLANES:
                        processPlanesFindAll();
                        break;
                    case Command.ADDPLANE:
                        processAddPlane();
                        break;
                    case Command.EDITPLANE:
                        processEditPlane();
                        break;
                    case Command.REMOVEPLANE:
                        processRemovePlane();
                        break;
                    case Command.FINDALLAIRLINES:
                        processAirlinesFindAll();
                        break;
                    case Command.ADDAIRLINE:
                        processAddAirline();
                        break;
                    case Command.EDITAIRLINE:
                        processEditAirline();
                        break;
                    case Command.REMOVEAIRLINE:
                        processRemoveAirline();
                        break;
                    case Command.FINDALLPLACES:
                        processPlacesFindAll();
                        break;
                    case Command.ADDPLACE:
                        processAddPlace();
                        break;
                    case Command.EDITPLACE:
                        processEditPlace();
                        break;
                    case Command.REMOVEPLACE:
                        processRemovePlace();
                        break;
                    case Command.ADDPLACEFEEDBACK:
                        processAddPlaceFeedback();
                        break;
                    case Command.EDITPLACEFEEDBACK:
                        processEditPlaceFeedback();
                        break;
                    case Command.REMOVEPLACEFEEDBACK:
                        processRemovePlaceFeedback();
                        break;
                    case Command.FINDALLTRIPS:
                        processTripsFindAll();
                        break;
                    case Command.ADDTRIP:
                        processAddTrip();
                        break;
                    case Command.EDITTRIP:
                        processEditTrip();
                        break;
                    case Command.REMOVETRIP:
                        processRemoveTrip();
                        break;
                    case Command.CANCELTRIP:
                        processCancelTrip();
                        break;
                    case Command.SETTRIPDONE:
                        processSetDoneTrip();
                        break;
                    case Command.ADDTRIPFEEDBACK:
                        processAddTripFeedback();
                        break;
                    case Command.EDITTRIPFEEDBACK:
                        processEditTripFeedback();
                        break;
                    case Command.REMOVETRIPFEEDBACK:
                        processRemoveTripFeedback();
                        break;
                    case Command.SETACTUALDATE:
                        processSetActualDate();
                        break;
                    case Command.GETACTUALDATE:
                        processGetActualDate();
                        break;
                    case Command.SETDURATIONTIMER:
                        processSetDurationTimer();
                        break;
                    case Command.GETTIMERINFORMATION:
                        processGetTimerInformation();
                        break;
                    case Command.GET_LOGS:
                        processGetLogs(input);
                        break;
                    case Command.REMOVE_LOGS:
                        processRemoveLogs();
                        break;       
                    case Command.FINDALLPURCHASES:
                        processFindAllPurchases();
                        break;
                    case Command.FINDALLMYPURCHASES:
                        processFindAllMyPurchases();
                        break;
                    case Command.FINDACTUALPURCHASE:
                        processFindActualPurchase();
                        break;
                    case Command.BUYSEATSTOTRIP:
                        processBuySeatsToTrip();
                        break;
                    case Command.EDITACTUALPURCHASE:
                        processEditActualPurchase();
                        break;
                    case Command.REMOVESEATSOFACTUALPURCHASE:
                        processRemoveSeatsOfActualPurchase();
                        break;
                    case Command.REMOVEACTUALPURCHASE:
                        processRemoveActualPurchase();
                        break;
                    case Command.REMOVEDONEPURCHASE:
                        processRemoveDonePurchase();
                        break;
                    case Command.FINISHACTUALPURCHASE:
                        processFinishActualPurchase();
                        break;
                    case Command.FINDAUCTIONEDSEATS:
                        processFindAllAuctionedSeats();
                        break;
                    case Command.BIDAUCTIONEDSEAT:
                        processBidActionedSeat();
                        break;
                    case Command.FINDMYBIDS:
                        processGetMyBids();
                        break;
                    case Command.GET_CHEAP_TRIPS:
                        processGetCheapTrips();
                        break;
                    default:
                        System.out.println("Command not found. Type help to get a command list.");
                        break;
                }
                
                System.out.println("");
                
            } catch (NoPermissionException e) { 
                System.err.println("Error - " + e);
            } catch(Exception ex){
                System.err.println("Error - " + ex);
            }
        }
    }

    private static void processSignIn(){
        Scanner sc = new Scanner(System.in);
        String username, password;
        
        System.out.println("Username:");
        username = sc.nextLine();
        System.out.println("Password:");
        password = sc.nextLine();

        SignInValue result = sAgencyManager.signIn(username, password);
        
        String resultStr = Utils.getSignUpValueString(result, username);
        System.out.println(resultStr);
    }
    
    private static void processSignUp(){
        Scanner sc = new Scanner(System.in);
        TUserDTO userDTO = new TUserDTO();
        boolean result;
        
        do{
            System.out.println("Type of the User:");
            System.out.println("0- Operator");
            System.out.println("1- Client");
            System.out.print("Type: ");
            userDTO.setUsertype(Integer.parseInt(sc.nextLine()));
        }while(userDTO.getUsertype() != Config.CLIENT && userDTO.getUsertype() != Config.OPERATOR);
        
        System.out.println("Username:");
        userDTO.setUsername(sc.nextLine());
        System.out.println("Password:");
        userDTO.setPassword(sc.nextLine());

        if(userDTO.getUsertype()==Config.CLIENT)
        {
            System.out.println("Name:");
            userDTO.setClientName(sc.nextLine());
        }
        
        result = sAgencyManager.signUp(userDTO);
        if(!result)
            System.out.println("Username already exists or passwords are different.");
        else
            System.out.println("Sign up with sucess! Now you can sign in, after operator approval " + userDTO.getUsername() + ".");
    }
    
    private static void processLogout(){
        boolean result;

        result = sAgencyManager.logout();
        if(!result)
            System.out.println("A problem occurred. The system didn't finish de task.");
        else
            System.out.println("You have done logout with success!");
    }
            
    private static void processAcceptUser() {
        Scanner sc = new Scanner(System.in);
        TUserDTO userDTO;
        String username;
        boolean result;
        int op;
        
        System.out.println("Username:");
        username = sc.nextLine();
        
        userDTO = sAgencyManager.getTUserDTO(username);
                
        if(userDTO == null)
        {
            System.out.println("Not found a user with that name. Try again.");
            return;
        }
        
        System.out.println("Do you want accept the user " + userDTO + " ? [1/0]");
        op = Integer.parseInt(sc.nextLine());
        
        if(op==0)
            return;
        
        result = sAgencyManager.acceptUser(userDTO);
        
        if(!result)
            System.out.println("An error has occurred..");
        else
            System.out.println("The user has been accepted with success.");

        
    }
    
    private static void processDepositToAccount() {
        Scanner sc = new Scanner(System.in);
        float amount;
        boolean result;
        
        System.out.println("Amount:");
        amount = Float.parseFloat(sc.nextLine());
        
        result = sAgencyManager.depositToAccount(amount);
        
        if(!result)
            System.out.println("An error has occurred.");
        else
            System.out.println("The amount has been accepted with success.");

    }
    private static void processUsersFindAll(){
        System.out.println("All users in the system:");
        
        for(TUserDTO userDTO : sAgencyManager.findAllUsers())
            System.out.println(userDTO);
    }
 
    //-------------------------------------------------------
    //planes
    private static void processPlanesFindAll() throws NoPermissionException{
        
        System.out.println("All planes in the system:");
        
        for(TPlaneDTO planeDTO : sAgencyManager.findAllPlanes())
            System.out.println(planeDTO);
    }
    
    private static void processAddPlane() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TPlaneDTO planeDTO = new TPlaneDTO();
        boolean result;
        
        System.out.println("Plane Name:");
        planeDTO.setPlaneName(sc.nextLine());
        System.out.println("Plane Limit:");
        planeDTO.setPlaneLimit(Integer.parseInt(sc.nextLine()));
        
        result = sAgencyManager.addPlane(planeDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't add the plane.");
        else
            System.out.println("Plane added with sucess!");
    }
    
    private static void processEditPlane() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TPlaneDTO planeDTO;
        boolean result;
        int id;
        

        System.out.println("Plane Id:");
        id = Integer.parseInt(sc.nextLine());
        planeDTO = sAgencyManager.findPlane(id);

        if(planeDTO == null)
        {
            System.out.println("Not found a plane with that id. Try again.");
            return;
        }

        System.out.println("Plane: " + planeDTO);

        System.out.println("New plane Name:");
        planeDTO.setPlaneName(sc.nextLine());
        System.out.println("New plane Limit:");
        planeDTO.setPlaneLimit(Integer.parseInt(sc.nextLine()));

        result = sAgencyManager.editPlane(planeDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't edit the plane.");
        else
            System.out.println("Plane edited with success!");
    }
    
    private static void processRemovePlane() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TPlaneDTO planeDTO;
        boolean result;
        int id;
        int op;
        
        
        System.out.println("Plane Id:");
        id = Integer.parseInt(sc.nextLine());
        planeDTO = sAgencyManager.findPlane(id);

        if(planeDTO == null)
        {
            System.out.println("Not found a plane with that id. Try again.");
            return;
        }


        System.out.println("Do you want remove the plane: " + planeDTO + "? [1/0]");
        op = Integer.parseInt(sc.nextLine());

        if(op == 0)
            return;

        result = sAgencyManager.removePlane(planeDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't remove the plane.");
        else
            System.out.println("Plane removed with success!");
    }
    
    //----------------------------------------------------
    //airlines
    private static void processAirlinesFindAll() throws NoPermissionException{
        
        System.out.println("All airlines in the system:");
        
        for(TAirlineDTO airlineDTO : sAgencyManager.findAllAirlines())
            System.out.println(airlineDTO);
    }
    
    private static void processAddAirline() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TAirlineDTO airlineDTO = new TAirlineDTO();
        boolean result;
        
        System.out.println("Airline Name:");
        airlineDTO.setAirlineName(sc.nextLine());
        System.out.println("Phone Number:");
        airlineDTO.setPhoneNumber(sc.nextLine());
        
        result = sAgencyManager.addAirline(airlineDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't add the airline.");
        else
            System.out.println("Airline added with sucess!");
    }
    
    private static void processEditAirline() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TAirlineDTO airlineDTO;
        boolean result;
        int id;
        

        System.out.println("Airline Id:");
        id = Integer.parseInt(sc.nextLine());
        airlineDTO = sAgencyManager.findAirline(id);

        if(airlineDTO == null)
        {
            System.out.println("Not found an airline with that id. Try again.");
            return;
        }

        System.out.println("Airline: " + airlineDTO);

        System.out.println("New Airline Name:");
        airlineDTO.setAirlineName(sc.nextLine());
        System.out.println("New Phonenumber:");
        airlineDTO.setPhoneNumber(sc.nextLine());

        result = sAgencyManager.editAirline(airlineDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't edit the airline.");
        else
            System.out.println("Airline edited with success!");
    }
    
    private static void processRemoveAirline() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TAirlineDTO airlineDTO;
        boolean result;
        int id;
        int op;
        
        
        System.out.println("Airline Id:");
        id = Integer.parseInt(sc.nextLine());
        airlineDTO = sAgencyManager.findAirline(id);

        if(airlineDTO == null)
        {
            System.out.println("Not found a airline with that id. Try again.");
            return;
        }


        System.out.println("Do you want remove the airline: " + airlineDTO + "? [1/0]");
        op = Integer.parseInt(sc.nextLine());

        if(op == 0)
            return;

        result = sAgencyManager.removeAirline(airlineDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't remove the airline.");
        else
            System.out.println("Airline removed with success!");
    }
    
    //----------------------------------------------------
    //places
    private static void processPlacesFindAll() throws NoPermissionException{
        
        System.out.println("All places in the system:");
        
        for(TPlaceDTO placeDTO : sAgencyManager.findAllPlaces())
            System.out.println(placeDTO);
    }
    
    private static void processAddPlace() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TPlaceDTO placeDTO = new TPlaceDTO();
        boolean result;
        
        System.out.println("Country:");
        placeDTO.setCountry(sc.nextLine());
        System.out.println("City:");
        placeDTO.setCity(sc.nextLine());
        System.out.println("Address:");
        placeDTO.setAddress(sc.nextLine());
        
        result = sAgencyManager.addPlace(placeDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't add the place.");
        else
            System.out.println("Place added with sucess!");
    }
    
    private static void processEditPlace() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TPlaceDTO placeDTO;
        boolean result;
        int id;
        
        System.out.println("Place Id:");
        id = Integer.parseInt(sc.nextLine());
        placeDTO = sAgencyManager.findPlace(id);

        if(placeDTO == null)
        {
            System.out.println("Not found a place with that id. Try again.");
            return;
        }

        System.out.println("Place: " + placeDTO);

        System.out.println("New place country:");
        placeDTO.setCountry(sc.nextLine());
        System.out.println("New place city:");
        placeDTO.setCity(sc.nextLine());
        System.out.println("New place address:");
        placeDTO.setAddress(sc.nextLine());

        result = sAgencyManager.editPlace(placeDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't edit the place.");
        else
            System.out.println("Place edited with success!");
    }
    
    private static void processRemovePlace() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TPlaceDTO placeDTO;
        boolean result;
        int id;
        int op;
        
        
        System.out.println("Place Id:");
        id = Integer.parseInt(sc.nextLine());
        placeDTO = sAgencyManager.findPlace(id);

        if(placeDTO == null)
        {
            System.out.println("Not found a place with that id. Try again.");
            return;
        }
        
        System.out.println("Do you want remove the place: " + placeDTO + "? [1/0]");
        op = Integer.parseInt(sc.nextLine());

        if(op == 0)
            return;

        result = sAgencyManager.removePlace(placeDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't remove the place.");
        else
            System.out.println("Place removed with success!");
    }
    
    //----------------------------------------------------
    //places feedback
    private static void processAddPlaceFeedback() throws NoPermissionException {
        Scanner sc = new Scanner(System.in);
        TPlaceFeedbackDTO placeFeedbackDTO = new TPlaceFeedbackDTO();
        int placeid;
        TPlaceDTO tPlaceDTO;
        boolean result;
        
        
        
        System.out.println("Place Id:");
        placeid = Integer.parseInt(sc.nextLine());
        tPlaceDTO = sAgencyManager.findPlace(placeid);

        if(tPlaceDTO == null)
        {
            System.out.println("Not found a place with that id. Try again.");
            return;
        }

        System.out.println("Place: " + tPlaceDTO);
        
        System.out.println("Score: ");
        placeFeedbackDTO.setScore(Integer.parseInt(sc.nextLine()));
        
        
        result = sAgencyManager.addFeedbackToPlace(tPlaceDTO, placeFeedbackDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't added the feedback.");
        else
            System.out.println("Feedback added with success!");
        
    }
    
    private static void processEditPlaceFeedback() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TPlaceFeedbackDTO placeFeedbackDTO;
        boolean result;
        int id;
        
        System.out.println("Place feedback Id:");
        id = Integer.parseInt(sc.nextLine());
        placeFeedbackDTO = sAgencyManager.findPlacefeedback(id);

        if(placeFeedbackDTO == null)
        {
            System.out.println("Not found a place's feedback with that id. Try again.");
            return;
        }

        System.out.println("Place: " + placeFeedbackDTO);

        System.out.println("Score: ");
        placeFeedbackDTO.setScore(Integer.parseInt(sc.nextLine()));
        
        
        result = sAgencyManager.editFeedbackOfPlace(placeFeedbackDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't edited the feedback.");
        else
            System.out.println("Feedback edited with success!");
    }
    
    private static void processRemovePlaceFeedback() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TPlaceFeedbackDTO placeFeedbackDTO;
        boolean result;
        int id;
        int op;
        
        System.out.println("Place feedback Id:");
        id = Integer.parseInt(sc.nextLine());
        placeFeedbackDTO = sAgencyManager.findPlacefeedback(id);

        if(placeFeedbackDTO == null)
        {
            System.out.println("Not found a place's feedback with that id. Try again.");
            return;
        }
        
        System.out.println("Do you want remove the place: " + placeFeedbackDTO + "? [1/0]");
        op = Integer.parseInt(sc.nextLine());

        if(op == 0)
            return;
        
        result = sAgencyManager.removeFeedbackOfPlace(placeFeedbackDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't removed the feedback.");
        else
            System.out.println("Feedback removed with success!");
    }


//----------------------------------------------------
    //trips
    
    private static void processTripsFindAll(){
        System.out.println("All trips in the system:");
       
        for(TTripDTO tripDTO : sAgencyManager.findAllTrips())
            System.out.println(tripDTO);

    }
    private static void processAddTrip() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TTripDTO tripDTO = new TTripDTO();
        int placeid,toPlaceid, planeid, airlineid;
        TPlaceDTO tFromPlaceDTO, tToPlaceDTO;
        TPlaneDTO tPlaneDTO;
        TAirlineDTO tAirlineDTO;
        
        boolean result;
        
        System.out.println("From Place Id:");
        placeid = Integer.parseInt(sc.nextLine());
        tFromPlaceDTO = sAgencyManager.findPlace(placeid);

        if(tFromPlaceDTO == null)
        {
            System.out.println("Not found a place with that id. Try again.");
            return;
        }
        System.out.println("From Place: " + tFromPlaceDTO);
        
        //----------
        System.out.println("To Place Id:");
        placeid = Integer.parseInt(sc.nextLine());
        tToPlaceDTO = sAgencyManager.findPlace(placeid);

        if(tToPlaceDTO == null)
        {
            System.out.println("Not found a place with that id. Try again.");
            return;
        }
        System.out.println("To Place: " + tToPlaceDTO);
        
        //-------------
        System.out.println("Plane Id:");
        planeid = Integer.parseInt(sc.nextLine());
        tPlaneDTO = sAgencyManager.findPlane(planeid);

        if(tPlaneDTO == null)
        {
            System.out.println("Not found a plane with that id. Try again.");
            return;
        }
        System.out.println("Plane: " + tPlaneDTO);
        //----
        System.out.println("Airline Id:");
        airlineid = Integer.parseInt(sc.nextLine());
        tAirlineDTO = sAgencyManager.findAirline(airlineid);

        if(tAirlineDTO == null)
        {
            System.out.println("Not found a airline with that id. Try again.");
            return;
        }
        System.out.println("Airline: " + tAirlineDTO);
        
        //-----------------
        
        tripDTO.setFromPlaceDTO(tFromPlaceDTO);
        tripDTO.setToPlaceDTO(tToPlaceDTO);
        tripDTO.setAirlineDTO(tAirlineDTO);
        tripDTO.setPlaneDTO(tPlaneDTO);
              
        System.out.println("Price: ");
        tripDTO.setPrice(Double.parseDouble(sc.nextLine()));
        
        System.out.println("Date trip: ");
        tripDTO.setDatetrip(Integer.parseInt(sc.nextLine()));
        
        result = sAgencyManager.addTrip(tripDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't added the trip.");
        else
            System.out.println("Trip added with success!");

    }
    private static void processEditTrip() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TTripDTO tripDTO;
        int placeid, planeid, airlineid, id;
        TPlaceDTO tFromPlaceDTO,tToPlaceDTO;
        TPlaneDTO tPlaneDTO;
        TAirlineDTO tAirlineDTO;
        
        boolean result;
        
        System.out.println("Trip Id to edit:");
        id = Integer.parseInt(sc.nextLine());
        tripDTO = sAgencyManager.findTrip(id);

        if(tripDTO == null)
        {
            System.out.println("Not found a trip with that id. Try again.");
            return;
        }
        
        System.out.println("Editing the trip: " + tripDTO);
        
        System.out.println("From Place Id:");
        placeid = Integer.parseInt(sc.nextLine());
        tFromPlaceDTO = sAgencyManager.findPlace(placeid);

        if(tFromPlaceDTO == null)
        {
            System.out.println("Not found a place with that id. Try again.");
            return;
        }
        System.out.println("From Place: " + tFromPlaceDTO);
        
        //----------
        System.out.println("To Place Id:");
        placeid = Integer.parseInt(sc.nextLine());
        tToPlaceDTO = sAgencyManager.findPlace(placeid);

        if(tToPlaceDTO == null)
        {
            System.out.println("Not found a place with that id. Try again.");
            return;
        }
        System.out.println("To Place: " + tToPlaceDTO);
        
        //-------------
        System.out.println("Plane Id:");
        planeid = Integer.parseInt(sc.nextLine());
        tPlaneDTO = sAgencyManager.findPlane(planeid);

        if(tPlaneDTO == null)
        {
            System.out.println("Not found a plane with that id. Try again.");
            return;
        }
        System.out.println("Plane: " + tPlaneDTO);
        //----
        System.out.println("Airline Id:");
        airlineid = Integer.parseInt(sc.nextLine());
        tAirlineDTO = sAgencyManager.findAirline(airlineid);

        if(tAirlineDTO == null)
        {
            System.out.println("Not found a airline with that id. Try again.");
            return;
        }
        System.out.println("Airline: " + tAirlineDTO);
        
        //-----------------
        
        tripDTO.setFromPlaceDTO(tFromPlaceDTO);
        tripDTO.setToPlaceDTO(tToPlaceDTO);
        tripDTO.setAirlineDTO(tAirlineDTO);
        tripDTO.setPlaneDTO(tPlaneDTO);
              
        System.out.println("Price: ");
        tripDTO.setPrice(Double.parseDouble(sc.nextLine()));
        
        System.out.println("Date trip: ");
        tripDTO.setDatetrip(Integer.parseInt(sc.nextLine()));
        
        result = sAgencyManager.editTrip(tripDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't eddited the trip.");
        else
            System.out.println("Trip eddited with success!");
    }

    private static void processRemoveTrip() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TTripDTO tripDTO;
        int id;
        int op;
        boolean result;
        
        System.out.println("Trip Id to remove:");
        id = Integer.parseInt(sc.nextLine());
        tripDTO = sAgencyManager.findTrip(id);

        if(tripDTO == null)
        {
            System.out.println("Not found a trip with that id. Try again.");
            return;
        }
        
        System.out.println("Do you want remove the trip: " + tripDTO + "? [1/0]");
        op = Integer.parseInt(sc.nextLine());

        if(op == 0)
            return;
        
        result = sAgencyManager.removeTrip(tripDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't removed the trip.");
        else
            System.out.println("Trip removed with success!");
    }
    private static void processCancelTrip() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TTripDTO tripDTO;
        int id;
        int op;
        boolean result;
        
        System.out.println("Trip Id to cancel:");
        id = Integer.parseInt(sc.nextLine());
        tripDTO = sAgencyManager.findTrip(id);

        if(tripDTO == null)
        {
            System.out.println("Not found a trip with that id. Try again.");
            return;
        }
        
        System.out.println("Do you want cancel the trip: " + tripDTO + "? [1/0]");
        op = Integer.parseInt(sc.nextLine());

        if(op == 0)
            return;
        
        result = sAgencyManager.cancelTrip(tripDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't canceled the trip.");
        else
            System.out.println("Trip canceled with success!");
    }
    private static void processSetDoneTrip() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TTripDTO tripDTO;
        int id;
        int op;
        boolean result;
        
        System.out.println("Trip Id to set as done:");
        id = Integer.parseInt(sc.nextLine());
        tripDTO = sAgencyManager.findTrip(id);

        if(tripDTO == null)
        {
            System.out.println("Not found a trip with that id. Try again.");
            return;
        }
        
        System.out.println("Do you want set as done the trip: " + tripDTO + "? [1/0]");
        op = Integer.parseInt(sc.nextLine());

        if(op == 0)
            return;
        
        result = sAgencyManager.setTripDone(tripDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't setted as done the trip.");
        else
            System.out.println("Trip setted as done with success!");
    }

    //----------------------------------------------------
    //trips feedback
    private static void processAddTripFeedback() throws NoPermissionException {
        Scanner sc = new Scanner(System.in);
        TTripFeedbackDTO fedbackDTO = new TTripFeedbackDTO();
        int tripid;
        TTripDTO tTripDTO;
        boolean result;
        
        System.out.println("Trip Id:");
        tripid = Integer.parseInt(sc.nextLine());
        tTripDTO = sAgencyManager.findTrip(tripid);

        if(tTripDTO == null)
        {
            System.out.println("Not found a trip with that id. Try again.");
            return;
        }

        System.out.println("Place: " + tTripDTO);
        
        System.out.println("Score: ");
        fedbackDTO.setScore(Integer.parseInt(sc.nextLine()));
        
        
        result = sAgencyManager.addFeedbackToTrip(tTripDTO, fedbackDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't added the feedback.");
        else
            System.out.println("Feedback added with success!");
        
    }
    
    private static void processEditTripFeedback() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TTripFeedbackDTO feedbackDTO;
        boolean result;
        int id;
        
        System.out.println("Trip feedback Id:");
        id = Integer.parseInt(sc.nextLine());
        feedbackDTO = sAgencyManager.findTripfeedback(id);

        if(feedbackDTO == null)
        {
            System.out.println("Not found a trip's feedback with that id. Try again.");
            return;
        }

        System.out.println("Trip's Feedback: " + feedbackDTO);

        System.out.println("Score: ");
        feedbackDTO.setScore(Integer.parseInt(sc.nextLine()));
        
        
        result = sAgencyManager.editFeedbackOfTrip(feedbackDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't edited the feedback.");
        else
            System.out.println("Feedback edited with success!");
    }
    
    private static void processRemoveTripFeedback() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TTripFeedbackDTO tripFeedbackDTO;
        boolean result;
        int id;
        int op;
        
        System.out.println("Trip feedback Id:");
        id = Integer.parseInt(sc.nextLine());
        tripFeedbackDTO = sAgencyManager.findTripfeedback(id);

        if(tripFeedbackDTO == null)
        {
            System.out.println("Not found a trip's feedback with that id. Try again.");
            return;
        }
        
        System.out.println("Do you want remove the place: " + tripFeedbackDTO + "? [1/0]");
        op = Integer.parseInt(sc.nextLine());

        if(op == 0)
            return;
        
        result = sAgencyManager.removeFeedbackOfTrip(tripFeedbackDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't removed the feedback.");
        else
            System.out.println("Feedback removed with success!");
    }
    
    //----------------------------------------------------
    //date
    
    private static void processGetActualDate(){
        System.out.println("Actual Date: " + sAgencyManager.getDate());
    }
    private static void processSetActualDate(){
        Scanner sc = new Scanner(System.in);
        int date;
        boolean result;
        
        System.out.println("Actual date pretended:");
        date = Integer.parseInt(sc.nextLine());
        
        
        result = sAgencyManager.setDate(date);
        if(!result)
            System.out.println("A problem occurred. The system didn't setted the new date.");
        else
            System.out.println("Actual date edited with success!"); 
        
    }
    private static void processSetDurationTimer(){
        Scanner sc = new Scanner(System.in);
        boolean result;
        int durationSeconds;
        
        System.out.println("Duration (seconds) :");
        durationSeconds = Integer.parseInt(sc.nextLine());
        
        result = sAgencyManager.setDurationTimer(durationSeconds);
        if(!result)
            System.out.println("A problem occurred. The system didn't setted the duration timer.");
        else
            System.out.println("Duration timer edited with success!"); 
    }
    
    private static void processGetTimerInformation(){
        System.out.println("Timer Information: " + sAgencyManager.getTimerInformation());
    }
    
    // logs
    
    private static void processGetLogs(String input) throws NoPermissionException {
        String[] inputs = input.split(" ");
        if (inputs.length == 1) {	
            printLogs(0);	
            return;	
        }	

         try {	
            int numberOfLines = Integer.parseInt(inputs[1]);	
            printLogs(numberOfLines);	

         } catch(NumberFormatException e) {	
            System.out.println("Wrong number format: " + inputs[1]);	
        }	
    }	

     private static void processRemoveLogs() throws NoPermissionException {	
        sAgencyManager.removeLogs();	
        System.out.println("Logs removed");	
    }
     
     private static void printLogs(int lines) throws NoPermissionException {	
        List<Log> logs = sAgencyManager.getLogs(lines);
        System.out.println("\nLOGS (" + logs.size() + " logs):");
        for (Log log : logs) {
            System.out.println(Utils.getFormattedLogMessage(log));
        }
     }
                
    //----------------------------------------------------
    //purchases
    private static void processFindAllPurchases() throws NoPermissionException{
        System.out.println("All Purchases in the system:");
       
        for(TPurchaseDTO purchaseDTO : sAgencyManager.findAllPurchases())
            System.out.println(purchaseDTO);
    }
    
    private static void processFindAllMyPurchases() throws NoPermissionException {
        System.out.println("All of your purchases in the system:");
       
        for(TPurchaseDTO purchaseDTO : sAgencyManager.findAllMyPurchases())
            System.out.println(purchaseDTO);
    }
    
    private static void processBuySeatsToTrip() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TTripDTO tripDTO;
        List<TSeatDTO> seatDTOList = new ArrayList();
        TSeatDTO seatDTO;
        int tripid, numberOfSeats;
        boolean result;
    
        System.out.println("Trip Id:");
        tripid = Integer.parseInt(sc.nextLine());
        tripDTO = sAgencyManager.findTrip(tripid);

        if(tripDTO == null)
        {
            System.out.println("Not found a trip with that id. Try again.");
            return;
        }

        System.out.println("Trip: " + tripDTO);
        
        System.out.println("Number of seats: ");
        numberOfSeats = Integer.parseInt(sc.nextLine());
        while(numberOfSeats>0)
        {
            seatDTO = new TSeatDTO();
            seatDTO.setAuctioned(false);
            System.out.println("Luggage of seat " + numberOfSeats + ":");
            seatDTO.setLuggage(sc.nextLine());
            seatDTOList.add(seatDTO);
            numberOfSeats--;
        }
          
        result = sAgencyManager.buySeatsToTrip(tripDTO, seatDTOList);
        if(!result)
            System.out.println("A problem occurred. The system didn't bought the seat of the trip "  +tripDTO +".");
        else
            System.out.println("You bought the seat to the trip " + tripDTO +" with sucess.");
        
    }
    
    private static void processFindActualPurchase() throws NoPermissionException {
        TPurchaseDTO purchaseDTO;
        purchaseDTO = sAgencyManager.getActualPurchase();
      
        if(purchaseDTO == null)
        {
            System.out.println("Not found an uncompleted purchase. Try again.");
            return;
        }
        
        System.out.println("Actual purchase (undone): " + purchaseDTO);
    }
    
    private static void processEditActualPurchase() throws NoPermissionException {
        Scanner sc = new Scanner(System.in);
        TPurchaseDTO purchaseDTO;
        boolean result;
        
        purchaseDTO = sAgencyManager.getActualPurchase();
      
        if(purchaseDTO == null)
        {
            System.out.println("Not found an uncompleted purchase. Try again.");
            return;
        }
        
        System.out.println("Actual purchase (undone): " + purchaseDTO);
        
        for(TSeatDTO seatDTO : purchaseDTO.gettSeatCollection())
        {
            System.out.println("Actual seat: " + seatDTO);
            System.out.println("New luggage :");
            seatDTO.setLuggage(sc.nextLine());
        }
        
        result = sAgencyManager.editActualPurchase(purchaseDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't edited the purchase.");
        else
            System.out.println("You edited the purchase with sucess.");

    }
    
    private static void processRemoveSeatsOfActualPurchase() throws NoPermissionException {
        Scanner sc = new Scanner(System.in);
        TPurchaseDTO purchaseDTO;
        int tripid;
        TTripDTO tripDTO;
        boolean result;
        
        purchaseDTO = sAgencyManager.getActualPurchase();
      
        if(purchaseDTO == null)
        {
            System.out.println("Not found an uncompleted purchase. Try again.");
            return;
        }
        
        System.out.println("Actual purchase (undone): " + purchaseDTO);
        
        System.out.println("Trip Id to remove of purchase:");
        tripid = Integer.parseInt(sc.nextLine());
        tripDTO = sAgencyManager.findTrip(tripid);

        if(tripDTO == null)
        {
            System.out.println("Not found a trip with that id. Try again.");
            return;
        }
      
        
        result = sAgencyManager.removeSeatsOfActualPurchase(purchaseDTO, tripDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't removed the seats to the trip: " + tripDTO + " of the purchase.");
        else
            System.out.println("You removed the seats to the trip: " + tripDTO + " of the purchase with sucess.");
    }

    private static void processRemoveActualPurchase() throws NoPermissionException {
        Scanner sc = new Scanner(System.in);
        TPurchaseDTO purchaseDTO;
        int op;
        boolean result;
        
        purchaseDTO = sAgencyManager.getActualPurchase();
      
        if(purchaseDTO == null)
        {
            System.out.println("Not found an uncompleted purchase. Try again.");
            return;
        }
        
        System.out.println("Actual purchase (undone): " + purchaseDTO);
        
        System.out.println("Do you want remove the purchase: " + purchaseDTO + "? [1/0]");
        op = Integer.parseInt(sc.nextLine());

        if(op == 0)
            return;
        
        result = sAgencyManager.removeActualPurchase(purchaseDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't removed actual purchase.");
        else
            System.out.println("You removed the actual purchase.");
    }

    private static void processFinishActualPurchase() throws NoPermissionException {
        Scanner sc = new Scanner(System.in);
        TPurchaseDTO purchaseDTO;
        int op;
        boolean result;
        
        purchaseDTO = sAgencyManager.getActualPurchase();
      
        if(purchaseDTO == null)
        {
            System.out.println("Not found an uncompleted purchase. Try again.");
            return;
        }
        
        System.out.println("Actual purchase (undone): " + purchaseDTO);
        
        System.out.println("The purchase: " + purchaseDTO + "\n Will cost X€. Do you want finish? [1/0]");
        op = Integer.parseInt(sc.nextLine());

        if(op == 0)
            return;
        
        result = sAgencyManager.finishActualPurchase(purchaseDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't finished the actual purchase.");
        else
            System.out.println("You finished the actual purchase.");
    }

    private static void processRemoveDonePurchase() throws NoPermissionException {
        Scanner sc = new Scanner(System.in);
        TPurchaseDTO purchaseDTO;
        int op;
        boolean result;
        int purchaseid;
        
        System.out.println("ID of Purchase to remove: ");
        purchaseid = Integer.parseInt(sc.nextLine());

        purchaseDTO = sAgencyManager.findPurchase(purchaseid);
      
        if(purchaseDTO == null)
        {
            System.out.println("Not found a purchase with that id. Try again.");
            return;
        }
        
        System.out.println("Do you want remove the purchase: " + purchaseDTO + "? [1/0]");
        op = Integer.parseInt(sc.nextLine());

        if(op == 0)
            return;
        
        result = sAgencyManager.removeDonePurchase(purchaseDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't removed done purchase.");
        else
            System.out.println("You removed the done purchase.");
    }
    
    //----------------------------------------------------
    //auctioned seats
    private static void processFindAllAuctionedSeats() throws NoPermissionException {
        System.out.println("All Actioned seats in the system:");
       
        for(TSeatDTO seatDTO : sAgencyManager.findAllAuctionedSeats())
            System.out.println(seatDTO);
    }

    private static void processBidActionedSeat() throws NoPermissionException {
        Scanner sc = new Scanner(System.in);
        
        TSeatDTO seatDTO;
        int seatId; double price;
        boolean result;
    
        System.out.println("Seat Id:");
        seatId = Integer.parseInt(sc.nextLine());
        seatDTO = sAgencyManager.findAuctionedSeat(seatId);

        if(seatDTO == null)
        {
            System.out.println("Not found a auctioned seat with that id. Try again.");
            return;
        }

        System.out.println("Auctioned Seat: " + seatDTO);
        
        System.out.println("New bid for the seat: ");
        seatDTO.setPrice(Double.parseDouble(sc.nextLine()));

        System.out.println("Luggage: ");
        seatDTO.setLuggage(sc.nextLine());

        result = sAgencyManager.bidAuctionedSeat(seatDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't bid the seat.");
        else
            System.out.println("You did the bid to the seat with success.");
    }

    private static void processGetMyBids() throws NoPermissionException {
        System.out.println("All Actioned seats in the system:");
       
        for(TSeatDTO seatDTO : sAgencyManager.getMyBids())
            System.out.println(seatDTO);
    }
    
    private static void processGetCheapTrips() {
        System.out.println("Cheapest trips:");
        for (TTripDTO trip : sGuestAgencyManager.getCheapestTrips(5)) {
            System.out.println(trip);
        }
    }
    
    //----------------------------------------------------
    //Auxiliar methods
    private static void printCommandList(){
        System.out.println("\n-- Help --");
        
        //users
        System.out.println(Command.SIGNIN + " - Sign in");
        System.out.println(Command.SIGNUP + " - Sign up");
        System.out.println(Command.LOGOUT + " - Logout");
        System.out.println(Command.DEPOSITTOACCOUNT + " - Deposit amount in user account");
        System.out.println(Command.FINDALLUSERS + " - Find all Users");
        System.out.println("asguest - Enter as guest");
        System.out.println(Command.EXIT + " - Exit");

        //planes
        System.out.println("\n-------Planes--------");
        System.out.println(Command.FINDALLPLANES + " - List All Planes");
        System.out.println(Command.ADDPLANE + " - Add a new plane");
        System.out.println(Command.EDITPLANE + " - Edit a plane");
        System.out.println(Command.REMOVEPLANE + " - Remove a plane");
        
        //airlines
        System.out.println("\n-------Airlines--------");
        System.out.println(Command.FINDALLAIRLINES + " - List All airlines");
        System.out.println(Command.ADDAIRLINE + " - Add a new airline");
        System.out.println(Command.EDITAIRLINE + " - Edit an airline");
        System.out.println(Command.REMOVEAIRLINE + " - Remove an airline");
        
        //places
        System.out.println("\n-------Places--------");
        System.out.println(Command.FINDALLPLACES + " - List All places");
        System.out.println(Command.ADDPLACE + " - Add a new place");
        System.out.println(Command.EDITPLACE + " - Edit a place");
        System.out.println(Command.REMOVEPLACE + " - Remove a place");
        
        
        //places feedback
        System.out.println("\n-------Feedback of Places--------");
        System.out.println(Command.ADDPLACEFEEDBACK + " - Add a new feedback of a place");
        System.out.println(Command.EDITPLACEFEEDBACK + " - Edit a feedback of a place");
        System.out.println(Command.REMOVEPLACEFEEDBACK + " - Remove feedback of a place");
        
        //trips
        System.out.println("\n-------Trips--------");
        System.out.println(Command.FINDALLTRIPS + " - List All trips");
        System.out.println(Command.ADDTRIP + " - Add a new trip");
        System.out.println(Command.EDITTRIP + " - Edit a trip");
        System.out.println(Command.REMOVETRIP + " - Remove a trip");
        System.out.println(Command.CANCELTRIP + " - Cancel a trip");
        System.out.println(Command.SETTRIPDONE + " - Set done a trip");
        
        //trips feedback
        System.out.println("\n-------Feedback of Trips--------");
        System.out.println(Command.ADDTRIPFEEDBACK+ " - Add a new feedback of a trip");
        System.out.println(Command.EDITTRIPFEEDBACK + " - Edit a feedback of a trip");
        System.out.println(Command.REMOVETRIPFEEDBACK + " - Remove feedback of a trip");
        
        //time
        System.out.println("\n-------Time--------");
        System.out.println(Command.GETACTUALDATE+ " - Get actual time of the system");
        System.out.println(Command.SETACTUALDATE+ " - Set actual time of the system");
        System.out.println(Command.SETDURATIONTIMER + " - Set (in minuts) the increment of time");
        System.out.println(Command.GETTIMERINFORMATION + " - Get information of the timer");
        
        //logs
        System.out.println("\n-------Logs--------");
        System.out.println(Command.GET_LOGS + " - Get logs. Syntax: getlogs [n] (n: number of lines - optional)");
        System.out.println(Command.REMOVE_LOGS + " - Remove all logs");
        
        //Purchases
        System.out.println("\n-------Purchases--------");
        System.out.println(Command.FINDALLPURCHASES+ " - Find all purchases in the system");
        System.out.println(Command.FINDACTUALPURCHASE+ " - Get actual purchase.");
        System.out.println(Command.FINDALLMYPURCHASES+ " - Find all your purchases in the system");
        System.out.println(Command.BUYSEATSTOTRIP+ " - Buy seats to a trip");
        System.out.println(Command.EDITACTUALPURCHASE+ " - Edit actual purchase");
        System.out.println(Command.REMOVESEATSOFACTUALPURCHASE+ " - Remove seats of a purchase. Will remove from the actual purchase.");
        System.out.println(Command.REMOVEACTUALPURCHASE+ " - Remove the actual purchase");
        System.out.println(Command.FINISHACTUALPURCHASE+ " - Finish the actual purchase");
        System.out.println(Command.REMOVEDONEPURCHASE+ " - Remove a done purchase");

        
        
        
        //auctioned seats
        System.out.println("\n-------Actioned Seats--------");
        System.out.println(Command.FINDAUCTIONEDSEATS+ " - Find all actioned seats in the system");
        System.out.println(Command.BIDAUCTIONEDSEAT+ " - Bid auctioned seat in the system");
        System.out.println(Command.FINDMYBIDS+ " - Find all my bids in the system");

        System.out.println("----------------");
    }
    
    private static void processCLS(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
            
    private static void initRemoteReferences() {
        Properties prop = new Properties();

        prop.setProperty("java.naming.factory.initial",
                "com.sun.enterprise.naming.SerialInitContextFactory");

        prop.setProperty("java.naming.factory.url.pkgs",
                "com.sun.enterprise.naming");

        prop.setProperty("java.naming.factory.state",
                "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

        prop.setProperty("org.omg.CORBA.ORBInitialHost", "192.168.56.175");
        prop.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

        InitialContext ctx = null;
        try {
           ctx = new InitialContext(prop);
        
        } catch (NamingException e) {
           System.out.println(e.getMessage());
           System.exit(1);
        }

        String agencyManagerClassName = "java:global/Agency/Agency-ejb/AgencyManager!logic.AgencyManagerRemote";
        String guestAgencyManagerClassName = "java:global/Agency/Agency-ejb/GuestAgencyManager!logic.GuestAgencyManagerRemote";

        try {
           sGuestAgencyManager = (GuestAgencyManagerRemote) ctx.lookup(guestAgencyManagerClassName);
           sAgencyManager = (AgencyManagerRemote) ctx.lookup(agencyManagerClassName);
        
        } catch (NamingException e) {
           System.out.println(e.getMessage());
           e.printStackTrace();
        }
    }

    
}