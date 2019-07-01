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
public interface TPlaneFacadeLocal {

    void create(TPlane tPlane);

    void edit(TPlane tPlane);

    void remove(TPlane tPlane);

    TPlane find(Object id);

    List<TPlane> findAll();

    List<TPlane> findRange(int[] range);

    int count();
    
}
