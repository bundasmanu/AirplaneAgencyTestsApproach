/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;

/**
 *
 * @author bruno
 */
public class TSeatDTO implements Serializable{
    private Integer id;
    private String luggage;
    private Boolean auctioned;
    private Double price;
    private TTripDTO tripDTO;
    
    public TSeatDTO() {
    }

    public TSeatDTO(Integer id, String luggage, Boolean auctioned, Double price) {
        this.id = id;
        this.luggage = luggage;
        this.auctioned = auctioned;
        this.price = price;
    }

    public TSeatDTO(Integer id, String luggage, Boolean auctioned, Double price, TTripDTO tripDTO) {
        this.id = id;
        this.luggage = luggage;
        this.auctioned = auctioned;
        this.price = price;
        this.tripDTO = tripDTO;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLuggage() {
        return luggage;
    }

    public void setLuggage(String luggage) {
        this.luggage = luggage;
    }

    public Boolean getAuctioned() {
        return auctioned;
    }

    public void setAuctioned(Boolean auctioned) {
        this.auctioned = auctioned;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TTripDTO getTripDTO() {
        return tripDTO;
    }

    public void setTripDTO(TTripDTO tripDTO) {
        this.tripDTO = tripDTO;
    }

    @Override
    public String toString() {
        return "TSeatDTO{" + "id=" + id + ", luggage=" + luggage + ", auctioned=" + auctioned + ", price=" + price + '}';
    }

    
}
