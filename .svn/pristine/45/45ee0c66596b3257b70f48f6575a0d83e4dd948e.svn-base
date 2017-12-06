package com.tayyarah.api.flight.tbo.model;

import java.io.Serializable;
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

public class TboTicketRequestForLCC implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("EndUserIp")
	private String endUserIp;
	@JsonProperty("TokenId")
	private String tokenId;
	@JsonProperty("TraceId")
	private String traceId;
	@JsonProperty("ResultIndex")
	private String resultIndex;
	@JsonProperty("Passengers")
	private List<PassengerLCC> passengers = new ArrayList<PassengerLCC>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	

	/**
	* 
	* @return
	* The endUserIp
	*/
	@JsonProperty("EndUserIp")
	public String getEndUserIp() {
	return endUserIp;
	}

	/**
	* 
	* @param endUserIp
	* The EndUserIp
	*/
	@JsonProperty("EndUserIp")
	public void setEndUserIp(String endUserIp) {
	this.endUserIp = endUserIp;
	}

	/**
	* 
	* @return
	* The tokenId
	*/
	@JsonProperty("TokenId")
	public String getTokenId() {
	return tokenId;
	}

	/**
	* 
	* @param tokenId
	* The TokenId
	*/
	@JsonProperty("TokenId")
	public void setTokenId(String tokenId) {
	this.tokenId = tokenId;
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
	* The resultIndex
	*/
	@JsonProperty("ResultIndex")
	public String getResultIndex() {
	return resultIndex;
	}

	/**
	* 
	* @param resultIndex
	* The ResultIndex
	*/
	@JsonProperty("ResultIndex")
	public void setResultIndex(String resultIndex) {
	this.resultIndex = resultIndex;
	}

	/**
	* 
	* @return
	* The passengers
	*/
	@JsonProperty("Passengers")
	public List<PassengerLCC> getPassengers() {
	return passengers;
	}

	/**
	* 
	* @param passengers
	* The Passengers
	*/
	@JsonProperty("Passengers")
	public void setPassengers(List<PassengerLCC> passengers) {
	this.passengers = passengers;
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