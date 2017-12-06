package com.tayyarah.api.bus.esmart.model;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiConfirmTicket {

	@JsonProperty("opPNR")
	private String opPNR;
	@JsonProperty("etstnumber")
	private String etstnumber;
	@JsonProperty("commPCT")
	private Double commPCT;
	@JsonProperty("totalFare")
	private Double totalFare;
	@JsonProperty("cancellationPolicy")
	private String cancellationPolicy;
	@JsonProperty("tripCode")
	private String tripCode;
	@JsonProperty("apiStatus")
	private ApiStatus apiStatus;
	@JsonProperty("inventoryType")
	private Integer inventoryType;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("opPNR")
	public String getOpPNR() {
	return opPNR;
	}

	@JsonProperty("opPNR")
	public void setOpPNR(String opPNR) {
	this.opPNR = opPNR;
	}

	@JsonProperty("etstnumber")
	public String getEtstnumber() {
	return etstnumber;
	}

	@JsonProperty("etstnumber")
	public void setEtstnumber(String etstnumber) {
	this.etstnumber = etstnumber;
	}

	@JsonProperty("commPCT")
	public Double getCommPCT() {
	return commPCT;
	}

	@JsonProperty("commPCT")
	public void setCommPCT(Double commPCT) {
	this.commPCT = commPCT;
	}

	@JsonProperty("totalFare")
	public Double getTotalFare() {
	return totalFare;
	}

	@JsonProperty("totalFare")
	public void setTotalFare(Double totalFare) {
	this.totalFare = totalFare;
	}

	@JsonProperty("cancellationPolicy")
	public String getCancellationPolicy() {
	return cancellationPolicy;
	}

	@JsonProperty("cancellationPolicy")
	public void setCancellationPolicy(String cancellationPolicy) {
	this.cancellationPolicy = cancellationPolicy;
	}

	@JsonProperty("tripCode")
	public String getTripCode() {
	return tripCode;
	}

	@JsonProperty("tripCode")
	public void setTripCode(String tripCode) {
	this.tripCode = tripCode;
	}

	@JsonProperty("apiStatus")
	public ApiStatus getApiStatus() {
	return apiStatus;
	}

	@JsonProperty("apiStatus")
	public void setApiStatus(ApiStatus apiStatus) {
	this.apiStatus = apiStatus;
	}

	@JsonProperty("inventoryType")
	public Integer getInventoryType() {
	return inventoryType;
	}

	@JsonProperty("inventoryType")
	public void setInventoryType(Integer inventoryType) {
	this.inventoryType = inventoryType;
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
