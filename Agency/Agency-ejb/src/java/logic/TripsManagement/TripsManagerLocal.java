/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import java.util.List;
import javax.ejb.Local;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaceFeedbackDTO;
import logic.TPlaceDTO;
import logic.TPlaneDTO;
import logic.TPurchaseDTO;
import logic.TSeatDTO;
import logic.TTripDTO;
import logic.TTripFeedbackDTO;

@Local
public interface TripsManagerLocal {
    
    //planes
    List<TPlaneDTO> findAllPlanes(String username) throws NoPermissionException;
    List<TPlaneDTO> findPlanesByRange(String username, int begin, int index) throws NoPermissionException;
    TPlaneDTO findPlane(int id, String username) throws NoPermissionException;
    boolean addPlane(TPlaneDTO planeDTO, String username) throws NoPermissionException;
    boolean editPlane(TPlaneDTO planeDTO, String username) throws NoPermissionException;
    boolean removePlane(TPlaneDTO planeDTO, String username) throws NoPermissionException;
    
    //Airline
    List<TAirlineDTO> findAllAirlines(String username) throws NoPermissionException;
    TAirlineDTO findAirline(int id, String username) throws NoPermissionException;
    boolean addAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException;
    boolean editAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException;
    boolean removeAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException;

    //places
    List<TPlaceDTO> findAllPlaces(String username);
    TPlaceDTO findPlace(int id);
    boolean addPlace(TPlaceDTO placeDTO, String username) throws NoPermissionException;
    boolean editPlace(TPlaceDTO placeDTO, String username) throws NoPermissionException;
    boolean removePlace(TPlaceDTO placeDTO, String username) throws NoPermissionException;
    
    //place feedback
    TPlaceFeedbackDTO findPlacefeedback(int id);
    boolean addFeedbackToPlace(TPlaceDTO placeDTO, TPlaceFeedbackDTO feedbackDTO, String username) throws NoPermissionException;
    boolean editFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO, String username) throws NoPermissionException;
    boolean removeFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO, String username) throws NoPermissionException;
    
    //trip
    List<TTripDTO> findAllTrips();
    List<TTripDTO> findAllUndoneTrips();
    TTripDTO findTrip(int id);
    boolean addTrip(TTripDTO tripDTO, String username) throws NoPermissionException;
    boolean editTrip(TTripDTO tripDTO, String username) throws NoPermissionException;
    boolean removeTrip(TTripDTO tripDTO, String username) throws NoPermissionException;
    boolean cancelTrip(TTripDTO tripDTO, String username) throws NoPermissionException;
    boolean setTripDone(TTripDTO tripDTO, String username) throws NoPermissionException;
    void processEndOfTrips(int actualDate);
    
    
    //trip feedback
    TTripFeedbackDTO findTripfeedback(int id);
    boolean addFeedbackToTrip(TTripDTO tripDTO, TTripFeedbackDTO tripFeedbackDTO, String username) throws NoPermissionException;
    boolean editFeedbackOfTrip(TTripFeedbackDTO tripFeedbackDTO, String username) throws NoPermissionException;
    boolean removeFeedbackOfTrip(TTripFeedbackDTO tripFeedbackDTO, String username) throws NoPermissionException;

    //purchase
    List<TPurchaseDTO> findAllPurchases(String username) throws NoPermissionException;
    List<TPurchaseDTO> findAllMyPurchases(String username) throws NoPermissionException;
    TPurchaseDTO findPurchase(int id, String username) throws NoPermissionException;
    TPurchaseDTO getActualPurchase(String username) throws NoPermissionException;
    boolean buySeatsToTrip(TTripDTO tripDTO, List<TSeatDTO> seatDTOList, String username) throws NoPermissionException;
    boolean changeNumberOfPersonsOfActualPurchase(TPurchaseDTO purchaseDTO, String username) throws NoPermissionException;
    boolean editActualPurchase(TPurchaseDTO purchaseDTO, String username) throws NoPermissionException;
    boolean removeSeatsOfActualPurchase(TPurchaseDTO purchaseDTO, TTripDTO tripDTO, String username) throws NoPermissionException;
    boolean removeActualPurchase(TPurchaseDTO purchaseDTO, String username) throws NoPermissionException;
    boolean finishActualPurchase(TPurchaseDTO purchaseDTO, String username) throws NoPermissionException;
    boolean removeDonePurchase(TPurchaseDTO purchaseDTO, String username) throws NoPermissionException;

    
    //acutioned seats
    List<TSeatDTO> findAllAuctionedSeats(String username) throws NoPermissionException;
    TSeatDTO findAuctionedSeat(int id, String username) throws NoPermissionException;
    List<TSeatDTO> getMyBids(String username) throws NoPermissionException;
    boolean bidAuctionedSeat(TSeatDTO seatDTO, String username) throws NoPermissionException;
    
    int getAvailableSeats(TTripDTO trip);
    
    List<TTripDTO> getCheapeastTrips(int count);
    List<TTripDTO> getActiveTripsByUser(String username);
    int getNoOfSeatsFromTripByUser(String username, TTripDTO tripDTO);
}
