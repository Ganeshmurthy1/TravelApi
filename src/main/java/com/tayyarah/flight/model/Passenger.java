package com.tayyarah.flight.model;

import java.io.Serializable;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Passenger implements Serializable
{
    
	private static final long serialVersionUID = 1L;
	
	private String id;

    private String type;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", type = "+type+"]";
    }
}
			
			
