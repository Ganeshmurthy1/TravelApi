package com.tayyarah.api.flight.tbo.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CalendarResponse {

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
	@JsonProperty("SearchResults")
	private List<SearchResult> searchResults = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("ResponseStatus")
	public Integer getResponseStatus() {
	return responseStatus;
	}

	@JsonProperty("ResponseStatus")
	public void setResponseStatus(Integer responseStatus) {
	this.responseStatus = responseStatus;
	}

	@JsonProperty("Error")
	public Error getError() {
	return error;
	}

	@JsonProperty("Error")
	public void setError(Error error) {
	this.error = error;
	}

	@JsonProperty("TraceId")
	public String getTraceId() {
	return traceId;
	}

	@JsonProperty("TraceId")
	public void setTraceId(String traceId) {
	this.traceId = traceId;
	}

	@JsonProperty("Origin")
	public String getOrigin() {
	return origin;
	}

	@JsonProperty("Origin")
	public void setOrigin(String origin) {
	this.origin = origin;
	}

	@JsonProperty("Destination")
	public String getDestination() {
	return destination;
	}

	@JsonProperty("Destination")
	public void setDestination(String destination) {
	this.destination = destination;
	}

	@JsonProperty("SearchResults")
	public List<SearchResult> getSearchResults() {
	return searchResults;
	}

	@JsonProperty("SearchResults")
	public void setSearchResults(List<SearchResult> searchResults) {
	this.searchResults = searchResults;
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
