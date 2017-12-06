package com.tayyarah.api.flight.tbo.model;


import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Sector {

	@JsonProperty("Origin")
	private String origin;
	@JsonProperty("Destination")
	private String destination;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	* 
	* @return
	* The origin
	*/
	@JsonProperty("Origin")
	public String getOrigin() {
	return origin;
	}

	/**
	* 
	* @param origin
	* The Origin
	*/
	@JsonProperty("Origin")
	public void setOrigin(String origin) {
	this.origin = origin;
	}

	/**
	* 
	* @return
	* The destination
	*/
	@JsonProperty("Destination")
	public String getDestination() {
	return destination;
	}

	/**
	* 
	* @param destination
	* The Destination
	*/
	@JsonProperty("Destination")
	public void setDestination(String destination) {
	this.destination = destination;
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
