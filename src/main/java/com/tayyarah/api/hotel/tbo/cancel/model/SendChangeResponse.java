package com.tayyarah.api.hotel.tbo.cancel.model;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "HotelChangeRequestResult" })
public class SendChangeResponse {

	@JsonProperty("HotelChangeRequestResult")
	@Valid
	private com.tayyarah.api.hotel.tbo.cancel.model.HotelChangeRequestResult HotelChangeRequestResult;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The HotelChangeRequestResult
	 */
	@JsonProperty("HotelChangeRequestResult")
	public com.tayyarah.api.hotel.tbo.cancel.model.HotelChangeRequestResult getHotelChangeRequestResult() {
		return HotelChangeRequestResult;
	}

	/**
	 * 
	 * @param HotelChangeRequestResult
	 *            The HotelChangeRequestResult
	 */
	@JsonProperty("HotelChangeRequestResult")
	public void setHotelChangeRequestResult(com.tayyarah.api.hotel.tbo.cancel.model.HotelChangeRequestResult HotelChangeRequestResult) {
		this.HotelChangeRequestResult = HotelChangeRequestResult;
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