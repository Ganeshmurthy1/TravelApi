package com.tayyarah.api.flight.tbo.model;
import java.util.HashMap;
import java.util.Map;

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
"PreferredDepartureTime"
})
public class CalendarSegment {
	@JsonProperty("Origin")
	private String origin;
	@JsonProperty("Destination")
	private String destination;
	@JsonProperty("FlightCabinClass")
	private String flightCabinClass;
	@JsonProperty("PreferredDepartureTime")
	private String preferredDepartureTime;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("Origin")
	public String getOrigin() {
	return origin;
	}

	@JsonProperty("Origin")
	public void setOrigin(String origin) {
	this.origin = origin;
	}

	@JsonProperty("Destination")
	public String getDestination() {
	return destination;
	}

	@JsonProperty("Destination")
	public void setDestination(String destination) {
	this.destination = destination;
	}

	@JsonProperty("FlightCabinClass")
	public String getFlightCabinClass() {
	return flightCabinClass;
	}

	@JsonProperty("FlightCabinClass")
	public void setFlightCabinClass(String flightCabinClass) {
	this.flightCabinClass = flightCabinClass;
	}

	@JsonProperty("PreferredDepartureTime")
	public String getPreferredDepartureTime() {
	return preferredDepartureTime;
	}

	@JsonProperty("PreferredDepartureTime")
	public void setPreferredDepartureTime(String preferredDepartureTime) {
	this.preferredDepartureTime = preferredDepartureTime;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}

}
