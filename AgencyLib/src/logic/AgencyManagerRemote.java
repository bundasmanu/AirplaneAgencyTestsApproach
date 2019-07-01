/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author bruno
 */
@Remote
public interface AgencyManagerRemote {
    //users
    SignInValue signIn(String username, String password);
    boolean signUp(TUserDTO userDTO);
    boolean logout();
    boolean acceptUser(TUserDTO userDTO);
    TUserDTO getTUserDTO(String username);
    boolean depositToAccount(float amount);
    List<TUserDTO> findAllUsers();
        
    //planes
    List<TPlaneDTO> findAllPlanes() throws NoPermissionException;
    TPlaneDTO findPlane(int id) throws NoPermissionException;
    boolean addPlane(TPlaneDTO planeDTO) throws NoPermissionException;
    boolean editPlane(TPlaneDTO planeDTO) throws NoPermissionException;
    boolean removePlane(TPlaneDTO planeDTO) throws NoPermissionException;
    
    //airlines
    List<TAirlineDTO> findAllAirlines() throws NoPermissionException;
    TAirlineDTO findAirline(int id) throws NoPermissionException;
    boolean addAirline(TAirlineDTO airlineDTO) throws NoPermissionException;
    boolean editAirline(TAirlineDTO airlineDTO) throws NoPermissionException;
    boolean removeAirline(TAirlineDTO airlineDTO) throws NoPermissionException;
    
    //places
    List<TPlaceDTO> findAllPlaces();
    TPlaceDTO findPlace(int id);
    boolean addPlace(TPlaceDTO placeDTO) throws NoPermissionException;
    boolean editPlace(TPlaceDTO placeDTO) throws NoPermissionException;
    boolean removePlace(TPlaceDTO placeDTO) throws NoPermissionException;
    
    //place feedback
    TPlaceFeedbackDTO findPlacefeedback(int id);
    boolean addFeedbackToPlace(TPlaceDTO placeDTO, TPlaceFeedbackDTO feedbackDTO) throws NoPermissionException;
    boolean editFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO) throws NoPermissionException;
    boolean removeFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO) throws NoPermissionException;

    //trip
    List<TTripDTO> findAllTrips();
    List<TTripDTO> findAllUndoneTrips();
    TTripDTO findTrip(int id);
    boolean addTrip(TTripDTO tripDTO) throws NoPermissionException;
    boolean editTrip(TTripDTO tripDTO) throws NoPermissionException;
    boolean removeTrip(TTripDTO tripDTO) throws NoPermissionException;
    boolean cancelTrip(TTripDTO tripDTO) throws NoPermissionException;
    boolean setTripDone(TTripDTO tripDTO) throws NoPermissionException;

    //trip feedback
    TTripFeedbackDTO findTripfeedback(int id);
    boolean addFeedbackToTrip(TTripDTO tripDTO, TTripFeedbackDTO feedbackDTO) throws NoPermissionException;
    boolean editFeedbackOfTrip(TTripFeedbackDTO feedbackDTO) throws NoPermissionException;
    boolean removeFeedbackOfTrip(TTripFeedbackDTO feedbackDTO) throws NoPermissionException;
    
    //date
    int getDate();
    boolean setDate(int date);
    boolean setDurationTimer(long durationSeconds);
    String getTimerInformation();

    // logs
    List<Log> getLogs(int lines) throws NoPermissionException;
    void removeLogs() throws NoPermissionException;
    
    //purchase
    List<TPurchaseDTO> findAllPurchases() throws NoPermissionException;
    List<TPurchaseDTO> findAllMyPurchases() throws NoPermissionException;
    TPurchaseDTO findPurchase(int id) throws NoPermissionException;
    TPurchaseDTO getActualPurchase() throws NoPermissionException;
    boolean changeNumberOfPersonsOfActualPurchase(TPurchaseDTO purchaseDTO) throws NoPermissionException;
    boolean buySeatsToTrip(TTripDTO tripDTO, List<TSeatDTO> seatDTOList) throws NoPermissionException;
    boolean editActualPurchase(TPurchaseDTO purchaseDTO) throws NoPermissionException;
    boolean removeSeatsOfActualPurchase(TPurchaseDTO purchaseDTO, TTripDTO tripDTO) throws NoPermissionException;
    boolean removeActualPurchase(TPurchaseDTO purchaseDTO) throws NoPermissionException;
    boolean finishActualPurchase(TPurchaseDTO purchaseDTO) throws NoPermissionException;
    boolean removeDonePurchase(TPurchaseDTO purchaseDTO) throws NoPermissionException;

        
    //acutioned seats
    List<TSeatDTO> findAllAuctionedSeats() throws NoPermissionException;
    TSeatDTO findAuctionedSeat(int id) throws NoPermissionException;
    List<TSeatDTO> getMyBids() throws NoPermissionException;
    boolean bidAuctionedSeat(TSeatDTO seatDTO) throws NoPermissionException;

}
