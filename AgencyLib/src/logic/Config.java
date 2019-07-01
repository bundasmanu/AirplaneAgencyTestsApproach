/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author bruno
 */


public class Config {
    public static final int OPERATOR = 0;
    public static final int CLIENT = 1;
    
    
    public static final String MSG_NO_PERMISSION = "No permissions to invoke the method. Just an accepted operator or client has permissions.";
    public static final String MSG_NO_PERMISSION_OPERATOR = "No permissions to invoke the method. Just an accepted operator has permissions.";
    public static final String MSG_NO_PERMISSION_FEEDBACK = "No permission to change the place's feedback of other user.";
    public static final String MSG_NO_PERMISSION_LOG = "No permission to add logg: user does not exist.";
    public static final String MSG_NO_PERMISSION_CHANGE_TRIP = "No permissions to change the trip. It already has bought seats. To perform any alteration, you must cancel the trip.";
    public static final String MSG_NO_PERMISSION_MONEY = "No permissions to buy the seat/seats to the trip. The user doesn't have money.";
    public static final String MSG_NO_PERMISSION_TRIP_DONE = "No permissions to finish the purchase because one trip is already done.";
    public static final String MSG_NO_PERMISSION_PLANE_LIMIT_EXCEDED = "No permissions to finish the purchase because a plane already fullfit";
    public static final String MSG_NO_PERMISSION_TRIED_BUY_AUCTIONEDSEAT = "No permissions to finish the purchase because the user tried to buy auctioned seat.";
    
            
    public static final long DEFAULT_TIMER= 60;

    
}