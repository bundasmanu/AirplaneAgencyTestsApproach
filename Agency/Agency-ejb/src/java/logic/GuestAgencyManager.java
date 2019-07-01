/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import logic.TripsManagement.TripsManagerLocal;

@Stateless
public class GuestAgencyManager implements GuestAgencyManagerRemote {

    @EJB
    TripsManagerLocal tripsManager;
    
    public GuestAgencyManager() {
        // Do nothing
    }
    
    @Override
    public List<TTripDTO> getCheapestTrips(int count) {
        return tripsManager.getCheapeastTrips(count);
    }
    
}