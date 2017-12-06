package com.tayyarah.flight.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement (name="flightsearching")
public class FlightsearchList
{
    private List<Flightsearch> flightsearching = new ArrayList<Flightsearch>();
 
    public List<Flightsearch> getFlightsearch() {
        return flightsearching;
    }
 
    public void setFlightsearch(List<Flightsearch> flightsearching) {
        this.flightsearching = flightsearching;
    }
}