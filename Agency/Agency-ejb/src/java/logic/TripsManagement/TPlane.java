/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "t_plane")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TPlane.findAll", query = "SELECT t FROM TPlane t")
    , @NamedQuery(name = "TPlane.findById", query = "SELECT t FROM TPlane t WHERE t.id = :id")
    , @NamedQuery(name = "TPlane.findByPlanename", query = "SELECT t FROM TPlane t WHERE t.planename = :planename")
    , @NamedQuery(name = "TPlane.findByPlanelimit", query = "SELECT t FROM TPlane t WHERE t.planelimit = :planelimit")})
public class TPlane implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "planename")
    private String planename;
    @Basic(optional = false)
    @Column(name = "planelimit")
    private int planelimit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planeid")
    private Collection<TTrip> tTripCollection;

    public TPlane() {
    }

    public TPlane(Integer id) {
        this.id = id;
    }

    public TPlane(Integer id, String planename, int planelimit) {
        this.id = id;
        this.planename = planename;
        this.planelimit = planelimit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanename() {
        return planename;
    }

    public void setPlanename(String planename) {
        this.planename = planename;
    }

    public int getPlanelimit() {
        return planelimit;
    }

    public void setPlanelimit(int planelimit) {
        this.planelimit = planelimit;
    }

    @XmlTransient
    public Collection<TTrip> getTTripCollection() {
        return tTripCollection;
    }

    public void setTTripCollection(Collection<TTrip> tTripCollection) {
        this.tTripCollection = tTripCollection;
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
        if (!(object instanceof TPlane)) {
            return false;
        }
        TPlane other = (TPlane) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logic.TripsManagement.TPlane[ id=" + id + " ]";
    }
    
}
