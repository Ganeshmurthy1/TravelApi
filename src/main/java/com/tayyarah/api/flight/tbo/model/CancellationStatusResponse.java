package com.tayyarah.api.flight.tbo.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CancellationStatusResponse {

	@JsonProperty("ResponseStatus")
	private Integer responseStatus;
	@JsonProperty("Error")
	private Error error;
	@JsonProperty("TraceId")
	private String traceId;
	@JsonProperty("ChangeRequestId")
	private Integer changeRequestId;
	@JsonProperty("RefundedAmount")
	private Double refundedAmount;
	@JsonProperty("CancellationCharge")
	private Double cancellationCharge;
	@JsonProperty("ServiceTaxOnRAF")
	private Double serviceTaxOnRAF;
	@JsonProperty("ChangeRequestStatus")
	private Integer changeRequestStatus;
	@JsonProperty("SwachhBharatCess")
	private Double SwachhBharatCess;
	@JsonProperty("CreditNoteNo")
	private String CreditNoteNo;
	@JsonProperty("KrishiKalyanCess")
	private Double KrishiKalyanCess;
	@JsonProperty("CreditNoteCreatedOn")
	private String CreditNoteCreatedOn;
	@JsonProperty("TicketId")
	private String TicketId;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
	* The error
	*/
	@JsonProperty("Error")
	public Error getError() {
	return error;
	}

	/**
	* 
	* @param error
	* The Error
	*/
	@JsonProperty("Error")
	public void setError(Error error) {
	this.error = error;
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
	* The changeRequestId
	*/
	@JsonProperty("ChangeRequestId")
	public Integer getChangeRequestId() {
	return changeRequestId;
	}

	/**
	* 
	* @param changeRequestId
	* The ChangeRequestId
	*/
	@JsonProperty("ChangeRequestId")
	public void setChangeRequestId(Integer changeRequestId) {
	this.changeRequestId = changeRequestId;
	}

	/**
	* 
	* @return
	* The refundedAmount
	*/
	@JsonProperty("RefundedAmount")
	public Double getRefundedAmount() {
	return refundedAmount;
	}

	/**
	* 
	* @param refundedAmount
	* The RefundedAmount
	*/
	@JsonProperty("RefundedAmount")
	public void setRefundedAmount(Double refundedAmount) {
	this.refundedAmount = refundedAmount;
	}

	/**
	* 
	* @return
	* The cancellationCharge
	*/
	@JsonProperty("CancellationCharge")
	public Double getCancellationCharge() {
	return cancellationCharge;
	}

	/**
	* 
	* @param cancellationCharge
	* The CancellationCharge
	*/
	@JsonProperty("CancellationCharge")
	public void setCancellationCharge(Double cancellationCharge) {
	this.cancellationCharge = cancellationCharge;
	}

	/**
	* 
	* @return
	* The serviceTaxOnRAF
	*/
	@JsonProperty("ServiceTaxOnRAF")
	public Double getServiceTaxOnRAF() {
	return serviceTaxOnRAF;
	}

	/**
	* 
	* @param serviceTaxOnRAF
	* The ServiceTaxOnRAF
	*/
	@JsonProperty("ServiceTaxOnRAF")
	public void setServiceTaxOnRAF(Double serviceTaxOnRAF) {
	this.serviceTaxOnRAF = serviceTaxOnRAF;
	}

	/**
	* 
	* @return
	* The changeRequestStatus
	*/
	@JsonProperty("ChangeRequestStatus")
	public Integer getChangeRequestStatus() {
	return changeRequestStatus;
	}

	/**
	* 
	* @param changeRequestStatus
	* The ChangeRequestStatus
	*/
	@JsonProperty("ChangeRequestStatus")
	public void setChangeRequestStatus(Integer changeRequestStatus) {
	this.changeRequestStatus = changeRequestStatus;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}

	public Double getSwachhBharatCess() {
		return SwachhBharatCess;
	}

	public void setSwachhBharatCess(Double swachhBharatCess) {
		SwachhBharatCess = swachhBharatCess;
	}

	public String getCreditNoteNo() {
		return CreditNoteNo;
	}

	public void setCreditNoteNo(String creditNoteNo) {
		CreditNoteNo = creditNoteNo;
	}

	public Double getKrishiKalyanCess() {
		return KrishiKalyanCess;
	}

	public void setKrishiKalyanCess(Double krishiKalyanCess) {
		KrishiKalyanCess = krishiKalyanCess;
	}

	public String getCreditNoteCreatedOn() {
		return CreditNoteCreatedOn;
	}

	public void setCreditNoteCreatedOn(String creditNoteCreatedOn) {
		CreditNoteCreatedOn = creditNoteCreatedOn;
	}

	public String getTicketId() {
		return TicketId;
	}

	public void setTicketId(String ticketId) {
		TicketId = ticketId;
	}

}
