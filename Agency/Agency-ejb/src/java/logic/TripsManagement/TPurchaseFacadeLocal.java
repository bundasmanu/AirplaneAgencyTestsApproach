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
public interface TPurchaseFacadeLocal {

    void create(TPurchase tPurchase);

    void edit(TPurchase tPurchase);

    void remove(TPurchase tPurchase);

    TPurchase find(Object id);

    List<TPurchase> findAll();

    List<TPurchase> findRange(int[] range);

    int count();
    
    List<TPurchase> findAllNotDonePurchasesOfUser(TUser user);

    
}
