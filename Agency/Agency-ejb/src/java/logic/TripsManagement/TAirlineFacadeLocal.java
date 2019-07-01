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
public interface TAirlineFacadeLocal {

    void create(TAirline tAirline);

    void edit(TAirline tAirline);

    void remove(TAirline tAirline);

    TAirline find(Object id);

    List<TAirline> findAll();

    List<TAirline> findRange(int[] range);

    int count();
    
}
