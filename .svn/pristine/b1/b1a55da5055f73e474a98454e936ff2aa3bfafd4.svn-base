package com.tayyarah.api.bus.esmart.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiCancelConfirmation {
	@JsonProperty("etsTicketNo")
	private String etsTicketNo;
	@JsonProperty("seatNbrsToCancel")
	private List<String> seatNbrsToCancel;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("etsTicketNo")
	public String getEtsTicketNo() {
	return etsTicketNo;
	}

	@JsonProperty("etsTicketNo")
	public void setEtsTicketNo(String etsTicketNo) {
	this.etsTicketNo = etsTicketNo;
	}

	@JsonProperty("seatNbrsToCancel")
	public List<String> getSeatNbrsToCancel() {
	return seatNbrsToCancel;
	}

	@JsonProperty("seatNbrsToCancel")
	public void setSeatNbrsToCancel(List<String> seatNbrsToCancel) {
	this.seatNbrsToCancel = seatNbrsToCancel;
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
