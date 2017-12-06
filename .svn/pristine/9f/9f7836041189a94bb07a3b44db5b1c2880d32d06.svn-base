package com.tayyarah.api.flight.tbo.model;

import java.io.Serializable;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Destination implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("Airport")
	private Airport Airport;
	@JsonProperty("ArrTime")
	private String ArrTime;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Destination() {
	}

	/**
	 * 
	 * @param Airport
	 * @param ArrTime
	 */
	public Destination(Airport Airport, String ArrTime) {
		this.Airport = Airport;
		this.ArrTime = ArrTime;
	}

	/**
	 * 
	 * @return
	 * The Airport
	 */
	@JsonProperty("Airport")
	public Airport getAirport() {
		return Airport;
	}

	/**
	 * 
	 * @param Airport
	 * The Airport
	 */
	@JsonProperty("Airport")
	public void setAirport(Airport Airport) {
		this.Airport = Airport;
	}

	/**
	 * 
	 * @return
	 * The ArrTime
	 */
	@JsonProperty("ArrTime")
	public String getArrTime() {
		return ArrTime;
	}

	/**
	 * 
	 * @param ArrTime
	 * The ArrTime
	 */
	@JsonProperty("ArrTime")
	public void setArrTime(String ArrTime) {
		this.ArrTime = ArrTime;
	}
}