package com.tayyarah.api.bus.esmart.model;
/*Created By Vimal Susai Raj S 
 * Date : 19-5-2017*/

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Seat {

@JsonProperty("bookedBy")
private String bookedBy;
@JsonProperty("fare")
private Integer fare;
@JsonProperty("zIndex")
private Integer zIndex;
@JsonProperty("serviceTaxAmount")
private Integer serviceTaxAmount;
@JsonProperty("commission")
private Integer commission;
@JsonProperty("operatorServiceChargeAbsolute")
private Integer operatorServiceChargeAbsolute;
@JsonProperty("operatorServiceChargePercent")
private Integer operatorServiceChargePercent;
@JsonProperty("totalFareWithTaxes")
private Integer totalFareWithTaxes;
@JsonProperty("ladiesSeat")
private Boolean ladiesSeat;
@JsonProperty("ac")
private Boolean ac;
@JsonProperty("sleeper")
private Boolean sleeper;
@JsonProperty("serviceTaxPer")
private Integer serviceTaxPer;
@JsonProperty("width")
private Integer width;
@JsonProperty("available")
private Boolean available;
@JsonProperty("row")
private Integer row;
@JsonProperty("column")
private Integer column;
@JsonProperty("length")
private Integer length;
@JsonProperty("id")
private String id;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("bookedBy")
public String getBookedBy() {
return bookedBy;
}

@JsonProperty("bookedBy")
public void setBookedBy(String bookedBy) {
this.bookedBy = bookedBy;
}

@JsonProperty("fare")
public Integer getFare() {
return fare;
}

@JsonProperty("fare")
public void setFare(Integer fare) {
this.fare = fare;
}

@JsonProperty("zIndex")
public Integer getZIndex() {
return zIndex;
}

@JsonProperty("zIndex")
public void setZIndex(Integer zIndex) {
this.zIndex = zIndex;
}

@JsonProperty("serviceTaxAmount")
public Integer getServiceTaxAmount() {
return serviceTaxAmount;
}

@JsonProperty("serviceTaxAmount")
public void setServiceTaxAmount(Integer serviceTaxAmount) {
this.serviceTaxAmount = serviceTaxAmount;
}

@JsonProperty("commission")
public Integer getCommission() {
return commission;
}

@JsonProperty("commission")
public void setCommission(Integer commission) {
this.commission = commission;
}

@JsonProperty("operatorServiceChargeAbsolute")
public Integer getOperatorServiceChargeAbsolute() {
return operatorServiceChargeAbsolute;
}

@JsonProperty("operatorServiceChargeAbsolute")
public void setOperatorServiceChargeAbsolute(Integer operatorServiceChargeAbsolute) {
this.operatorServiceChargeAbsolute = operatorServiceChargeAbsolute;
}

@JsonProperty("operatorServiceChargePercent")
public Integer getOperatorServiceChargePercent() {
return operatorServiceChargePercent;
}

@JsonProperty("operatorServiceChargePercent")
public void setOperatorServiceChargePercent(Integer operatorServiceChargePercent) {
this.operatorServiceChargePercent = operatorServiceChargePercent;
}

@JsonProperty("totalFareWithTaxes")
public Integer getTotalFareWithTaxes() {
return totalFareWithTaxes;
}

@JsonProperty("totalFareWithTaxes")
public void setTotalFareWithTaxes(Integer totalFareWithTaxes) {
this.totalFareWithTaxes = totalFareWithTaxes;
}

@JsonProperty("ladiesSeat")
public Boolean getLadiesSeat() {
return ladiesSeat;
}

@JsonProperty("ladiesSeat")
public void setLadiesSeat(Boolean ladiesSeat) {
this.ladiesSeat = ladiesSeat;
}

@JsonProperty("ac")
public Boolean getAc() {
return ac;
}

@JsonProperty("ac")
public void setAc(Boolean ac) {
this.ac = ac;
}

@JsonProperty("sleeper")
public Boolean getSleeper() {
return sleeper;
}

@JsonProperty("sleeper")
public void setSleeper(Boolean sleeper) {
this.sleeper = sleeper;
}

@JsonProperty("serviceTaxPer")
public Integer getServiceTaxPer() {
return serviceTaxPer;
}

@JsonProperty("serviceTaxPer")
public void setServiceTaxPer(Integer serviceTaxPer) {
this.serviceTaxPer = serviceTaxPer;
}

@JsonProperty("width")
public Integer getWidth() {
return width;
}

@JsonProperty("width")
public void setWidth(Integer width) {
this.width = width;
}

@JsonProperty("available")
public Boolean getAvailable() {
return available;
}

@JsonProperty("available")
public void setAvailable(Boolean available) {
this.available = available;
}

@JsonProperty("row")
public Integer getRow() {
return row;
}

@JsonProperty("row")
public void setRow(Integer row) {
this.row = row;
}

@JsonProperty("column")
public Integer getColumn() {
return column;
}

@JsonProperty("column")
public void setColumn(Integer column) {
this.column = column;
}

@JsonProperty("length")
public Integer getLength() {
return length;
}

@JsonProperty("length")
public void setLength(Integer length) {
this.length = length;
}

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setId(String id) {
this.id = id;
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