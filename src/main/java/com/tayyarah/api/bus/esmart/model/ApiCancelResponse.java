package com.tayyarah.api.bus.esmart.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiCancelResponse {
	@JsonProperty("totalTicketFare")
	private String totalTicketFare;
	@JsonProperty("totalRefundAmount")
	private String totalRefundAmount;
	@JsonProperty("cancelChargesPercentage")
	private String cancelChargesPercentage;
	@JsonProperty("cancellationCharges")
	private Double cancellationCharges;
	@JsonProperty("apiStatus")
	private ApiStatus apiStatus;
	@JsonProperty("cancellable")
	private Boolean cancellable;
	@JsonProperty("partiallyCancellable")
	private Boolean partiallyCancellable;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("totalTicketFare")
	public String getTotalTicketFare() {
	return totalTicketFare;
	}

	@JsonProperty("totalTicketFare")
	public void setTotalTicketFare(String totalTicketFare) {
	this.totalTicketFare = totalTicketFare;
	}

	@JsonProperty("totalRefundAmount")
	public String getTotalRefundAmount() {
	return totalRefundAmount;
	}

	@JsonProperty("totalRefundAmount")
	public void setTotalRefundAmount(String totalRefundAmount) {
	this.totalRefundAmount = totalRefundAmount;
	}

	@JsonProperty("cancelChargesPercentage")
	public String getCancelChargesPercentage() {
	return cancelChargesPercentage;
	}

	@JsonProperty("cancelChargesPercentage")
	public void setCancelChargesPercentage(String cancelChargesPercentage) {
	this.cancelChargesPercentage = cancelChargesPercentage;
	}

	@JsonProperty("cancellationCharges")
	public Double getCancellationCharges() {
	return cancellationCharges;
	}

	@JsonProperty("cancellationCharges")
	public void setCancellationCharges(Double cancellationCharges) {
	this.cancellationCharges = cancellationCharges;
	}

	@JsonProperty("apiStatus")
	public ApiStatus getApiStatus() {
	return apiStatus;
	}

	@JsonProperty("apiStatus")
	public void setApiStatus(ApiStatus apiStatus) {
	this.apiStatus = apiStatus;
	}

	@JsonProperty("cancellable")
	public Boolean getCancellable() {
	return cancellable;
	}

	@JsonProperty("cancellable")
	public void setCancellable(Boolean cancellable) {
	this.cancellable = cancellable;
	}

	@JsonProperty("partiallyCancellable")
	public Boolean getPartiallyCancellable() {
	return partiallyCancellable;
	}

	@JsonProperty("partiallyCancellable")
	public void setPartiallyCancellable(Boolean partiallyCancellable) {
	this.partiallyCancellable = partiallyCancellable;
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
