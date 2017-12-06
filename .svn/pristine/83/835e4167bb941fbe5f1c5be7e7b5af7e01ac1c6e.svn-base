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

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TboCancelTicketRequest {

	
	
	@JsonProperty("BookingId")
	private Integer bookingId;
	@JsonProperty("RequestType")
	private Integer requestType;
	@JsonProperty("CancellationType")
	private Integer cancellationType;
	@JsonProperty("Sectors")
	private List<Sector> sectors = new ArrayList<Sector>();
	@JsonProperty("TicketId")
	private List<Integer> ticketId = new ArrayList<Integer>();
	@JsonProperty("Remarks")
	private String remarks;
	@JsonProperty("EndUserIp")
	private String endUserIp;
	@JsonProperty("TokenId")
	private String tokenId;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public TboCancelTicketRequest(Integer bookingId,Integer requestType,Integer cancellationType,String remarks,String endUserIp,String tokenId,List<Sector> sectors,List<Integer> ticketId){
		this.bookingId = bookingId;
		this.requestType = requestType;
		this.cancellationType = cancellationType;
		this.remarks = remarks;
		this.endUserIp = endUserIp;
		this.tokenId = tokenId;
		this.sectors = sectors;
		this.ticketId = ticketId;
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
	* The requestType
	*/
	@JsonProperty("RequestType")
	public Integer getRequestType() {
	return requestType;
	}

	/**
	* 
	* @param requestType
	* The RequestType
	*/
	@JsonProperty("RequestType")
	public void setRequestType(Integer requestType) {
	this.requestType = requestType;
	}

	/**
	* 
	* @return
	* The cancellationType
	*/
	@JsonProperty("CancellationType")
	public Integer getCancellationType() {
	return cancellationType;
	}

	/**
	* 
	* @param cancellationType
	* The CancellationType
	*/
	@JsonProperty("CancellationType")
	public void setCancellationType(Integer cancellationType) {
	this.cancellationType = cancellationType;
	}

	/**
	* 
	* @return
	* The sectors
	*/
	@JsonProperty("Sectors")
	public List<Sector> getSectors() {
	return sectors;
	}

	/**
	* 
	* @param sectors
	* The Sectors
	*/
	@JsonProperty("Sectors")
	public void setSectors(List<Sector> sectors) {
	this.sectors = sectors;
	}

	/**
	* 
	* @return
	* The ticketId
	*/
	@JsonProperty("TicketId")
	public List<Integer> getTicketId() {
	return ticketId;
	}

	/**
	* 
	* @param ticketId
	* The TicketId
	*/
	@JsonProperty("TicketId")
	public void setTicketId(List<Integer> ticketId) {
	this.ticketId = ticketId;
	}

	/**
	* 
	* @return
	* The remarks
	*/
	@JsonProperty("Remarks")
	public String getRemarks() {
	return remarks;
	}

	/**
	* 
	* @param remarks
	* The Remarks
	*/
	@JsonProperty("Remarks")
	public void setRemarks(String remarks) {
	this.remarks = remarks;
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

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}
	
	@Override
	public String toString() {
		return "TboCancelTicketRequest [bookingId=" + bookingId + ", requestType="
				+ requestType + ", sectors=" + sectors + ", ticketId=" + ticketId
				+ ", remarks=" + remarks + ", endUserIp=" + endUserIp + "]";
	}
	
	

}
