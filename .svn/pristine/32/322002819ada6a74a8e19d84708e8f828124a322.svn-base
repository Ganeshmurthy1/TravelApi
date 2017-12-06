package com.tayyarah.api.flight.tbo.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Availability {

	@JsonProperty("Class")
	private String _class;
	@JsonProperty("Seats")
	private String seats;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The _class
	 */
	@JsonProperty("Class")
	public String getClass_() {
		return _class;
	}

	/**
	 * 
	 * @param _class
	 * The Class
	 */
	@JsonProperty("Class")
	public void setClass_(String _class) {
		this._class = _class;
	}

	/**
	 * 
	 * @return
	 * The seats
	 */
	@JsonProperty("Seats")
	public String getSeats() {
		return seats;
	}

	/**
	 * 
	 * @param seats
	 * The Seats
	 */
	@JsonProperty("Seats")
	public void setSeats(String seats) {
		this.seats = seats;
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
