package com.tayyarah.api.flight.tbo.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchResult {
	@JsonProperty("AirlineCode")
	private String airlineCode;
	@JsonProperty("AirlineName")
	private String airlineName;
	@JsonProperty("DepartureDate")
	private String departureDate;
	@JsonProperty("IsLowestFareOfMonth")
	private Boolean isLowestFareOfMonth;
	@JsonProperty("Fare")
	private Double fare;
	@JsonProperty("BaseFare")
	private Integer baseFare;
	@JsonProperty("Tax")
	private Integer tax;
	@JsonProperty("OtherCharges")
	private Double otherCharges;
	@JsonProperty("FuelSurcharge")
	private Integer fuelSurcharge;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("AirlineCode")
	public String getAirlineCode() {
	return airlineCode;
	}

	@JsonProperty("AirlineCode")
	public void setAirlineCode(String airlineCode) {
	this.airlineCode = airlineCode;
	}

	@JsonProperty("AirlineName")
	public String getAirlineName() {
	return airlineName;
	}

	@JsonProperty("AirlineName")
	public void setAirlineName(String airlineName) {
	this.airlineName = airlineName;
	}

	@JsonProperty("DepartureDate")
	public String getDepartureDate() {
	return departureDate;
	}

	@JsonProperty("DepartureDate")
	public void setDepartureDate(String departureDate) {
	this.departureDate = departureDate;
	}

	@JsonProperty("IsLowestFareOfMonth")
	public Boolean getIsLowestFareOfMonth() {
	return isLowestFareOfMonth;
	}

	@JsonProperty("IsLowestFareOfMonth")
	public void setIsLowestFareOfMonth(Boolean isLowestFareOfMonth) {
	this.isLowestFareOfMonth = isLowestFareOfMonth;
	}

	@JsonProperty("Fare")
	public Double getFare() {
	return fare;
	}

	@JsonProperty("Fare")
	public void setFare(Double fare) {
	this.fare = fare;
	}

	@JsonProperty("BaseFare")
	public Integer getBaseFare() {
	return baseFare;
	}

	@JsonProperty("BaseFare")
	public void setBaseFare(Integer baseFare) {
	this.baseFare = baseFare;
	}

	@JsonProperty("Tax")
	public Integer getTax() {
	return tax;
	}

	@JsonProperty("Tax")
	public void setTax(Integer tax) {
	this.tax = tax;
	}

	@JsonProperty("OtherCharges")
	public Double getOtherCharges() {
	return otherCharges;
	}

	@JsonProperty("OtherCharges")
	public void setOtherCharges(Double otherCharges) {
	this.otherCharges = otherCharges;
	}

	@JsonProperty("FuelSurcharge")
	public Integer getFuelSurcharge() {
	return fuelSurcharge;
	}

	@JsonProperty("FuelSurcharge")
	public void setFuelSurcharge(Integer fuelSurcharge) {
	this.fuelSurcharge = fuelSurcharge;
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
