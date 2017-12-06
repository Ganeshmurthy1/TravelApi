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

public class AirPriceResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("Error")
	private Error error;
	@JsonProperty("IsPriceChanged")
	private Boolean isPriceChanged;
	@JsonProperty("ResponseStatus")
	private Integer responseStatus;
	@JsonProperty("Results")
	private Result results;
	@JsonProperty("TraceId")
	private String traceId;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
	* The isPriceChanged
	*/
	@JsonProperty("IsPriceChanged")
	public Boolean getIsPriceChanged() {
	return isPriceChanged;
	}

	/**
	* 
	* @param isPriceChanged
	* The IsPriceChanged
	*/
	@JsonProperty("IsPriceChanged")
	public void setIsPriceChanged(Boolean isPriceChanged) {
	this.isPriceChanged = isPriceChanged;
	}

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
	* The results
	*/
	@JsonProperty("Results")
	public Result getResults() {
	return results;
	}

	/**
	* 
	* @param results
	* The Results
	*/
	@JsonProperty("Results")
	public void setResults(Result results) {
	this.results = results;
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

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}
}
