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
public class TPurchaseDTO implements Serializable{
    private Integer id;
    private Boolean done;
    private Collection<TSeatDTO> tSeatCollection;

    public TPurchaseDTO() {
    }

    public TPurchaseDTO(Integer id, Boolean done, Collection<TSeatDTO> tSeatCollection) {
        this.id = id;
        this.done = done;
        this.tSeatCollection = tSeatCollection;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Collection<TSeatDTO> gettSeatCollection() {
        return tSeatCollection;
    }

    public void settSeatCollection(Collection<TSeatDTO> tSeatCollection) {
        this.tSeatCollection = tSeatCollection;
    }

    @Override
    public String toString() {
        return "TPurchaseDTO{" + "id=" + id + ", done=" + done + ", tSeatCollection=" + tSeatCollection + '}';
    }

    
}
