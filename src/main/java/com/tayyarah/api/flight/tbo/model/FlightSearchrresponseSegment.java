
package com.tayyarah.api.flight.tbo.model;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightSearchrresponseSegment implements Serializable{

	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@JsonProperty("IsClassChanged")
private Boolean IsClassChanged;	
@JsonProperty("Baggage")
private String Baggage;
@JsonProperty("CabinBaggage")
private String CabinBaggage;
@JsonProperty("TripIndicator")
private Integer TripIndicator;
@JsonProperty("SegmentIndicator")
private Integer SegmentIndicator;
@JsonProperty("NoOfSeatAvailable")
private Integer NoOfSeatAvailable;
@JsonProperty("Airline")
private com.tayyarah.api.flight.tbo.model.Airline Airline;
@JsonProperty("Origin")
private com.tayyarah.api.flight.tbo.model.Origin Origin;
@JsonProperty("Destination")
private com.tayyarah.api.flight.tbo.model.Destination Destination;
@JsonProperty("AccumulatedDuration")
private Integer AccumulatedDuration;
@JsonProperty("Duration")
private Integer Duration;
@JsonProperty("GroundTime")
private Integer GroundTime;
@JsonProperty("Mile")
private Integer Mile;
@JsonProperty("StopOver")
private Boolean StopOver;
@JsonProperty("StopPoint")
private String StopPoint;
@JsonProperty("StopPointArrivalTime")
private String StopPointArrivalTime;
@JsonProperty("StopPointDepartureTime")
private String StopPointDepartureTime;
@JsonProperty("Craft")
private String Craft;
@JsonProperty("Remark")
private String Remark;
@JsonProperty("IsETicketEligible")
private Boolean IsETicketEligible;
@JsonProperty("FlightStatus")
private String FlightStatus;
@JsonProperty("Status")
private String Status;
@JsonProperty("Availability")
private List<Availability> Availability;

/**
* No args constructor for use in serialization
* 
*/
public FlightSearchrresponseSegment() {
}

/**
* 
* @param StopPointArrivalTime
* @param Airline
* @param StopOver
* @param AccumulatedDuration
* @param StopPointDepartureTime
* @param Origin
* @param Mile
* @param IsETicketEligible
* @param Status
* @param SegmentIndicator
* @param TripIndicator
* @param StopPoint
* @param Craft
* @param Duration
* @param GroundTime
* @param FlightStatus
* @param Destination
*/
public FlightSearchrresponseSegment(Integer TripIndicator, Integer SegmentIndicator, Integer NoOfSeatAvailable, com.tayyarah.api.flight.tbo.model.Airline Airline, com.tayyarah.api.flight.tbo.model.Origin Origin, com.tayyarah.api.flight.tbo.model.Destination Destination, Integer AccumulatedDuration, Integer Duration, Integer GroundTime, Integer Mile, Boolean StopOver, String StopPoint, String StopPointArrivalTime, String StopPointDepartureTime, String Craft, String Remark, Boolean IsETicketEligible, String FlightStatus, String Status) {
this.TripIndicator = TripIndicator;
this.SegmentIndicator = SegmentIndicator;
this.NoOfSeatAvailable = NoOfSeatAvailable;
this.Airline = Airline;
this.Origin = Origin;
this.Destination = Destination;
this.AccumulatedDuration = AccumulatedDuration;
this.Duration = Duration;
this.GroundTime = GroundTime;
this.Mile = Mile;
this.StopOver = StopOver;
this.StopPoint = StopPoint;
this.StopPointArrivalTime = StopPointArrivalTime;
this.StopPointDepartureTime = StopPointDepartureTime;
this.Craft = Craft;
this.Remark = Remark;
this.IsETicketEligible = IsETicketEligible;
this.FlightStatus = FlightStatus;
this.Status = Status;
}

/**
* 
* @return
* The TripIndicator
*/
@JsonProperty("TripIndicator")
public Integer getTripIndicator() {
return TripIndicator;
}

/**
* 
* @param TripIndicator
* The TripIndicator
*/
@JsonProperty("TripIndicator")
public void setTripIndicator(Integer TripIndicator) {
this.TripIndicator = TripIndicator;
}

/**
* 
* @return
* The SegmentIndicator
*/
@JsonProperty("SegmentIndicator")
public Integer getSegmentIndicator() {
return SegmentIndicator;
}

/**
* 
* @param SegmentIndicator
* The SegmentIndicator
*/
@JsonProperty("SegmentIndicator")
public void setSegmentIndicator(Integer SegmentIndicator) {
this.SegmentIndicator = SegmentIndicator;
}

/**
* 
* @return
* The SegmentIndicator
*/
@JsonProperty("NoOfSeatAvailable")
public Integer getNoOfSeatAvailable() {
return NoOfSeatAvailable;
}

/**
* 
* @param SegmentIndicator
* The SegmentIndicator
*/
@JsonProperty("NoOfSeatAvailable")
public void setNoOfSeatAvailable(Integer NoOfSeatAvailable) {
this.NoOfSeatAvailable = NoOfSeatAvailable;
}

/**
* 
* @return
* The Airline
*/
@JsonProperty("Airline")
public com.tayyarah.api.flight.tbo.model.Airline getAirline() {
return Airline;
}

/**
* 
* @param Airline
* The Airline
*/
@JsonProperty("Airline")
public void setAirline(com.tayyarah.api.flight.tbo.model.Airline Airline) {
this.Airline = Airline;
}

/**
* 
* @return
* The Origin
*/
@JsonProperty("Origin")
public com.tayyarah.api.flight.tbo.model.Origin getOrigin() {
return Origin;
}

/**
* 
* @param Origin
* The Origin
*/
@JsonProperty("Origin")
public void setOrigin(com.tayyarah.api.flight.tbo.model.Origin Origin) {
this.Origin = Origin;
}

/**
* 
* @return
* The Destination
*/
@JsonProperty("Destination")
public com.tayyarah.api.flight.tbo.model.Destination getDestination() {
return Destination;
}

/**
* 
* @param Destination
* The Destination
*/
@JsonProperty("Destination")
public void setDestination(com.tayyarah.api.flight.tbo.model.Destination Destination) {
this.Destination = Destination;
}

/**
* 
* @return
* The AccumulatedDuration
*/
@JsonProperty("AccumulatedDuration")
public Integer getAccumulatedDuration() {
return AccumulatedDuration;
}

/**
* 
* @param AccumulatedDuration
* The AccumulatedDuration
*/
@JsonProperty("AccumulatedDuration")
public void setAccumulatedDuration(Integer AccumulatedDuration) {
this.AccumulatedDuration = AccumulatedDuration;
}

/**
* 
* @return
* The Duration
*/
@JsonProperty("Duration")
public Integer getDuration() {
return Duration;
}

/**
* 
* @param Duration
* The Duration
*/
@JsonProperty("Duration")
public void setDuration(Integer Duration) {
this.Duration = Duration;
}

/**
* 
* @return
* The GroundTime
*/
@JsonProperty("GroundTime")
public Integer getGroundTime() {
return GroundTime;
}

/**
* 
* @param GroundTime
* The GroundTime
*/
@JsonProperty("GroundTime")
public void setGroundTime(Integer GroundTime) {
this.GroundTime = GroundTime;
}

/**
* 
* @return
* The Mile
*/
@JsonProperty("Mile")
public Integer getMile() {
return Mile;
}

/**
* 
* @param Mile
* The Mile
*/
@JsonProperty("Mile")
public void setMile(Integer Mile) {
this.Mile = Mile;
}

/**
* 
* @return
* The StopOver
*/
@JsonProperty("StopOver")
public Boolean getStopOver() {
return StopOver;
}

/**
* 
* @param StopOver
* The StopOver
*/
@JsonProperty("StopOver")
public void setStopOver(Boolean StopOver) {
this.StopOver = StopOver;
}

/**
* 
* @return
* The StopPoint
*/
@JsonProperty("StopPoint")
public String getStopPoint() {
return StopPoint;
}

/**
* 
* @param StopPoint
* The StopPoint
*/
@JsonProperty("StopPoint")
public void setStopPoint(String StopPoint) {
this.StopPoint = StopPoint;
}

/**
* 
* @return
* The StopPointArrivalTime
*/
@JsonProperty("StopPointArrivalTime")
public String getStopPointArrivalTime() {
return StopPointArrivalTime;
}

/**
* 
* @param StopPointArrivalTime
* The StopPointArrivalTime
*/
@JsonProperty("StopPointArrivalTime")
public void setStopPointArrivalTime(String StopPointArrivalTime) {
this.StopPointArrivalTime = StopPointArrivalTime;
}

/**
* 
* @return
* The StopPointDepartureTime
*/
@JsonProperty("StopPointDepartureTime")
public String getStopPointDepartureTime() {
return StopPointDepartureTime;
}

/**
* 
* @param StopPointDepartureTime
* The StopPointDepartureTime
*/
@JsonProperty("StopPointDepartureTime")
public void setStopPointDepartureTime(String StopPointDepartureTime) {
this.StopPointDepartureTime = StopPointDepartureTime;
}

/**
* 
* @return
* The Craft
*/
@JsonProperty("Craft")
public String getCraft() {
return Craft;
}

/**
* 
* @param Craft
* The Craft
*/
@JsonProperty("Craft")
public void setCraft(String Craft) {
this.Craft = Craft;
}

/**
* 
* @return
* The Remark
*/
@JsonProperty("Remark")
public String getRemark() {
return Remark;
}

/**
* 
* @param Remark
* The Remark
*/
@JsonProperty("Remark")
public void setRemark(String Remark) {
this.Remark = Remark;
}


/**
* 
* @return
* The IsETicketEligible
*/
@JsonProperty("IsETicketEligible")
public Boolean getIsETicketEligible() {
return IsETicketEligible;
}

/**
* 
* @param IsETicketEligible
* The IsETicketEligible
*/
@JsonProperty("IsETicketEligible")
public void setIsETicketEligible(Boolean IsETicketEligible) {
this.IsETicketEligible = IsETicketEligible;
}

/**
* 
* @return
* The FlightStatus
*/
@JsonProperty("FlightStatus")
public String getFlightStatus() {
return FlightStatus;
}

/**
* 
* @param FlightStatus
* The FlightStatus
*/
@JsonProperty("FlightStatus")
public void setFlightStatus(String FlightStatus) {
this.FlightStatus = FlightStatus;
}

/**
* 
* @return
* The Status
*/
@JsonProperty("Status")
public String getStatus() {
return Status;
}

/**
* 
* @param Status
* The Status
*/
@JsonProperty("Status")
public void setStatus(String Status) {
this.Status = Status;
}

@JsonProperty("IsClassChanged")
public Boolean getIsClassChanged() {
	return IsClassChanged;
}

@JsonProperty("IsClassChanged")
public void setIsClassChanged(Boolean isClassChanged) {
	IsClassChanged = isClassChanged;
}
@JsonProperty("Availability")
public List<Availability> getAvailability() {
	return Availability;
}
@JsonProperty("Availability")
public void setAvailability(List<Availability> availability) {
	Availability = availability;
}

public String getCabinBaggage() {
	return CabinBaggage;
}

public void setCabinBaggage(String cabinBaggage) {
	CabinBaggage = cabinBaggage;
}

public String getBaggage() {
	return Baggage;
}

public void setBaggage(String baggage) {
	Baggage = baggage;
}




}