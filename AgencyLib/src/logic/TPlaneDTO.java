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
public class TPlaneDTO implements Serializable{
    private Integer id;
    private String planeName;
    private int planeLimit;

    public TPlaneDTO() {
    }

    public TPlaneDTO(int id, String planeName, int planeLimit) {
        this.id = id;
        this.planeName = planeName;
        this.planeLimit = planeLimit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName;
    }

    public int getPlaneLimit() {
        return planeLimit;
    }

    public void setPlaneLimit(int planeLimit) {
        this.planeLimit = planeLimit;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.planeName);
        hash = 41 * hash + this.planeLimit;
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
        final TPlaneDTO other = (TPlaneDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.planeLimit != other.planeLimit) {
            return false;
        }
        if (!Objects.equals(this.planeName, other.planeName)) {
            return false;
        }
        return true;
    }
    

    @Override
    public String toString() {
        return "TPlaneDTO{" + "id=" + id + ", planeName=" + planeName + ", planeLimit=" + planeLimit + '}';
    }
    
}
