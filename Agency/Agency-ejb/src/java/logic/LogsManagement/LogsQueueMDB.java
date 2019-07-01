/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.LogsManagement;

import java.io.Serializable;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import logic.Log;

@MessageDriven(
        activationConfig = {
            @ActivationConfigProperty(
                    propertyName = "destinationType",
                    propertyValue = "javax.jms.Queue"),
            @ActivationConfigProperty(
                    propertyName = "destinationLookup",
                    propertyValue = "jms/MyQueue"
            )
        }
)
public class LogsQueueMDB implements MessageListener {
    
    @EJB
    LogsManagerLocal logsManagerLocal;
    
    public LogsQueueMDB() {
        // Do nothing
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("LogsQueueMDB: message received. message=" + message);
        
        if (!(message instanceof ObjectMessage)) {
            System.out.println("LogsQueueMDB: not an ObjectMessage");
            return;
        }
        
        try {
            Serializable log = ((ObjectMessage) message).getObject();
            if (!(log instanceof Log)) {
                System.out.println("LogsQueueMDB: not an TLogDTO");
                return;
            }
            
            boolean result = logsManagerLocal.addLog((Log) log);
            System.out.println("LogsQueueMDB: added log. logDTO=" + log + ", result=" + result);
            
        } catch (JMSException ex) {
            System.out.println("LogsQueueMDB: JMSException=" + ex);
        }
    }
    
}