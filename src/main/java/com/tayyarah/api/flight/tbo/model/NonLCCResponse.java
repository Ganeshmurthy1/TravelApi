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

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NonLCCResponse {
	
	/**
	 * 
	 */

	
	@JsonProperty("PNR")
	private String pNR;
	@JsonProperty("BookingId")
	private Integer bookingId;
	@JsonProperty("SSRDenied")
	private Boolean sSRDenied;
	@JsonProperty("SSRMessage")
	private String sSRMessage;
	@JsonProperty("Status")
	private Integer status;
	@JsonProperty("IsPriceChanged")
	private Boolean isPriceChanged;
	@JsonProperty("IsTimeChanged")
	private Boolean isTimeChanged;
	@JsonProperty("FlightItinerary")
	private FlightItinerary flightItinerary;
	@JsonProperty("TicketStatus")
	private Integer ticketStatus;
	@JsonProperty("Message")
	private String message;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The pNR
	 */
	@JsonProperty("PNR")
	public String getPNR() {
		return pNR;
	}

	/**
	 * 
	 * @param pNR
	 * The PNR
	 */
	@JsonProperty("PNR")
	public void setPNR(String pNR) {
		this.pNR = pNR;
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
	 * The sSRDenied
	 */
	@JsonProperty("SSRDenied")
	public Boolean getSSRDenied() {
		return sSRDenied;
	}

	/**
	 * 
	 * @param sSRDenied
	 * The SSRDenied
	 */
	@JsonProperty("SSRDenied")
	public void setSSRDenied(Boolean sSRDenied) {
		this.sSRDenied = sSRDenied;
	}

	/**
	 * 
	 * @return
	 * The sSRMessage
	 */
	@JsonProperty("SSRMessage")
	public Object getSSRMessage() {
		return sSRMessage;
	}

	/**
	 * 
	 * @param sSRMessage
	 * The SSRMessage
	 */
	@JsonProperty("SSRMessage")
	public void setSSRMessage(String sSRMessage) {
		this.sSRMessage = sSRMessage;
	}

	/**
	 * 
	 * @return
	 * The status
	 */
	@JsonProperty("Status")
	public Integer getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 * The Status
	 */
	@JsonProperty("Status")
	public void setStatus(Integer status) {
		this.status = status;
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
	 * The isTimeChanged
	 */
	@JsonProperty("IsTimeChanged")
	public Boolean getIsTimeChanged() {
		return isTimeChanged;
	}

	/**
	 * 
	 * @param isTimeChanged
	 * The IsTimeChanged
	 */
	@JsonProperty("IsTimeChanged")
	public void setIsTimeChanged(Boolean isTimeChanged) {
		this.isTimeChanged = isTimeChanged;
	}

	/**
	 * 
	 * @return
	 * The flightItinerary
	 */
	@JsonProperty("FlightItinerary")
	public FlightItinerary getFlightItinerary() {
		return flightItinerary;
	}

	/**
	 * 
	 * @param flightItinerary
	 * The FlightItinerary
	 */
	@JsonProperty("FlightItinerary")
	public void setFlightItinerary(FlightItinerary flightItinerary) {
		this.flightItinerary = flightItinerary;
	}

	/**
	* 
	* @return
	* The ticketStatus
	*/
	@JsonProperty("TicketStatus")
	public Integer getTicketStatus() {
	return ticketStatus;
	}

	/**
	* 
	* @param ticketStatus
	* The TicketStatus
	*/
	@JsonProperty("TicketStatus")
	public void setTicketStatus(Integer ticketStatus) {
	this.ticketStatus = ticketStatus;
	}

	/**
	* 
	* @return
	* The message
	*/
	@JsonProperty("Message")
	public String getMessage() {
	return message;
	}

	/**
	* 
	* @param message
	* The Message
	*/
	@JsonProperty("Message")
	public void setMessage(String message) {
	this.message = message;
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
