package com.tayyarah.api.flight.tbo.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"Origin",
"Destination",
"FlightCabinClass",
"PreferredArrivalTime",
"PreferredDepartureTime"
})
public class Segment implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@JsonProperty("Origin")
private String Origin;
@JsonProperty("Destination")
private String Destination;
@JsonProperty("FlightCabinClass")
private String FlightCabinClass;
@JsonProperty("PreferredArrivalTime")
private String PreferredArrivalTime;
@JsonProperty("PreferredDepartureTime")
private String PreferredDepartureTime;

/**
* No args constructor for use in serialization
* 
*/
public Segment() {
}

/**
* 
* @param PreferredDepartureTime
* @param PreferredArrivalTime
* @param FlightCabinClass
* @param Origin
* @param Destination
*/
public Segment(String Origin, String Destination, String FlightCabinClass, String PreferredArrivalTime, String PreferredDepartureTime) {
this.Origin = Origin;
this.Destination = Destination;
this.FlightCabinClass = FlightCabinClass;
this.PreferredArrivalTime = PreferredArrivalTime;
this.PreferredDepartureTime = PreferredDepartureTime;
}

/**
* 
* @return
* The Origin
*/
@JsonProperty("Origin")
public String getOrigin() {
return Origin;
}

/**
* 
* @param Origin
* The Origin
*/
@JsonProperty("Origin")
public void setOrigin(String Origin) {
this.Origin = Origin;
}


/**
* 
* @return
* The Destination
*/
@JsonProperty("Destination")
public String getDestination() {
return Destination;
}

/**
* 
* @param Destination
* The Destination
*/
@JsonProperty("Destination")
public void setDestination(String Destination) {
this.Destination = Destination;
}


/**
* 
* @return
* The FlightCabinClass
*/
@JsonProperty("FlightCabinClass")
public String getFlightCabinClass() {
return FlightCabinClass;
}

/**
* 
* @param FlightCabinClass
* The FlightCabinClass
*/
@JsonProperty("FlightCabinClass")
public void setFlightCabinClass(String FlightCabinClass) {
this.FlightCabinClass = FlightCabinClass;
}

/**
* 
* @return
* The PreferredArrivalTime
*/
@JsonProperty("PreferredArrivalTime")
public String getPreferredArrivalTime() {
return PreferredArrivalTime;
}

/**
* 
* @param PreferredArrivalTime
* The PreferredArrivalTime
*/
@JsonProperty("PreferredArrivalTime")
public void setPreferredArrivalTime(String PreferredArrivalTime) {
this.PreferredArrivalTime = PreferredArrivalTime;
}



/**
* 
* @return
* The PreferredDepartureTime
*/
@JsonProperty("PreferredDepartureTime")
public String getPreferredDepartureTime() {
return PreferredDepartureTime;
}

/**
* 
* @param PreferredDepartureTime
* The PreferredDepartureTime
*/
@JsonProperty("PreferredDepartureTime")
public void setPreferredDepartureTime(String PreferredDepartureTime) {
this.PreferredDepartureTime = PreferredDepartureTime;
}


}
/*package com.tbo.flight.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Segment
{
   

	private String PreferredDepartureTime;

    private String PreferredArrivalTime;

    private String FlightCabinClass;

    private String Origin;

    private String Destination;

    public String getPreferredDepartureTime ()
    {
        return PreferredDepartureTime;
    }

    public void setPreferredDepartureTime (String PreferredDepartureTime)
    {
        this.PreferredDepartureTime = PreferredDepartureTime;
    }

    public String getPreferredArrivalTime ()
    {
        return PreferredArrivalTime;
    }

    public void setPreferredArrivalTime (String PreferredArrivalTime)
    {
        this.PreferredArrivalTime = PreferredArrivalTime;
    }

    public String getFlightCabinClass ()
    {
        return FlightCabinClass;
    }

    public void setFlightCabinClass (String FlightCabinClass)
    {
        this.FlightCabinClass = FlightCabinClass;
    }

    public String getOrigin ()
    {
        return Origin;
    }

    public void setOrigin (String Origin)
    {
        this.Origin = Origin;
    }

    public String getDestination ()
    {
        return Destination;
    }

    public void setDestination (String Destination)
    {
        this.Destination = Destination;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [PreferredDepartureTime = "+PreferredDepartureTime+", PreferredArrivalTime = "+PreferredArrivalTime+", FlightCabinClass = "+FlightCabinClass+", Origin = "+Origin+", Destination = "+Destination+"]";
    }
}
			*/