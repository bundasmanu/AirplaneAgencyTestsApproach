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
public class TTripDTO implements Serializable{
    private Integer id;
    private Double price;
    private Boolean done;
    private Boolean canceled;
    private Integer datetrip;
    private TAirlineDTO airlineDTO;
    private TPlaceDTO fromPlaceDTO;
    private TPlaceDTO toPlaceDTO;
    private TPlaneDTO planeDTO;
    private Collection<TTripFeedbackDTO> tTripfeedbackCollection;

    public TTripDTO() {
    }

    public TTripDTO(Integer id, Double price, Boolean done, Boolean canceled, Integer datetrip, TAirlineDTO airlineDTO, TPlaceDTO fromPlaceDTO, TPlaceDTO toPlaceDTO, TPlaneDTO planeDTO, Collection<TTripFeedbackDTO> tTripfeedbackCollection) {
        this.id = id;
        this.price = price;
        this.done = done;
        this.canceled = canceled;
        this.datetrip = datetrip;
        this.airlineDTO = airlineDTO;
        this.fromPlaceDTO = fromPlaceDTO;
        this.toPlaceDTO = toPlaceDTO;
        this.planeDTO = planeDTO;
        this.tTripfeedbackCollection = tTripfeedbackCollection;
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

    public TAirlineDTO getAirlineDTO() {
        return airlineDTO;
    }

    public void setAirlineDTO(TAirlineDTO airlineDTO) {
        this.airlineDTO = airlineDTO;
    }

    public TPlaceDTO getFromPlaceDTO() {
        return fromPlaceDTO;
    }

    public void setFromPlaceDTO(TPlaceDTO fromPlaceDTO) {
        this.fromPlaceDTO = fromPlaceDTO;
    }

    public TPlaceDTO getToPlaceDTO() {
        return toPlaceDTO;
    }

    public void setToPlaceDTO(TPlaceDTO toPlaceDTO) {
        this.toPlaceDTO = toPlaceDTO;
    }

    public TPlaneDTO getPlaneDTO() {
        return planeDTO;
    }

    public void setPlaneDTO(TPlaneDTO planeDTO) {
        this.planeDTO = planeDTO;
    }

    public Collection<TTripFeedbackDTO> gettTripfeedbackCollection() {
        return tTripfeedbackCollection;
    }

    public void settTripfeedbackCollection(Collection<TTripFeedbackDTO> tTripfeedbackCollection) {
        this.tTripfeedbackCollection = tTripfeedbackCollection;
    }

    @Override
    public String toString() {
        return "TTripDTO{" + "id=" + id + ", price=" + price + ", done=" + done + ", canceled=" + canceled + ", datetrip=" + datetrip + ", airlineDTO=" + airlineDTO + ", fromPlaceDTO=" + fromPlaceDTO + ", toPlaceDTO=" + toPlaceDTO + ", planeDTO=" + planeDTO + ", tTripfeedbackCollection=" + tTripfeedbackCollection + '}';
    }

}
