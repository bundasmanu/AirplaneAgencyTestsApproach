/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.websearch;

import java.util.List;
import javax.ejb.Local;

@Local
public interface FlightManagerLocal {
    
    List<Flight> getAllFlights();
    
    List<Flight> getFlights(String origin, String destiny);
    
}