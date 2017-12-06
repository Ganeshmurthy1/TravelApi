package com.tayyarah.api.flight.tbo.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class TboFlightFareRuleRequest {

	@JsonProperty("EndUserIp")
	private String endUserIp;
	@JsonProperty("TokenId")
	private String tokenId;
	@JsonProperty("TraceId")
	private String traceId;
	@JsonProperty("ResultIndex")
	private String resultIndex;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();


	public TboFlightFareRuleRequest(){
		
	}
    public TboFlightFareRuleRequest(String endUserIp,String tokenId,String traceId,String resultIndex){
		this.endUserIp = endUserIp;
		this.tokenId = tokenId;
		this.traceId = traceId;
		this.resultIndex = resultIndex;
	}
	
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

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}

}
