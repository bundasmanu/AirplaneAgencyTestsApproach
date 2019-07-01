/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author bruno
 */
@Local
public interface TTripFacadeLocal {

    void create(TTrip tTrip);

    void edit(TTrip tTrip);

    void remove(TTrip tTrip);

    TTrip find(Object id);

    List<TTrip> findAll();

    List<TTrip> findRange(int[] range);

    int count();
 
    List<TTrip> findAllNotDoneAndNotCanceled();
    
    List<TTrip> findLast(int number, String column);
}
