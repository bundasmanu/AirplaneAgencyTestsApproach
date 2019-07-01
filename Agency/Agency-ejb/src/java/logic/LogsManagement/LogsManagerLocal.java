/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.LogsManagement;

import logic.Log;
import java.util.List;
import javax.ejb.Local;
import logic.NoPermissionException;

@Local
public interface LogsManagerLocal {
    
    void sendLogMessage(String username, String msg, int date) throws NoPermissionException;
    
    List<Log> getAllLogs() throws NoPermissionException;
    
    List<Log> getLogs(int lines, String username) throws NoPermissionException;
    
    List<Log> getLogs(int count) throws NoPermissionException;
    
    boolean addLog(Log log);
    
    void removeLogs(String username) throws NoPermissionException;
    
}