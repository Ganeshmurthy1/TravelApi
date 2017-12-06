package com.tayyarah.flight.model;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FlightSegmentsGroup implements Serializable 
{
    
	private static final long serialVersionUID = 1L;
    private List<FlightSegments>  flightSegments;

	@Override
	public String toString() {
		return "ClassPojo [flightSegments="
				+ flightSegments;
	}

	/**
	 * @return the flightSegments
	 */
	public List<FlightSegments> getFlightSegments() {
		return flightSegments;
	}

	/**
	 * @param flightSegments the flightSegments to set
	 */
	public void setFlightSegments(List<FlightSegments> flightSegments) {
		this.flightSegments = flightSegments;
	}

   
}
			
