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
public interface TPlaceFacadeLocal {

    void create(TPlace tPlace);

    void edit(TPlace tPlace);

    void remove(TPlace tPlace);

    TPlace find(Object id);

    List<TPlace> findAll();

    List<TPlace> findRange(int[] range);

    int count();
    
}
