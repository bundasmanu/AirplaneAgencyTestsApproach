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
@Table(name = "t_placefeedback")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TPlacefeedback.findAll", query = "SELECT t FROM TPlacefeedback t")
    , @NamedQuery(name = "TPlacefeedback.findById", query = "SELECT t FROM TPlacefeedback t WHERE t.id = :id")
    , @NamedQuery(name = "TPlacefeedback.findByScore", query = "SELECT t FROM TPlacefeedback t WHERE t.score = :score")})
public class TPlacefeedback implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "score")
    private int score;
    @JoinColumn(name = "placeid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TPlace placeid;
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TUser userid;

    public TPlacefeedback() {
    }

    public TPlacefeedback(Integer id) {
        this.id = id;
    }

    public TPlacefeedback(Integer id, int score) {
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

    public TPlace getPlaceid() {
        return placeid;
    }

    public void setPlaceid(TPlace placeid) {
        this.placeid = placeid;
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
        if (!(object instanceof TPlacefeedback)) {
            return false;
        }
        TPlacefeedback other = (TPlacefeedback) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logic.TripsManagement.TPlacefeedback[ id=" + id + " ]";
    }
    
}
