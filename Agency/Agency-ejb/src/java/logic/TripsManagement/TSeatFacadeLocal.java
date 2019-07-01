/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import java.util.List;
import javax.ejb.Local;
import logic.UsersManagement.TUser;

/**
 *
 * @author bruno
 */
@Local
public interface TSeatFacadeLocal {

    void create(TSeat tSeat);

    TSeat createAndGetEntity(TSeat tSeat);
    
    void edit(TSeat tSeat);

    void remove(TSeat tSeat);

    TSeat find(Object id);

    List<TSeat> findAll();

    List<TSeat> findRange(int[] range);

    int count();
    
    List<TSeat> findBoughtSeatsOfTrip(TTrip trip);
    
    List<TSeat> findAuctionedSeats();
    
    List<TSeat> findAuctionedSeatsOfTrip(TTrip trip);
    
    List<TSeat> findAuctionedSeatsOfUser(TUser user);

    List<TSeat> findAuctioningSeatsOfUser(TUser user);
    
}
