/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author bruno
 */
public class TUserDTO implements Serializable{
    private Integer id;
    private int usertype;
    private String username;
    private String password;
    private String clientName;
    private Double balance;
    private Boolean accepted;
    private Collection<TPurchaseDTO> tPurchaseCollection;

    
    public TUserDTO() {
    }

    public TUserDTO(Integer id, int usertype, String username, String password, String clientName, Double balance, Boolean accepted, Collection<TPurchaseDTO> tPurchaseCollection) {
        this.id = id;
        this.usertype = usertype;
        this.username = username;
        this.password = password;
        this.clientName = clientName;
        this.balance = balance;
        this.accepted = accepted;
        this.tPurchaseCollection = tPurchaseCollection;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Collection<TPurchaseDTO> gettPurchaseCollection() {
        return tPurchaseCollection;
    }

    public void settPurchaseCollection(Collection<TPurchaseDTO> tPurchaseCollection) {
        this.tPurchaseCollection = tPurchaseCollection;
    }

    @Override
    public String toString() {
        return "TUserDTO{" + "id=" + id + ", usertype=" + usertype + ", username=" + username + ", password=" + password + ", clientName=" + clientName + ", balance=" + balance + ", accepted=" + accepted + ", tPurchaseCollection=" + tPurchaseCollection + '}';
    }

    
}
