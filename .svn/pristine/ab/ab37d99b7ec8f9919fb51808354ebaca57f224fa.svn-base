package com.tayyarah.api.flight.tbo.model;

import java.io.Serializable;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({
	"Airport",
	"DepTime"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Origin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("Airport")
	private com.tayyarah.api.flight.tbo.model.Airport Airport;
	@JsonProperty("DepTime")
	private String DepTime;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Origin() {
	}

	/**
	 * 
	 * @param Airport
	 * @param DepTime
	 */
	public Origin(com.tayyarah.api.flight.tbo.model.Airport Airport, String DepTime) {
		this.Airport = Airport;
		this.DepTime = DepTime;
	}

	/**
	 * 
	 * @return
	 * The Airport
	 */
	@JsonProperty("Airport")
	public com.tayyarah.api.flight.tbo.model.Airport getAirport() {
		return Airport;
	}

	/**
	 * 
	 * @param Airport
	 * The Airport
	 */
	@JsonProperty("Airport")
	public void setAirport(com.tayyarah.api.flight.tbo.model.Airport Airport) {
		this.Airport = Airport;
	}

	/**
	 * 
	 * @return
	 * The DepTime
	 */
	@JsonProperty("DepTime")
	public String getDepTime() {
		return DepTime;
	}

	/**
	 * 
	 * @param DepTime
	 * The DepTime
	 */
	@JsonProperty("DepTime")
	public void setDepTime(String DepTime) {
		this.DepTime = DepTime;
	}
}