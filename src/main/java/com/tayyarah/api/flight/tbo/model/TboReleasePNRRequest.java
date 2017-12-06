package com.tayyarah.api.flight.tbo.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class TboReleasePNRRequest {

	@JsonProperty("EndUserIp")
	private String endUserIp;
	@JsonProperty("TokenId")
	private String tokenId;
	@JsonProperty("BookingId")
	private Integer bookingId;
	@JsonProperty("Source")
	private String source;
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
	* The bookingId
	*/
	@JsonProperty("BookingId")
	public Integer getBookingId() {
	return bookingId;
	}

	/**
	* 
	* @param bookingId
	* The BookingId
	*/
	@JsonProperty("BookingId")
	public void setBookingId(Integer bookingId) {
	this.bookingId = bookingId;
	}

	/**
	* 
	* @return
	* The source
	*/
	@JsonProperty("Source")
	public String getSource() {
	return source;
	}

	/**
	* 
	* @param source
	* The Source
	*/
	@JsonProperty("Source")
	public void setSource(String source) {
	this.source = source;
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
