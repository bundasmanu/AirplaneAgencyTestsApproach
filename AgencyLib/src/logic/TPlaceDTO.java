/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author bruno
 */
public class TPlaceDTO implements Serializable{
    
    private Integer id;
    private String country;
    private String city;
    private String address;
    private Collection<TPlaceFeedbackDTO> tPlacefeedbackDTOCollection;

    public TPlaceDTO() {
    }

    public TPlaceDTO(Integer id, String country, String city, String address, Collection<TPlaceFeedbackDTO> tPlacefeedbackDTOCollection) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.address = address;
        this.tPlacefeedbackDTOCollection = tPlacefeedbackDTOCollection;
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

    public Collection<TPlaceFeedbackDTO> gettPlacefeedbackDTOCollection() {
        return tPlacefeedbackDTOCollection;
    }

    public void settPlacefeedbackDTOCollection(Collection<TPlaceFeedbackDTO> tPlacefeedbackDTOCollection) {
        this.tPlacefeedbackDTOCollection = tPlacefeedbackDTOCollection;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.country);
        hash = 37 * hash + Objects.hashCode(this.city);
        hash = 37 * hash + Objects.hashCode(this.address);
        hash = 37 * hash + Objects.hashCode(this.tPlacefeedbackDTOCollection);
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
        final TPlaceDTO other = (TPlaceDTO) obj;
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.tPlacefeedbackDTOCollection, other.tPlacefeedbackDTOCollection)) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        return "TPlaceDTO{" + "id=" + id + ", country=" + country + ", city=" + city + ", address=" + address + ", tPlacefeedbackDTOCollection=" + tPlacefeedbackDTOCollection + '}';
    }
}
