package com.tayyarah.api.flight.tbo.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class MealDynamic implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("WayType")
	private Integer wayType;
	@JsonProperty("Code")
	private String code;
	@JsonProperty("Description")
	private Integer description;
	@JsonProperty("AirlineDescription")
	private String airlineDescription;
	@JsonProperty("Quantity")
	private Integer quantity;
	@JsonProperty("Price")
	private BigDecimal price;
	@JsonProperty("Currency")
	private String currency;
	@JsonProperty("Origin")
	private String origin;
	@JsonProperty("Destination")
	private String destination;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The wayType
	 */
	@JsonProperty("WayType")
	public Integer getWayType() {
		return wayType;
	}

	/**
	 * 
	 * @param wayType
	 * The WayType
	 */
	@JsonProperty("WayType")
	public void setWayType(Integer wayType) {
		this.wayType = wayType;
	}

	/**
	 * 
	 * @return
	 * The code
	 */
	@JsonProperty("Code")
	public String getCode() {
		return code;
	}

	/**
	 * 
	 * @param code
	 * The Code
	 */
	@JsonProperty("Code")
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 
	 * @return
	 * The description
	 */
	@JsonProperty("Description")
	public Integer getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 * The Description
	 */
	@JsonProperty("Description")
	public void setDescription(Integer description) {
		this.description = description;
	}

	/**
	 * 
	 * @return
	 * The airlineDescription
	 */
	@JsonProperty("AirlineDescription")
	public String getAirlineDescription() {
		return airlineDescription;
	}

	/**
	 * 
	 * @param airlineDescription
	 * The AirlineDescription
	 */
	@JsonProperty("AirlineDescription")
	public void setAirlineDescription(String airlineDescription) {
		this.airlineDescription = airlineDescription;
	}

	/**
	 * 
	 * @return
	 * The quantity
	 */
	@JsonProperty("Quantity")
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 
	 * @param quantity
	 * The Quantity
	 */
	@JsonProperty("Quantity")
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * 
	 * @return
	 * The price
	 */
	@JsonProperty("Price")
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 
	 * @param price
	 * The Price
	 */
	@JsonProperty("Price")
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 
	 * @return
	 * The currency
	 */
	@JsonProperty("Currency")
	public String getCurrency() {
		return currency;
	}

	/**
	 * 
	 * @param currency
	 * The Currency
	 */
	@JsonProperty("Currency")
	public void setCurrency(String currency) {
		this.currency = currency;
	}

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
