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

public class TboPriceBDRequest {

	@JsonProperty("AdultCount")
	private String adultCount;
	@JsonProperty("ChildCount")
	private String childCount;
	@JsonProperty("InfantCount")
	private String infantCount;
	@JsonProperty("EndUserIp")
	private String endUserIp;
	@JsonProperty("TokenId")
	private String tokenId;
	@JsonProperty("TraceId")
	private String traceId;
	@JsonProperty("AirSearchResult")
	private List<AirSearchResult> airSearchResult = new ArrayList<AirSearchResult>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	* 
	* @return
	* The adultCount
	*/
	@JsonProperty("AdultCount")
	public String getAdultCount() {
	return adultCount;
	}

	/**
	* 
	* @param adultCount
	* The AdultCount
	*/
	@JsonProperty("AdultCount")
	public void setAdultCount(String adultCount) {
	this.adultCount = adultCount;
	}

	/**
	* 
	* @return
	* The childCount
	*/
	@JsonProperty("ChildCount")
	public String getChildCount() {
	return childCount;
	}

	/**
	* 
	* @param childCount
	* The ChildCount
	*/
	@JsonProperty("ChildCount")
	public void setChildCount(String childCount) {
	this.childCount = childCount;
	}

	/**
	* 
	* @return
	* The infantCount
	*/
	@JsonProperty("InfantCount")
	public String getInfantCount() {
	return infantCount;
	}

	/**
	* 
	* @param infantCount
	* The InfantCount
	*/
	@JsonProperty("InfantCount")
	public void setInfantCount(String infantCount) {
	this.infantCount = infantCount;
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
	* The airSearchResult
	*/
	@JsonProperty("AirSearchResult")
	public List<AirSearchResult> getAirSearchResult() {
	return airSearchResult;
	}

	/**
	* 
	* @param airSearchResult
	* The AirSearchResult
	*/
	@JsonProperty("AirSearchResult")
	public void setAirSearchResult(List<AirSearchResult> airSearchResult) {
	this.airSearchResult = airSearchResult;
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
