package com.tayyarah.api.flight.tbo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({
"ResultIndex",
"Source",
"IsLCC",
"IsRefundable",
"IsHoldAllowed",
"AirlineRemark",
"Fare",
"FareBreakdown",
"Segments",
"LastTicketDate",
"TicketAdvisory",
"FareRules",
"AirlineCode",
"ValidatingAirline",
"BaggageAllowance"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result implements Serializable{

/**
	 * 
	 */
private static final long serialVersionUID = 1L;
@JsonProperty("ResultIndex")
private String ResultIndex;
@JsonProperty("Source")
private Integer Source;
@JsonProperty("IsLCC")
private Boolean IsLCC;
@JsonProperty("IsRefundable")
private Boolean IsRefundable;
@JsonProperty("IsHoldAllowed")
private Boolean IsHoldAllowed;
@JsonProperty("GSTAllowed")
private Boolean GSTAllowed;
@JsonProperty("AirlineRemark")
private String AirlineRemark;
@JsonProperty("Fare")
private com.tayyarah.api.flight.tbo.model.Fare Fare;
@JsonProperty("FareBreakdown")
private List<com.tayyarah.api.flight.tbo.model.FareBreakdown> FareBreakdown = new ArrayList<com.tayyarah.api.flight.tbo.model.FareBreakdown>();
@JsonProperty("Segments")
private List<List<FlightSearchrresponseSegment>> Segments = new ArrayList<List<FlightSearchrresponseSegment>>();
@JsonProperty("LastTicketDate")
private String LastTicketDate;
@JsonProperty("TicketAdvisory")
private String TicketAdvisory;
@JsonProperty("FareRules")
private List<FareRule> FareRules = new ArrayList<FareRule>();
@JsonProperty("AirlineCode")
private String AirlineCode;
@JsonProperty("ValidatingAirline")
private String ValidatingAirline;
@JsonProperty("BaggageAllowance")
private String BaggageAllowance;

/**
* No args constructor for use in serialization
* 
*/
public Result() {
}

/**
* 
* @param FareBreakdown
* @param Source
* @param FareRules
* @param Fare
* @param ResultIndex
* @param LastTicketDate
* @param AirlineRemark
* @param TicketAdvisory
* @param Segments
* @param IsLCC
* @param IsRefundable
* @param ValidatingAirline
* @param AirlineCode
*/
public Result(String ResultIndex, Integer Source, Boolean IsLCC, Boolean IsRefundable, String AirlineRemark, com.tayyarah.api.flight.tbo.model.Fare Fare, List<com.tayyarah.api.flight.tbo.model.FareBreakdown> FareBreakdown, List<List<FlightSearchrresponseSegment>> Segments, String LastTicketDate, String TicketAdvisory, List<FareRule> FareRules, String AirlineCode, String ValidatingAirline, String BaggageAllowance) {
this.ResultIndex = ResultIndex;
this.Source = Source;
this.IsLCC = IsLCC;
this.IsRefundable = IsRefundable;
this.AirlineRemark = AirlineRemark;
this.Fare = Fare;
this.FareBreakdown = FareBreakdown;
this.Segments = Segments;
this.LastTicketDate = LastTicketDate;
this.TicketAdvisory = TicketAdvisory;
this.FareRules = FareRules;
this.AirlineCode = AirlineCode;
this.ValidatingAirline = ValidatingAirline;
this.BaggageAllowance = BaggageAllowance;
}

/**
* 
* @return
* The ResultIndex
*/
@JsonProperty("ResultIndex")
public String getResultIndex() {
return ResultIndex;
}

/**
* 
* @param ResultIndex
* The ResultIndex
*/
@JsonProperty("ResultIndex")
public void setResultIndex(String ResultIndex) {
this.ResultIndex = ResultIndex;
}

/**
* 
* @return
* The Source
*/
@JsonProperty("Source")
public Integer getSource() {
return Source;
}

/**
* 
* @param Source
* The Source
*/
@JsonProperty("Source")
public void setSource(Integer Source) {
this.Source = Source;
}

/**
* 
* @return
* The IsLCC
*/
@JsonProperty("IsLCC")
public Boolean getIsLCC() {
return IsLCC;
}

/**
* 
* @param IsLCC
* The IsLCC
*/
@JsonProperty("IsLCC")
public void setIsLCC(Boolean IsLCC) {
this.IsLCC = IsLCC;
}

/**
* 
* @return
* The IsRefundable
*/
@JsonProperty("IsRefundable")
public Boolean getIsRefundable() {
return IsRefundable;
}

/**
* 
* @param IsRefundable
* The IsRefundable
*/
@JsonProperty("IsRefundable")
public void setIsRefundable(Boolean IsRefundable) {
this.IsRefundable = IsRefundable;
}

/**
* 
* @return
* The AirlineRemark
*/
@JsonProperty("AirlineRemark")
public String getAirlineRemark() {
return AirlineRemark;
}

/**
* 
* @param AirlineRemark
* The AirlineRemark
*/
@JsonProperty("AirlineRemark")
public void setAirlineRemark(String AirlineRemark) {
this.AirlineRemark = AirlineRemark;
}

/**
* 
* @return
* The Fare
*/
@JsonProperty("Fare")
public com.tayyarah.api.flight.tbo.model.Fare getFare() {
return Fare;
}

/**
* 
* @param Fare
* The Fare
*/
@JsonProperty("Fare")
public void setFare(com.tayyarah.api.flight.tbo.model.Fare Fare) {
this.Fare = Fare;
}

/**
* 
* @return
* The FareBreakdown
*/
@JsonProperty("FareBreakdown")
public List<com.tayyarah.api.flight.tbo.model.FareBreakdown> getFareBreakdown() {
return FareBreakdown;
}

/**
* 
* @param FareBreakdown
* The FareBreakdown
*/
@JsonProperty("FareBreakdown")
public void setFareBreakdown(List<com.tayyarah.api.flight.tbo.model.FareBreakdown> FareBreakdown) {
this.FareBreakdown = FareBreakdown;
}

/**
* 
* @return
* The Segments
*/
@JsonProperty("Segments")
public List<List<FlightSearchrresponseSegment>> getSegments() {
return Segments;
}

/**
* 
* @param Segments
* The Segments
*/
@JsonProperty("Segments")
public void setSegments(List<List<FlightSearchrresponseSegment>> Segments) {
this.Segments = Segments;
}

/**
* 
* @return
* The LastTicketDate
*/
@JsonProperty("LastTicketDate")
public String getLastTicketDate() {
return LastTicketDate;
}

/**
* 
* @param LastTicketDate
* The LastTicketDate
*/
@JsonProperty("LastTicketDate")
public void setLastTicketDate(String LastTicketDate) {
this.LastTicketDate = LastTicketDate;
}

/**
* 
* @return
* The TicketAdvisory
*/
@JsonProperty("TicketAdvisory")
public String getTicketAdvisory() {
return TicketAdvisory;
}

/**
* 
* @param TicketAdvisory
* The TicketAdvisory
*/
@JsonProperty("TicketAdvisory")
public void setTicketAdvisory(String TicketAdvisory) {
this.TicketAdvisory = TicketAdvisory;
}

/**
* 
* @return
* The FareRules
*/
@JsonProperty("FareRules")
public List<FareRule> getFareRules() {
return FareRules;
}

/**
* 
* @param FareRules
* The FareRules
*/
@JsonProperty("FareRules")
public void setFareRules(List<FareRule> FareRules) {
this.FareRules = FareRules;
}

/**
* 
* @return
* The AirlineCode
*/
@JsonProperty("AirlineCode")
public String getAirlineCode() {
return AirlineCode;
}

/**
* 
* @param AirlineCode
* The AirlineCode
*/
@JsonProperty("AirlineCode")
public void setAirlineCode(String AirlineCode) {
this.AirlineCode = AirlineCode;
}

/**
* 
* @return
* The ValidatingAirline
*/
@JsonProperty("ValidatingAirline")
public String getValidatingAirline() {
return ValidatingAirline;
}

/**
* 
* @param ValidatingAirline
* The ValidatingAirline
*/
@JsonProperty("ValidatingAirline")
public void setValidatingAirline(String ValidatingAirline) {
this.ValidatingAirline = ValidatingAirline;
}

/**
* 
* @return
* The BaggageAllowance
*/
@JsonProperty("BaggageAllowance")
public String getBaggageAllowance() {
return BaggageAllowance;
}

/**
* 
* @param BaggageAllowance
* The BaggageAllowance
*/
@JsonProperty("BaggageAllowance")
public void setBaggageAllowance(String BaggageAllowance) {
this.BaggageAllowance = BaggageAllowance;
}

public Boolean getIsHoldAllowed() {
	return IsHoldAllowed;
}

public void setIsHoldAllowed(Boolean isHoldAllowed) {
	IsHoldAllowed = isHoldAllowed;
}

public Boolean getGSTAllowed() {
	return GSTAllowed;
}

public void setGSTAllowed(Boolean gSTAllowed) {
	GSTAllowed = gSTAllowed;
}

}