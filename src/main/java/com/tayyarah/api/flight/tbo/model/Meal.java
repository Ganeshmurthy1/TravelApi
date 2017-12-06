package com.tayyarah.api.flight.tbo.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class Meal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("Code")
	private String code;
	@JsonProperty("Description")
	private String description;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
	public String getDescription() {
	return description;
	}

	/**
	* 
	* @param description
	* The Description
	*/
	@JsonProperty("Description")
	public void setDescription(String description) {
	this.description = description;
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
