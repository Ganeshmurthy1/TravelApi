package com.tayyarah.api.flight.tbo.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


public class TboFlightCalendarFareRequest {
	@JsonProperty("JourneyType")
	private String journeyType;
	@JsonProperty("EndUserIp")
	private String endUserIp;
	@JsonProperty("TokenId")
	private String tokenId;
	@JsonProperty("PreferredAirlines")
	private List<String> preferredAirlines ;
	@JsonProperty("Segments")
	private List<CalendarSegment> segments;
	@JsonProperty("Sources")
	private String[] sources;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("JourneyType")
	public String getJourneyType() {
	return journeyType;
	}

	@JsonProperty("JourneyType")
	public void setJourneyType(String journeyType) {
	this.journeyType = journeyType;
	}

	@JsonProperty("EndUserIp")
	public String getEndUserIp() {
	return endUserIp;
	}

	@JsonProperty("EndUserIp")
	public void setEndUserIp(String endUserIp) {
	this.endUserIp = endUserIp;
	}

	@JsonProperty("TokenId")
	public String getTokenId() {
	return tokenId;
	}

	@JsonProperty("TokenId")
	public void setTokenId(String tokenId) {
	this.tokenId = tokenId;
	}

	@JsonProperty("PreferredAirlines")
	public List<String> getPreferredAirlines() {
	return preferredAirlines;
	}

	@JsonProperty("PreferredAirlines")
	public void setPreferredAirlines(List<String> preferredAirlines) {
	this.preferredAirlines = preferredAirlines;
	}

	@JsonProperty("Segments")
	public List<CalendarSegment> getSegments() {
	return segments;
	}

	@JsonProperty("Segments")
	public void setSegments(List<CalendarSegment> segments) {
	this.segments = segments;
	}

	@JsonProperty("Sources")
	public String[] getSources() {
	return sources;
	}

	@JsonProperty("Sources")
	public void setSources(String[] sources) {
	this.sources = sources;
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
