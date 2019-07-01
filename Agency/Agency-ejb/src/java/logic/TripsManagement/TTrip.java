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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "t_trip")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TTrip.findAll", query = "SELECT t FROM TTrip t")
    , @NamedQuery(name = "TTrip.findById", query = "SELECT t FROM TTrip t WHERE t.id = :id")
    , @NamedQuery(name = "TTrip.findByPrice", query = "SELECT t FROM TTrip t WHERE t.price = :price")
    , @NamedQuery(name = "TTrip.findByDone", query = "SELECT t FROM TTrip t WHERE t.done = :done")
    , @NamedQuery(name = "TTrip.findByCanceled", query = "SELECT t FROM TTrip t WHERE t.canceled = :canceled")
    , @NamedQuery(name = "TTrip.findByDatetrip", query = "SELECT t FROM TTrip t WHERE t.datetrip = :datetrip")})
public class TTrip implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @Column(name = "done")
    private Boolean done;
    @Column(name = "canceled")
    private Boolean canceled;
    @Column(name = "datetrip")
    private Integer datetrip;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tripid")
    private Collection<TSeat> tSeatCollection;
    @JoinColumn(name = "airlineid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TAirline airlineid;
    @JoinColumn(name = "fromplaceid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TPlace fromplaceid;
    @JoinColumn(name = "toplaceid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TPlace toplaceid;
    @JoinColumn(name = "planeid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TPlane planeid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tripid")
    private Collection<TTripfeedback> tTripfeedbackCollection;

    public TTrip() {
    }

    public TTrip(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public Integer getDatetrip() {
        return datetrip;
    }

    public void setDatetrip(Integer datetrip) {
        this.datetrip = datetrip;
    }

    @XmlTransient
    public Collection<TSeat> getTSeatCollection() {
        return tSeatCollection;
    }

    public void setTSeatCollection(Collection<TSeat> tSeatCollection) {
        this.tSeatCollection = tSeatCollection;
    }

    public TAirline getAirlineid() {
        return airlineid;
    }

    public void setAirlineid(TAirline airlineid) {
        this.airlineid = airlineid;
    }

    public TPlace getFromplaceid() {
        return fromplaceid;
    }

    public void setFromplaceid(TPlace fromplaceid) {
        this.fromplaceid = fromplaceid;
    }

    public TPlace getToplaceid() {
        return toplaceid;
    }

    public void setToplaceid(TPlace toplaceid) {
        this.toplaceid = toplaceid;
    }

    public TPlane getPlaneid() {
        return planeid;
    }

    public void setPlaneid(TPlane planeid) {
        this.planeid = planeid;
    }

    @XmlTransient
    public Collection<TTripfeedback> getTTripfeedbackCollection() {
        return tTripfeedbackCollection;
    }

    public void setTTripfeedbackCollection(Collection<TTripfeedback> tTripfeedbackCollection) {
        this.tTripfeedbackCollection = tTripfeedbackCollection;
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
        if (!(object instanceof TTrip)) {
            return false;
        }
        TTrip other = (TTrip) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logic.TripsManagement.TTrip[ id=" + id + " ]";
    }
    
}
