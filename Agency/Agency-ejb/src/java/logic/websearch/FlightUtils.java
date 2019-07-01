/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.websearch;

import logic.TPlaceDTO;
import logic.TTripDTO;

public class FlightUtils {
    
    private FlightUtils() {
        // Do nothing.
    }
    
    public static Flight createFlightFromTrip(TTripDTO trip, int emptySeats) {
        TPlaceDTO origin = trip.getFromPlaceDTO();
        TPlaceDTO destiny = trip.getToPlaceDTO();
        
        return new Flight(trip.getPrice(), emptySeats, origin.getCity(), destiny.getCity());
    }
    
    public static boolean isTripActive(TTripDTO trip) {
        return !trip.getDone() && !trip.getCanceled();
    }
    
    public static boolean matchesOrigin(String criteria, TTripDTO trip) {
        TPlaceDTO place = trip.getFromPlaceDTO();
        return criteria.compareToIgnoreCase(place.getCity()) == 0;
    }
    
    public static boolean matchesDestiny(String criteria, TTripDTO trip) {
        TPlaceDTO place = trip.getToPlaceDTO();
        return criteria.compareToIgnoreCase(place.getCity()) == 0;
    }
    
    public static boolean validateParam(String param) {
        return param != null && !param.trim().isEmpty();
    }
}
