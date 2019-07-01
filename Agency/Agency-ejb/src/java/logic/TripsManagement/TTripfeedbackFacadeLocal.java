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
public interface TTripfeedbackFacadeLocal {

    void create(TTripfeedback tTripfeedback);

    void edit(TTripfeedback tTripfeedback);

    void remove(TTripfeedback tTripfeedback);

    TTripfeedback find(Object id);

    List<TTripfeedback> findAll();

    List<TTripfeedback> findRange(int[] range);

    int count();
    
}
