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
@Table(name = "t_place")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TPlace.findAll", query = "SELECT t FROM TPlace t")
    , @NamedQuery(name = "TPlace.findById", query = "SELECT t FROM TPlace t WHERE t.id = :id")
    , @NamedQuery(name = "TPlace.findByCountry", query = "SELECT t FROM TPlace t WHERE t.country = :country")
    , @NamedQuery(name = "TPlace.findByCity", query = "SELECT t FROM TPlace t WHERE t.city = :city")
    , @NamedQuery(name = "TPlace.findByAddress", query = "SELECT t FROM TPlace t WHERE t.address = :address")})
public class TPlace implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "country")
    private String country;
    @Basic(optional = false)
    @Column(name = "city")
    private String city;
    @Column(name = "address")
    private String address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "placeid")
    private Collection<TPlacefeedback> tPlacefeedbackCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fromplaceid")
    private Collection<TTrip> tTripCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "toplaceid")
    private Collection<TTrip> tTripCollection1;

    public TPlace() {
    }

    public TPlace(Integer id) {
        this.id = id;
    }

    public TPlace(Integer id, String country, String city) {
        this.id = id;
        this.country = country;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlTransient
    public Collection<TPlacefeedback> getTPlacefeedbackCollection() {
        return tPlacefeedbackCollection;
    }

    public void setTPlacefeedbackCollection(Collection<TPlacefeedback> tPlacefeedbackCollection) {
        this.tPlacefeedbackCollection = tPlacefeedbackCollection;
    }

    @XmlTransient
    public Collection<TTrip> getTTripCollection() {
        return tTripCollection;
    }

    public void setTTripCollection(Collection<TTrip> tTripCollection) {
        this.tTripCollection = tTripCollection;
    }

    @XmlTransient
    public Collection<TTrip> getTTripCollection1() {
        return tTripCollection1;
    }

    public void setTTripCollection1(Collection<TTrip> tTripCollection1) {
        this.tTripCollection1 = tTripCollection1;
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
        if (!(object instanceof TPlace)) {
            return false;
        }
        TPlace other = (TPlace) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logic.TripsManagement.TPlace[ id=" + id + " ]";
    }
    
}
