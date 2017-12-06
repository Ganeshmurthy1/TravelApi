package com.tayyarah.api.flight.tbo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class PriceBDResponse {

	@JsonProperty("ResponseStatus")
	private Integer responseStatus;
	@JsonProperty("Error")
	private Error error;
	@JsonProperty("TraceId")
	private String traceId;
	@JsonProperty("Origin")
	private String origin;
	@JsonProperty("Destination")
	private String destination;
	@JsonProperty("IsPriced")
	private Boolean isPriced;
	@JsonProperty("Results")
	private List<List<Result>> results = new ArrayList<List<Result>>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	* 
	* @return
	* The responseStatus
	*/
	@JsonProperty("ResponseStatus")
	public Integer getResponseStatus() {
	return responseStatus;
	}

	/**
	* 
	* @param responseStatus
	* The ResponseStatus
	*/
	@JsonProperty("ResponseStatus")
	public void setResponseStatus(Integer responseStatus) {
	this.responseStatus = responseStatus;
	}

	/**
	* 
	* @return
	* The error
	*/
	@JsonProperty("Error")
	public Error getError() {
	return error;
	}

	/**
	* 
	* @param error
	* The Error
	*/
	@JsonProperty("Error")
	public void setError(Error error) {
	this.error = error;
	}

	/**
	* 
	* @return
	* The traceId
	*/
	@JsonProperty("TraceId")
	public String getTraceId() {
	return traceId;
	}

	/**
	* 
	* @param traceId
	* The TraceId
	*/
	@JsonProperty("TraceId")
	public void setTraceId(String traceId) {
	this.traceId = traceId;
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

	/**
	* 
	* @return
	* The isPriced
	*/
	@JsonProperty("IsPriced")
	public Boolean getIsPriced() {
	return isPriced;
	}

	/**
	* 
	* @param isPriced
	* The IsPriced
	*/
	@JsonProperty("IsPriced")
	public void setIsPriced(Boolean isPriced) {
	this.isPriced = isPriced;
	}

	/**
	* 
	* @return
	* The results
	*/
	@JsonProperty("Results")
	public List<List<Result>> getResults() {
	return results;
	}

	/**
	* 
	* @param results
	* The Results
	*/
	@JsonProperty("Results")
	public void setResults(List<List<Result>> results) {
	this.results = results;
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


