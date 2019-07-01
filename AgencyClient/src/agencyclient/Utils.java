/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agencyclient;

import logic.Log;
import logic.LogTypes;
import logic.SignInValue;

/** Class for utility methods */
public class Utils {
    
    private static final String FINISH_PURCHASE_LOG_MESSAGE = "finished purchase";
    private static final String CREATE_TRIP_LOG_MESSAGE = "created trip";
    private static final String FINISH_TRIP_LOG_MESSAGE = "finished trip";
    
    // signup
    
    public static String getSignUpValueString(SignInValue value, String username) {
        switch (value) {
            case SUCCESS:
                return "Welcome to the Agency, " + username + ".";
                
            case NOT_FOUND:
                return "No account found with this username.";
                
            case NOT_ACCEPTED:
                return "Account is stil in validation process.";
                
            case WRONG_CREDENTIALS:
                return "Username or Password wrong.";
                
            default:
                throw new IllegalStateException("Unexpected value found:" + value);
        }
    }
    
    // logs
    
    public static String getFormattedLogMessage(Log log) {
        String date = "[" + log.getDate() + "] | ";
        String user = "username=" + log.getUsername() + " | ";
        String action = "action=" + getActionTextFromLogType(log.getMsg());
        return date + user + action;
    }
    
    public static String getActionTextFromLogType(String logType) {
        switch (logType) {
            case LogTypes.FINISH_PURCHASE:
                return FINISH_PURCHASE_LOG_MESSAGE;
                
            case LogTypes.CREATE_TRIP:
                return CREATE_TRIP_LOG_MESSAGE;
                
            case LogTypes.FINISH_TRIP:
                return FINISH_TRIP_LOG_MESSAGE;
                
            default:
                System.out.println("Unknown log type:" + logType);
                return logType; 
        }
    }
    
}
