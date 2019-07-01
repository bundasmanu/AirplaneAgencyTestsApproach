/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.UsersManagement;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author bruno
 */
@Local
public interface TUserFacadeLocal {

    void create(TUser tUser);

    void edit(TUser tUser);

    void remove(TUser tUser);

    TUser find(Object id);

    List<TUser> findAll();

    List<TUser> findRange(int[] range);

    int count();
    
}
