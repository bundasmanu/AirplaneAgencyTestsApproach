/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.websearch;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import logic.TTripDTO;
import logic.TripsManagement.TripsManagerLocal;

@Singleton
public class FlightManager implements FlightManagerLocal {
    
    @EJB
    private TripsManagerLocal tripsManager;
    
    public FlightManager() {
        // Do nothing
    }
    
    @Override
    public List<Flight> getAllFlights() {
        System.out.println("[FlightManager] getAllFlights");
                
        List<Flight> flightsList = new ArrayList<>();
        for (TTripDTO trip : tripsManager.findAllTrips()) {
            if (FlightUtils.isTripActive(trip)) {
                int emptySeats = tripsManager.getAvailableSeats(trip);
                flightsList.add(FlightUtils.createFlightFromTrip(trip, emptySeats));
            }
        }
        
        System.out.println("[FlightManager] getFlight. results found=" + flightsList.size());
        return flightsList;
    }
    
    @Override
    public List<Flight> getFlights(String origin, String destiny) {
        System.out.println("[FlightManager] getFlight. origin=" + origin + ", destiny=" + destiny);
        
        boolean compareOrigin = FlightUtils.validateParam(origin);
        boolean compareDestiny = FlightUtils.validateParam(destiny);
                
        List<Flight> flightsList = new ArrayList<>();
        for (TTripDTO trip : tripsManager.findAllTrips()) {
            if (FlightUtils.isTripActive(trip)) {
                
                if (compareOrigin) {
                    if (!FlightUtils.matchesOrigin(origin, trip)) {
                        continue;
                    }
                }
                
                if (compareDestiny) {
                    if (!FlightUtils.matchesDestiny(destiny, trip)) {
                        continue;
                    }
                }
                
                int emptySeats = tripsManager.getAvailableSeats(trip);
                flightsList.add(FlightUtils.createFlightFromTrip(trip, emptySeats));
            }
        }
        
        System.out.println("[FlightManager] getFlight. results found=" + flightsList.size());
        return flightsList;
    }
}