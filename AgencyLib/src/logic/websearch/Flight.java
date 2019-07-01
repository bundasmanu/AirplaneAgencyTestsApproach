/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.websearch;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Flight {

    @XmlElement
    private Double price;
    
    @XmlElement
    private int emptySeats;
    
    @XmlElement
    private String origin;
    
    @XmlElement
    private String destiny;
    
    public Flight() {
        // Do nothing
    }
    
    public Flight(Double price, int emptySeats, String origin, String destiny) {
        this.price = price;
        this.emptySeats = emptySeats;
        this.origin = origin;
        this.destiny = destiny;
    }

    public Double getPrice() {
        return price;
    }

    public int getEmptySeats() {
        return emptySeats;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestiny() {
        return destiny;
    }
    
    @Override
    public String toString() {
        return "Flight{" + "price=" + price + ", emptySeats=" + emptySeats + ", origin=" + origin + ", destiny=" + destiny + '}';
    }
}