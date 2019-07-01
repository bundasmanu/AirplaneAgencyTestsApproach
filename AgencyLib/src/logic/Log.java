/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;

public class Log implements Serializable {
    
    private final String username;
    private final String msg;
    private final int date;
    
    public Log(String username, String msg, int date) {
        this.username = username;
        this.msg = msg;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public String getMsg() {
        return msg;
    }

    public int getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Log {" +
                "username=" + username + 
                ", msg=" + msg +
                ", date=" + date + "}";
    }
}