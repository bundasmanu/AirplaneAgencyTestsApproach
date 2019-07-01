/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import logic.LogsManagement.LogsManagerLocal;
import logic.TimerManagement.TimerManagerLocal;
import logic.TripsManagement.TripsManagerLocal;
import logic.UsersManagement.UsersManagerLocal;

/**
 *
 * @author bruno
 */
@Stateful
public class AgencyManager implements AgencyManagerRemote {

    @EJB
    UsersManagerLocal usersManagerLocal;
    
    @EJB
    TripsManagerLocal tripsManagerLocal;
    
    @EJB
    TimerManagerLocal timerManagerLocal;
    
    @EJB
    LogsManagerLocal logsManagerLocal;
    
    String username;

    //users
    @Override
    public SignInValue signIn(String username, String password) {
        SignInValue result = usersManagerLocal.signIn(username, password);
        
        //if the user logged with success username var has is username, otherwise username var is null or empty
        if (result == SignInValue.SUCCESS) {
            this.username = username;
        }
        
        return result;
    }
    
    @Override
    public boolean signUp(TUserDTO userDTO) {
        return usersManagerLocal.signUp(userDTO);
    }
    
    @Override
    public TUserDTO getTUserDTO(String username) {
        return usersManagerLocal.getTUserDTO(username);
    }

    @Override
    public boolean acceptUser(TUserDTO userDTO) {
        return usersManagerLocal.acceptTUser(userDTO, username);
    }

    @Override
    public boolean logout() {
        this.username = null;
        return true;
    }
    
    @Override
    public boolean depositToAccount(float amount) {
        return usersManagerLocal.depositToAccount(amount, username);
    }
    
    @Override
    public List<TUserDTO> findAllUsers(){
        return usersManagerLocal.findAllUsers();
    }
    
//----------------------------------------------------------------------
    //Planes
    @Override
    public List<TPlaneDTO> findAllPlanes() throws NoPermissionException{
        return tripsManagerLocal.findAllPlanes(username);
    }
    
    public List<TTripDTO> findAllUndoneTrips(){
        return tripsManagerLocal.findAllUndoneTrips();
    }
    
    @Override
    public TPlaneDTO findPlane(int id) throws NoPermissionException {
        return tripsManagerLocal.findPlane(id, username);
    }

    @Override
    public boolean addPlane(TPlaneDTO planeDTO) throws NoPermissionException {
        return tripsManagerLocal.addPlane(planeDTO, username);
    }

    @Override
    public boolean editPlane(TPlaneDTO planeDTO) throws NoPermissionException {
        return tripsManagerLocal.editPlane(planeDTO, username);
    }

    @Override
    public boolean removePlane(TPlaneDTO planeDTO) throws NoPermissionException {
        return tripsManagerLocal.removePlane(planeDTO, username);
    }

    //----------------------------------------------------------------------
    //Airlines
    @Override
    public List<TAirlineDTO> findAllAirlines() throws NoPermissionException {
        return tripsManagerLocal.findAllAirlines(username);
    }

    @Override
    public TAirlineDTO findAirline(int id) throws NoPermissionException {
        return tripsManagerLocal.findAirline(id, username);
    }

    @Override
    public boolean addAirline(TAirlineDTO airlineDTO) throws NoPermissionException {
        return tripsManagerLocal.addAirline(airlineDTO, username);
    }

    @Override
    public boolean editAirline(TAirlineDTO airlineDTO) throws NoPermissionException {
        return tripsManagerLocal.editAirline(airlineDTO, username);
    }

    @Override
    public boolean removeAirline(TAirlineDTO airlineDTO) throws NoPermissionException {
        return tripsManagerLocal.removeAirline(airlineDTO, username);
    }
//----------------------------------------------------------------------
    //Places
    @Override
    public List<TPlaceDTO> findAllPlaces() {
        return tripsManagerLocal.findAllPlaces(username);
    }

    @Override
    public TPlaceDTO findPlace(int id) {
        return tripsManagerLocal.findPlace(id);
    }

    @Override
    public boolean addPlace(TPlaceDTO placeDTO) throws NoPermissionException {
        return tripsManagerLocal.addPlace(placeDTO, username);
    }

    @Override
    public boolean editPlace(TPlaceDTO placeDTO) throws NoPermissionException {
        return tripsManagerLocal.editPlace(placeDTO, username);
    }

    @Override
    public boolean removePlace(TPlaceDTO placeDTO) throws NoPermissionException {
        return tripsManagerLocal.removePlace(placeDTO, username);
    }
    //----------------------------------------------------------------------
    //Feedback Places
    @Override
    public TPlaceFeedbackDTO findPlacefeedback(int id) {
        return tripsManagerLocal.findPlacefeedback(id);
    }
    
    @Override
    public boolean addFeedbackToPlace(TPlaceDTO placeDTO, TPlaceFeedbackDTO feedbackDTO) throws NoPermissionException {
        return tripsManagerLocal.addFeedbackToPlace(placeDTO, feedbackDTO, username);
    }

    @Override
    public boolean editFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO) throws NoPermissionException {
        return tripsManagerLocal.editFeedbackOfPlace(feedbackDTO, username);
    }

    @Override
    public boolean removeFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO) throws NoPermissionException {
        return tripsManagerLocal.removeFeedbackOfPlace(feedbackDTO, username);
    }
    //----------------------------------------------------------------------    
//Trips
    @Override
    public List<TTripDTO> findAllTrips() {
        return tripsManagerLocal.findAllTrips();
    }

    @Override
    public TTripDTO findTrip(int id) {
        return tripsManagerLocal.findTrip(id);
    }

    @Override
    public boolean addTrip(TTripDTO tripDTO) throws NoPermissionException {
        return tripsManagerLocal.addTrip(tripDTO, username);
    }

    @Override
    public boolean editTrip(TTripDTO tripDTO) throws NoPermissionException {
        return tripsManagerLocal.editTrip(tripDTO, username);
    }

    @Override
    public boolean removeTrip(TTripDTO tripDTO) throws NoPermissionException {
        return tripsManagerLocal.removeTrip(tripDTO, username);
    }

    @Override
    public boolean cancelTrip(TTripDTO tripDTO) throws NoPermissionException {
        return tripsManagerLocal.cancelTrip(tripDTO, username);
    }

    @Override
    public boolean setTripDone(TTripDTO tripDTO) throws NoPermissionException {
        return tripsManagerLocal.setTripDone(tripDTO, username);
    }

     //----------------------------------------------------------------------    
//Trips feedback
    
    @Override
    public TTripFeedbackDTO findTripfeedback(int id) {
        return tripsManagerLocal.findTripfeedback(id);
    }

    @Override
    public boolean addFeedbackToTrip(TTripDTO tripDTO, TTripFeedbackDTO feedbackDTO) throws NoPermissionException {
        return tripsManagerLocal.addFeedbackToTrip(tripDTO, feedbackDTO, username);
    }

    @Override
    public boolean editFeedbackOfTrip(TTripFeedbackDTO feedbackDTO) throws NoPermissionException {
        return tripsManagerLocal.editFeedbackOfTrip(feedbackDTO, username);
    }

    @Override
    public boolean removeFeedbackOfTrip(TTripFeedbackDTO feedbackDTO) throws NoPermissionException {
        return tripsManagerLocal.removeFeedbackOfTrip(feedbackDTO, username);
    }

    //---------------------
    // date
    @Override
    public int getDate() {
        return timerManagerLocal.getDate();
    }

    @Override
    public boolean setDate(int date) {
        return timerManagerLocal.setDate(date);
    }
    
    @Override
    public boolean setDurationTimer(long durationSeconds) {
        return timerManagerLocal.setDurationTimer(durationSeconds);
    }

    @Override
    public String getTimerInformation() {
        return timerManagerLocal.getTimerInformation();
    }    

    //---------------------
    // logs
    @Override
    public List<Log> getLogs(int lines) throws NoPermissionException {
        return logsManagerLocal.getLogs(lines, username);
    }
    
    @Override	
    public void removeLogs() throws NoPermissionException {	
        logsManagerLocal.removeLogs(username);	
    }

    //---------------------
    // purchases
    @Override
    public List<TPurchaseDTO> findAllPurchases() throws NoPermissionException {
        return tripsManagerLocal.findAllPurchases(username);
    }

    @Override
    public List<TPurchaseDTO> findAllMyPurchases() throws NoPermissionException {
        return tripsManagerLocal.findAllMyPurchases(username);
    }

    @Override
    public TPurchaseDTO findPurchase(int id) throws NoPermissionException {
        return tripsManagerLocal.findPurchase(id, username);
    }

    @Override
    public boolean buySeatsToTrip(TTripDTO tripDTO, List<TSeatDTO> seatDTOList) throws NoPermissionException {
        return tripsManagerLocal.buySeatsToTrip(tripDTO, seatDTOList, username);
    }

    @Override
    public boolean editActualPurchase(TPurchaseDTO purchaseDTO) throws NoPermissionException {
        return tripsManagerLocal.editActualPurchase(purchaseDTO, username);
    }

    @Override
    public boolean changeNumberOfPersonsOfActualPurchase(TPurchaseDTO purchaseDTO) throws NoPermissionException {
        return tripsManagerLocal.changeNumberOfPersonsOfActualPurchase(purchaseDTO, username);
    }
    
    @Override
    public boolean removeActualPurchase(TPurchaseDTO purchaseDTO) throws NoPermissionException {
        return tripsManagerLocal.removeActualPurchase(purchaseDTO, username);
    }

    @Override
    public boolean finishActualPurchase(TPurchaseDTO purchaseDTO) throws NoPermissionException {
        return tripsManagerLocal.finishActualPurchase(purchaseDTO, username);
    }

    @Override
    public boolean removeSeatsOfActualPurchase(TPurchaseDTO purchaseDTO, TTripDTO tripDTO) throws NoPermissionException {
        return tripsManagerLocal.removeSeatsOfActualPurchase(purchaseDTO, tripDTO, username);
    }

    @Override
    public TPurchaseDTO getActualPurchase() throws NoPermissionException {
        return tripsManagerLocal.getActualPurchase(username);
    }
    @Override
    public boolean removeDonePurchase(TPurchaseDTO purchaseDTO) throws NoPermissionException {
        return tripsManagerLocal.removeDonePurchase(purchaseDTO, username);

    }
    
    
    
    //AUCTIONED SEATS

    @Override
    public List<TSeatDTO> findAllAuctionedSeats() throws NoPermissionException {
        return tripsManagerLocal.findAllAuctionedSeats(username);
    }

    @Override
    public List<TSeatDTO> getMyBids() throws NoPermissionException {
        return tripsManagerLocal.getMyBids(username);
    }

    @Override
    public boolean bidAuctionedSeat(TSeatDTO seatDTO) throws NoPermissionException {
        return tripsManagerLocal.bidAuctionedSeat(seatDTO, username);

    }

    @Override
    public TSeatDTO findAuctionedSeat(int id) throws NoPermissionException {
        return tripsManagerLocal.findAuctionedSeat(id, username);
    }

    

}
