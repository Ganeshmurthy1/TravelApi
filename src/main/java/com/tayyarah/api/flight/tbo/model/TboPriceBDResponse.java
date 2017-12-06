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

public class TboPriceBDResponse {

	@JsonProperty("Response")
	private PriceBDResponse response;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	* 
	* @return
	* The response
	*/
	@JsonProperty("Response")
	public PriceBDResponse getResponse() {
	return response;
	}

	/**
	* 
	* @param response
	* The Response
	*/
	@JsonProperty("Response")
	public void setResponse(PriceBDResponse response) {
	this.response = response;
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

