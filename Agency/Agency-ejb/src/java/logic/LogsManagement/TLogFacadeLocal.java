/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.LogsManagement;

import java.util.List;
import javax.ejb.Local;

@Local
public interface TLogFacadeLocal {

    void create(TLog tLog);

    void edit(TLog tLog);

    void remove(TLog tLog);

    TLog find(Object id);

    List<TLog> findAll();

    List<TLog> findRange(int[] range);
    
    List<TLog> findLast(int limit, String column);
    
    void removeAll();

    int count();
    
}