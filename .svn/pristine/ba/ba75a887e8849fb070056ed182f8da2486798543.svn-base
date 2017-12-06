package com.tayyarah.api.bus.esmart.model;
/*Created By Vimal Susai Raj S 
 * Date : 19-5-2017*/
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
public class GetAvaiableSeats {

	@JsonProperty("seats")
	private List<Seat> seats ;
	@JsonProperty("boardingPoints")
	private List<BoardingPoint> boardingPoints;
	@JsonProperty("serviceTaxApplicable")
	private Boolean serviceTaxApplicable;
	@JsonProperty("inventoryType")
	private Integer inventoryType;
	@JsonProperty("apiStatus")
	private ApiStatus apiStatus;
	@JsonProperty("etsServiceChargePer")
	private Integer etsServiceChargePer;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("seats")
	public List<Seat> getSeats() {
	return seats;
	}

	@JsonProperty("seats")
	public void setSeats(List<Seat> seats) {
	this.seats = seats;
	}

	@JsonProperty("boardingPoints")
	public List<BoardingPoint> getBoardingPoints() {
	return boardingPoints;
	}

	@JsonProperty("boardingPoints")
	public void setBoardingPoints(List<BoardingPoint> boardingPoints) {
	this.boardingPoints = boardingPoints;
	}

	@JsonProperty("serviceTaxApplicable")
	public Boolean getServiceTaxApplicable() {
	return serviceTaxApplicable;
	}

	@JsonProperty("serviceTaxApplicable")
	public void setServiceTaxApplicable(Boolean serviceTaxApplicable) {
	this.serviceTaxApplicable = serviceTaxApplicable;
	}

	@JsonProperty("inventoryType")
	public Integer getInventoryType() {
	return inventoryType;
	}

	@JsonProperty("inventoryType")
	public void setInventoryType(Integer inventoryType) {
	this.inventoryType = inventoryType;
	}

	@JsonProperty("apiStatus")
	public ApiStatus getApiStatus() {
	return apiStatus;
	}

	@JsonProperty("apiStatus")
	public void setApiStatus(ApiStatus apiStatus) {
	this.apiStatus = apiStatus;
	}

	@JsonProperty("etsServiceChargePer")
	public Integer getEtsServiceChargePer() {
	return etsServiceChargePer;
	}

	@JsonProperty("etsServiceChargePer")
	public void setEtsServiceChargePer(Integer etsServiceChargePer) {
	this.etsServiceChargePer = etsServiceChargePer;
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
