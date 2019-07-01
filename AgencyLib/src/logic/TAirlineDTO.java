/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author bruno
 */
public class TAirlineDTO implements Serializable{
    private Integer id;
    private String airlineName;
    private String phoneNumber;

    public TAirlineDTO() {
    }

    
    public TAirlineDTO(Integer id, String airlineName, String phoneNumber) {
        this.id = id;
        this.airlineName = airlineName;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.airlineName);
        hash = 97 * hash + Objects.hashCode(this.phoneNumber);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TAirlineDTO other = (TAirlineDTO) obj;
        if (!Objects.equals(this.airlineName, other.airlineName)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TAirlineDTO{" + "id=" + id + ", airlineName=" + airlineName + ", phoneNumber=" + phoneNumber + '}';
    }

    
    
}
