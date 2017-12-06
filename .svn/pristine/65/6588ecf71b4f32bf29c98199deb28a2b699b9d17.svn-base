package com.tayyarah.flight.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FareRules implements Serializable
{
    
	private static final long serialVersionUID = 1L;	
	private  List<FareRule>  fareRule;
    private String travelerType;

    public List<FareRule> getFareRule ()
    {
       return fareRule;
    }
    public void setFareRule (List<FareRule> fareRule)
    {
        this.fareRule = fareRule;
    }
    public String getTravelerType ()
    {
        return travelerType;
    }
    public void setTravelerType (String travelerType)
    {
        this.travelerType = travelerType;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [fareRule = "+fareRule+", travelerType = "+travelerType+"]";
    }
}	
			
