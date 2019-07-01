/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.LogsManagement;

import logic.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import logic.Config;
import logic.DTOFactory;
import logic.NoPermissionException;
import logic.TUserDTO;
import logic.UsersManagement.UsersManagerLocal;

@Singleton
public class LogsManager implements LogsManagerLocal {
    
    private static final String DATE_COLUMN = "datelog";
  
    @EJB
    UsersManagerLocal userManager;
    
    @Inject
    @JMSConnectionFactory("jms/MyConnFactory")
    private JMSContext jmsContext;
    
    @Resource(lookup = "jms/MyConnFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(mappedName = "jms/MyQueue")
    Queue logsQueue;
    
    @EJB
    TLogFacadeLocal logFacade;
    
    public LogsManager() {
        // Do nothing
    }
    
    @Override
    public void sendLogMessage(String username, String msg, int date) throws NoPermissionException {        
        if (username == null || username.isEmpty())
                throw new NoPermissionException(Config.MSG_NO_PERMISSION_LOG);
        
        
        TUserDTO userDTO = userManager.getTUserDTO(username);
        
        System.out.println("sendLogMessage: user retrieved:" + userDTO);
        
        if(userDTO == null)
            throw new NoPermissionException(Config.MSG_NO_PERMISSION_LOG);
        
        Log log = new Log(username, msg, date);
        ObjectMessage message = jmsContext.createObjectMessage(log);
        jmsContext.createProducer().send(logsQueue, message);
    }
    
    @Override
    public List<Log> getAllLogs() {
        List<Log> logList = new ArrayList<>();
        for (TLog log : logFacade.findAll()) {
            logList.add(DTOFactory.getTLogDTOFromTLog(log));
        }
        
        return logList;
    }

    @Override
    public List<Log> getLogs(int lines, String username) throws NoPermissionException {
        userManager.verifyPermission(username, Config.OPERATOR);

        List<TLog> retrievedList;
        if (lines > 0) {
            retrievedList = logFacade.findLast(lines, DATE_COLUMN);
            Collections.reverse(retrievedList);
        
        } else {
            retrievedList = logFacade.findAll();
        }
        
        List<Log> logList = new ArrayList<>();
        for (TLog log : retrievedList) {
            logList.add(DTOFactory.getTLogDTOFromTLog(log));
        }
        
        return logList;
    }

    @Override
    public boolean addLog(Log log) {
        TLog newLog = DTOFactory.getTLogFromTLogDTO(log);
        
        logFacade.create(newLog);
        return true;
    }

    @Override
    public List<Log> getLogs(int count) throws NoPermissionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeLogs(String username) throws NoPermissionException {
        userManager.verifyPermission(username, Config.OPERATOR);
        
        logFacade.removeAll();
    }
}