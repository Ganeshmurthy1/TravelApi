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

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightItinerary {

	@JsonProperty("BookingId")
	private Integer bookingId;
	@JsonProperty("PNR")
	private String pNR;
	@JsonProperty("IsDomestic")
	private Boolean isDomestic;
	@JsonProperty("Source")
	private Integer source;
	@JsonProperty("Origin")
	private String origin;
	@JsonProperty("Destination")
	private String destination;
	@JsonProperty("AirlineCode")
	private String airlineCode;
	@JsonProperty("ValidatingAirlineCode")
	private String validatingAirlineCode;
	@JsonProperty("AirlineRemark")
	private String airlineRemark;
	@JsonProperty("IsLCC")
	private Boolean isLCC;
	@JsonProperty("NonRefundable")
	private Boolean nonRefundable;
	@JsonProperty("FareType")
	private String fareType;
	@JsonProperty("CreditNoteNo")
	private String creditNoteNo;
	@JsonProperty("Fare")
	private Fare fare;
	@JsonProperty("CreditNoteCreatedOn")
	private String creditNoteCreatedOn;
	@JsonProperty("Passenger")
	private List<Passenger> passenger = new ArrayList<Passenger>();
	@JsonProperty("CancellationCharges")
	private Object cancellationCharges;
	@JsonProperty("Segments")
	private List<BookResponseSegment> segments = new ArrayList<BookResponseSegment>();
	@JsonProperty("FareRules")
	private List<FareRule> fareRules = new ArrayList<FareRule>();
	@JsonProperty("Status")
	private Integer status;
	@JsonProperty("InvoiceNo")
	private String invoiceNo;
	@JsonProperty("InvoiceCreatedOn")
	private String invoiceCreatedOn;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
	* The isDomestic
	*/
	@JsonProperty("IsDomestic")
	public Boolean getIsDomestic() {
	return isDomestic;
	}

	/**
	* 
	* @param isDomestic
	* The IsDomestic
	*/
	@JsonProperty("IsDomestic")
	public void setIsDomestic(Boolean isDomestic) {
	this.isDomestic = isDomestic;
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
	* The origin
	*/
	@JsonProperty("Origin")
	public String getOrigin() {
	return origin;
	}

	/**
	* 
	* @param origin
	* The Origin
	*/
	@JsonProperty("Origin")
	public void setOrigin(String origin) {
	this.origin = origin;
	}

	/**
	* 
	* @return
	* The destination
	*/
	@JsonProperty("Destination")
	public String getDestination() {
	return destination;
	}

	/**
	* 
	* @param destination
	* The Destination
	*/
	@JsonProperty("Destination")
	public void setDestination(String destination) {
	this.destination = destination;
	}

	/**
	* 
	* @return
	* The airlineCode
	*/
	@JsonProperty("AirlineCode")
	public String getAirlineCode() {
	return airlineCode;
	}

	/**
	* 
	* @param airlineCode
	* The AirlineCode
	*/
	@JsonProperty("AirlineCode")
	public void setAirlineCode(String airlineCode) {
	this.airlineCode = airlineCode;
	}

	/**
	* 
	* @return
	* The validatingAirlineCode
	*/
	@JsonProperty("ValidatingAirlineCode")
	public String getValidatingAirlineCode() {
	return validatingAirlineCode;
	}

	/**
	* 
	* @param validatingAirlineCode
	* The ValidatingAirlineCode
	*/
	@JsonProperty("ValidatingAirlineCode")
	public void setValidatingAirlineCode(String validatingAirlineCode) {
	this.validatingAirlineCode = validatingAirlineCode;
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
	* The nonRefundable
	*/
	@JsonProperty("NonRefundable")
	public Boolean getNonRefundable() {
	return nonRefundable;
	}

	/**
	* 
	* @param nonRefundable
	* The NonRefundable
	*/
	@JsonProperty("NonRefundable")
	public void setNonRefundable(Boolean nonRefundable) {
	this.nonRefundable = nonRefundable;
	}

	/**
	* 
	* @return
	* The fareType
	*/
	@JsonProperty("FareType")
	public String getFareType() {
	return fareType;
	}

	/**
	* 
	* @param fareType
	* The FareType
	*/
	@JsonProperty("FareType")
	public void setFareType(String fareType) {
	this.fareType = fareType;
	}

	/**
	* 
	* @return
	* The creditNoteNo
	*/
	@JsonProperty("CreditNoteNo")
	public Object getCreditNoteNo() {
	return creditNoteNo;
	}

	/**
	* 
	* @param creditNoteNo
	* The CreditNoteNo
	*/
	@JsonProperty("CreditNoteNo")
	public void setCreditNoteNo(String creditNoteNo) {
	this.creditNoteNo = creditNoteNo;
	}

	/**
	* 
	* @return
	* The fare
	*/
	@JsonProperty("Fare")
	public Fare getFare() {
	return fare;
	}

	/**
	* 
	* @param fare
	* The Fare
	*/
	@JsonProperty("Fare")
	public void setFare(Fare fare) {
	this.fare = fare;
	}

	/**
	* 
	* @return
	* The creditNoteCreatedOn
	*/
	@JsonProperty("CreditNoteCreatedOn")
	public Object getCreditNoteCreatedOn() {
	return creditNoteCreatedOn;
	}

	/**
	* 
	* @param creditNoteCreatedOn
	* The CreditNoteCreatedOn
	*/
	@JsonProperty("CreditNoteCreatedOn")
	public void setCreditNoteCreatedOn(String creditNoteCreatedOn) {
	this.creditNoteCreatedOn = creditNoteCreatedOn;
	}

	/**
	* 
	* @return
	* The passenger
	*/
	@JsonProperty("Passenger")
	public List<Passenger> getPassenger() {
	return passenger;
	}

	/**
	* 
	* @param passenger
	* The Passenger
	*/
	@JsonProperty("Passenger")
	public void setPassenger(List<Passenger> passenger) {
	this.passenger = passenger;
	}

	/**
	* 
	* @return
	* The cancellationCharges
	*/
	@JsonProperty("CancellationCharges")
	public Object getCancellationCharges() {
	return cancellationCharges;
	}

	/**
	* 
	* @param cancellationCharges
	* The CancellationCharges
	*/
	@JsonProperty("CancellationCharges")
	public void setCancellationCharges(Object cancellationCharges) {
	this.cancellationCharges = cancellationCharges;
	}

	/**
	* 
	* @return
	* The segments
	*/
	@JsonProperty("Segments")
	public List<BookResponseSegment> getSegments() {
	return segments;
	}

	/**
	* 
	* @param segments
	* The Segments
	*/
	@JsonProperty("Segments")
	public void setSegments(List<BookResponseSegment> segments) {
	this.segments = segments;
	}

	/**
	* 
	* @return
	* The fareRules
	*/
	@JsonProperty("FareRules")
	public List<FareRule> getFareRules() {
	return fareRules;
	}

	/**
	* 
	* @param fareRules
	* The FareRules
	*/
	@JsonProperty("FareRules")
	public void setFareRules(List<FareRule> fareRules) {
	this.fareRules = fareRules;
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
	* The invoiceNo
	*/
	@JsonProperty("InvoiceNo")
	public String getInvoiceNo() {
	return invoiceNo;
	}

	/**
	* 
	* @param invoiceNo
	* The InvoiceNo
	*/
	@JsonProperty("InvoiceNo")
	public void setInvoiceNo(String invoiceNo) {
	this.invoiceNo = invoiceNo;
	}

	/**
	* 
	* @return
	* The invoiceCreatedOn
	*/
	@JsonProperty("InvoiceCreatedOn")
	public String getInvoiceCreatedOn() {
	return invoiceCreatedOn;
	}

	/**
	* 
	* @param invoiceCreatedOn
	* The InvoiceCreatedOn
	*/
	@JsonProperty("InvoiceCreatedOn")
	public void setInvoiceCreatedOn(String invoiceCreatedOn) {
	this.invoiceCreatedOn = invoiceCreatedOn;
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
