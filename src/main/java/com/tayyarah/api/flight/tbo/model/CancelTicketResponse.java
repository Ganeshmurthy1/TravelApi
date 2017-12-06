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

public class CancelTicketResponse {

	@JsonProperty("TicketCRInfo")
	private List<TicketCRInfo> ticketCRInfo = new ArrayList<TicketCRInfo>();
	@JsonProperty("ResponseStatus")
	private Integer responseStatus;
	@JsonProperty("TraceId")
	private String traceId;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	* 
	* @return
	* The ticketCRInfo
	*/
	@JsonProperty("TicketCRInfo")
	public List<TicketCRInfo> getTicketCRInfo() {
	return ticketCRInfo;
	}

	/**
	* 
	* @param ticketCRInfo
	* The TicketCRInfo
	*/
	@JsonProperty("TicketCRInfo")
	public void setTicketCRInfo(List<TicketCRInfo> ticketCRInfo) {
	this.ticketCRInfo = ticketCRInfo;
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
