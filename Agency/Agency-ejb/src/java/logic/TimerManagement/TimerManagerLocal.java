/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TimerManagement;

import javax.ejb.Local;

/**
 *
 * @author bruno
 */
@Local
public interface TimerManagerLocal {
    int getDate();
    boolean setDate(int date);
    boolean setDurationTimer(long durationMinuts);
    String getTimerInformation();
}
