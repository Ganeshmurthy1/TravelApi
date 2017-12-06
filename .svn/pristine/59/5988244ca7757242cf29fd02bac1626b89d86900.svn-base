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

public class AirSearchResult {

	@JsonProperty("ResultIndex")
	private String resultIndex;
	@JsonProperty("Source")
	private Integer source;
	@JsonProperty("IsLCC")
	private Boolean isLCC;
	@JsonProperty("IsRefundable")
	private Boolean isRefundable;
	@JsonProperty("AirlineRemark")
	private String airlineRemark;
	@JsonProperty("Segments")
	private List<List<PriceBDRequestSegment>> segments = new ArrayList<List<PriceBDRequestSegment>>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
	* The source
	*/
	@JsonProperty("Source")
	public Integer getSource() {
	return source;
	}

	/**
	* 
	* @param source
	* The Source
	*/
	@JsonProperty("Source")
	public void setSource(Integer source) {
	this.source = source;
	}

	/**
	* 
	* @return
	* The isLCC
	*/
	@JsonProperty("IsLCC")
	public Boolean getIsLCC() {
	return isLCC;
	}

	/**
	* 
	* @param isLCC
	* The IsLCC
	*/
	@JsonProperty("IsLCC")
	public void setIsLCC(Boolean isLCC) {
	this.isLCC = isLCC;
	}

	/**
	* 
	* @return
	* The isRefundable
	*/
	@JsonProperty("IsRefundable")
	public Boolean getIsRefundable() {
	return isRefundable;
	}

	/**
	* 
	* @param isRefundable
	* The IsRefundable
	*/
	@JsonProperty("IsRefundable")
	public void setIsRefundable(Boolean isRefundable) {
	this.isRefundable = isRefundable;
	}

	/**
	* 
	* @return
	* The airlineRemark
	*/
	@JsonProperty("AirlineRemark")
	public String getAirlineRemark() {
	return airlineRemark;
	}

	/**
	* 
	* @param airlineRemark
	* The AirlineRemark
	*/
	@JsonProperty("AirlineRemark")
	public void setAirlineRemark(String airlineRemark) {
	this.airlineRemark = airlineRemark;
	}

	/**
	* 
	* @return
	* The segments
	*/
	@JsonProperty("Segments")
	public List<List<PriceBDRequestSegment>> getSegments() {
	return segments;
	}

	/**
	* 
	* @param segments
	* The Segments
	*/
	@JsonProperty("Segments")
	public void setSegments(List<List<PriceBDRequestSegment>> segments) {
	this.segments = segments;
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
