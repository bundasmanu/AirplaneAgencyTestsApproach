/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import logic.UsersManagement.TUser;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "t_tripfeedback")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TTripfeedback.findAll", query = "SELECT t FROM TTripfeedback t")
    , @NamedQuery(name = "TTripfeedback.findById", query = "SELECT t FROM TTripfeedback t WHERE t.id = :id")
    , @NamedQuery(name = "TTripfeedback.findByScore", query = "SELECT t FROM TTripfeedback t WHERE t.score = :score")})
public class TTripfeedback implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "score")
    private int score;
    @JoinColumn(name = "tripid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TTrip tripid;
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TUser userid;

    public TTripfeedback() {
    }

    public TTripfeedback(Integer id) {
        this.id = id;
    }

    public TTripfeedback(Integer id, int score) {
        this.id = id;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public TTrip getTripid() {
        return tripid;
    }

    public void setTripid(TTrip tripid) {
        this.tripid = tripid;
    }

    public TUser getUserid() {
        return userid;
    }

    public void setUserid(TUser userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TTripfeedback)) {
            return false;
        }
        TTripfeedback other = (TTripfeedback) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logic.TripsManagement.TTripfeedback[ id=" + id + " ]";
    }
    
}
