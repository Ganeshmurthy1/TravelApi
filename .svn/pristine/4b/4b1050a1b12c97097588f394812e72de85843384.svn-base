package com.tayyarah.api.flight.tbo.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class PriceBDRequestSegment {

	@JsonProperty("TripIndicator")
	private Integer tripIndicator;
	@JsonProperty("SegmentIndicator")
	private Integer segmentIndicator;
	@JsonProperty("Airline")
	private Airline airline;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	* 
	* @return
	* The tripIndicator
	*/
	@JsonProperty("TripIndicator")
	public Integer getTripIndicator() {
	return tripIndicator;
	}

	/**
	* 
	* @param tripIndicator
	* The TripIndicator
	*/
	@JsonProperty("TripIndicator")
	public void setTripIndicator(Integer tripIndicator) {
	this.tripIndicator = tripIndicator;
	}

	/**
	* 
	* @return
	* The segmentIndicator
	*/
	@JsonProperty("SegmentIndicator")
	public Integer getSegmentIndicator() {
	return segmentIndicator;
	}

	/**
	* 
	* @param segmentIndicator
	* The SegmentIndicator
	*/
	@JsonProperty("SegmentIndicator")
	public void setSegmentIndicator(Integer segmentIndicator) {
	this.segmentIndicator = segmentIndicator;
	}

	/**
	* 
	* @return
	* The airline
	*/
	@JsonProperty("Airline")
	public Airline getAirline() {
	return airline;
	}

	/**
	* 
	* @param airline
	* The Airline
	*/
	@JsonProperty("Airline")
	public void setAirline(Airline airline) {
	this.airline = airline;
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
