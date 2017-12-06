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

public class BookResponseSegment {

	@JsonProperty("TripIndicator")
	private Integer tripIndicator;
	@JsonProperty("SegmentIndicator")
	private Integer segmentIndicator;
	@JsonProperty("Airline")
	private Airline airline;
	@JsonProperty("AirlinePNR")
	private String airlinePNR;
	@JsonProperty("Origin")
	private Origin origin;
	@JsonProperty("Destination")
	private Destination destination;
	@JsonProperty("Duration")
	private Integer duration;
	@JsonProperty("GroundTime")
	private Integer groundTime;
	@JsonProperty("Mile")
	private Integer mile;
	@JsonProperty("StopOver")
	private Boolean stopOver;
	@JsonProperty("StopPoint")
	private String stopPoint;
	@JsonProperty("StopPointArrivalTime")
	private String stopPointArrivalTime;
	@JsonProperty("StopPointDepartureTime")
	private String stopPointDepartureTime;
	@JsonProperty("Craft")
	private String craft;
	@JsonProperty("Remark")
	private Object remark;
	@JsonProperty("IsETicketEligible")
	private Boolean isETicketEligible;
	@JsonProperty("FlightStatus")
	private String flightStatus;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("AccumulatedDuration")
	private Integer accumulatedDuration;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	* 
	* @return
	* The tripIndicator
	*/
	@JsonProperty("TripIndicator")
	public Integer getTripIndicator() {
	return tripIndicator;
	}

	/**
	* 
	* @param tripIndicator
	* The TripIndicator
	*/
	@JsonProperty("TripIndicator")
	public void setTripIndicator(Integer tripIndicator) {
	this.tripIndicator = tripIndicator;
	}

	/**
	* 
	* @return
	* The segmentIndicator
	*/
	@JsonProperty("SegmentIndicator")
	public Integer getSegmentIndicator() {
	return segmentIndicator;
	}

	/**
	* 
	* @param segmentIndicator
	* The SegmentIndicator
	*/
	@JsonProperty("SegmentIndicator")
	public void setSegmentIndicator(Integer segmentIndicator) {
	this.segmentIndicator = segmentIndicator;
	}

	/**
	* 
	* @return
	* The airline
	*/
	@JsonProperty("Airline")
	public Airline getAirline() {
	return airline;
	}

	/**
	* 
	* @param airline
	* The Airline
	*/
	@JsonProperty("Airline")
	public void setAirline(Airline airline) {
	this.airline = airline;
	}

	/**
	* 
	* @return
	* The airlinePNR
	*/
	@JsonProperty("AirlinePNR")
	public String getAirlinePNR() {
	return airlinePNR;
	}

	/**
	* 
	* @param airlinePNR
	* The AirlinePNR
	*/
	@JsonProperty("AirlinePNR")
	public void setAirlinePNR(String airlinePNR) {
	this.airlinePNR = airlinePNR;
	}

	/**
	* 
	* @return
	* The origin
	*/
	@JsonProperty("Origin")
	public Origin getOrigin() {
	return origin;
	}

	/**
	* 
	* @param origin
	* The Origin
	*/
	@JsonProperty("Origin")
	public void setOrigin(Origin origin) {
	this.origin = origin;
	}

	/**
	* 
	* @return
	* The destination
	*/
	@JsonProperty("Destination")
	public Destination getDestination() {
	return destination;
	}

	/**
	* 
	* @param destination
	* The Destination
	*/
	@JsonProperty("Destination")
	public void setDestination(Destination destination) {
	this.destination = destination;
	}

	/**
	* 
	* @return
	* The duration
	*/
	@JsonProperty("Duration")
	public Integer getDuration() {
	return duration;
	}

	/**
	* 
	* @param duration
	* The Duration
	*/
	@JsonProperty("Duration")
	public void setDuration(Integer duration) {
	this.duration = duration;
	}

	/**
	* 
	* @return
	* The groundTime
	*/
	@JsonProperty("GroundTime")
	public Integer getGroundTime() {
	return groundTime;
	}

	/**
	* 
	* @param groundTime
	* The GroundTime
	*/
	@JsonProperty("GroundTime")
	public void setGroundTime(Integer groundTime) {
	this.groundTime = groundTime;
	}

	/**
	* 
	* @return
	* The mile
	*/
	@JsonProperty("Mile")
	public Integer getMile() {
	return mile;
	}

	/**
	* 
	* @param mile
	* The Mile
	*/
	@JsonProperty("Mile")
	public void setMile(Integer mile) {
	this.mile = mile;
	}

	/**
	* 
	* @return
	* The stopOver
	*/
	@JsonProperty("StopOver")
	public Boolean getStopOver() {
	return stopOver;
	}

	/**
	* 
	* @param stopOver
	* The StopOver
	*/
	@JsonProperty("StopOver")
	public void setStopOver(Boolean stopOver) {
	this.stopOver = stopOver;
	}

	/**
	* 
	* @return
	* The stopPoint
	*/
	@JsonProperty("StopPoint")
	public String getStopPoint() {
	return stopPoint;
	}

	/**
	* 
	* @param stopPoint
	* The StopPoint
	*/
	@JsonProperty("StopPoint")
	public void setStopPoint(String stopPoint) {
	this.stopPoint = stopPoint;
	}

	/**
	* 
	* @return
	* The stopPointArrivalTime
	*/
	@JsonProperty("StopPointArrivalTime")
	public String getStopPointArrivalTime() {
	return stopPointArrivalTime;
	}

	/**
	* 
	* @param stopPointArrivalTime
	* The StopPointArrivalTime
	*/
	@JsonProperty("StopPointArrivalTime")
	public void setStopPointArrivalTime(String stopPointArrivalTime) {
	this.stopPointArrivalTime = stopPointArrivalTime;
	}

	/**
	* 
	* @return
	* The stopPointDepartureTime
	*/
	@JsonProperty("StopPointDepartureTime")
	public String getStopPointDepartureTime() {
	return stopPointDepartureTime;
	}

	/**
	* 
	* @param stopPointDepartureTime
	* The StopPointDepartureTime
	*/
	@JsonProperty("StopPointDepartureTime")
	public void setStopPointDepartureTime(String stopPointDepartureTime) {
	this.stopPointDepartureTime = stopPointDepartureTime;
	}

	/**
	* 
	* @return
	* The craft
	*/
	@JsonProperty("Craft")
	public String getCraft() {
	return craft;
	}

	/**
	* 
	* @param craft
	* The Craft
	*/
	@JsonProperty("Craft")
	public void setCraft(String craft) {
	this.craft = craft;
	}

	/**
	* 
	* @return
	* The remark
	*/
	@JsonProperty("Remark")
	public Object getRemark() {
	return remark;
	}

	/**
	* 
	* @param remark
	* The Remark
	*/
	@JsonProperty("Remark")
	public void setRemark(Object remark) {
	this.remark = remark;
	}

	/**
	* 
	* @return
	* The isETicketEligible
	*/
	@JsonProperty("IsETicketEligible")
	public Boolean getIsETicketEligible() {
	return isETicketEligible;
	}

	/**
	* 
	* @param isETicketEligible
	* The IsETicketEligible
	*/
	@JsonProperty("IsETicketEligible")
	public void setIsETicketEligible(Boolean isETicketEligible) {
	this.isETicketEligible = isETicketEligible;
	}

	/**
	* 
	* @return
	* The flightStatus
	*/
	@JsonProperty("FlightStatus")
	public String getFlightStatus() {
	return flightStatus;
	}

	/**
	* 
	* @param flightStatus
	* The FlightStatus
	*/
	@JsonProperty("FlightStatus")
	public void setFlightStatus(String flightStatus) {
	this.flightStatus = flightStatus;
	}

	/**
	* 
	* @return
	* The status
	*/
	@JsonProperty("Status")
	public String getStatus() {
	return status;
	}

	/**
	* 
	* @param status
	* The Status
	*/
	@JsonProperty("Status")
	public void setStatus(String status) {
	this.status = status;
	}

	/**
	* 
	* @return
	* The accumulatedDuration
	*/
	@JsonProperty("AccumulatedDuration")
	public Integer getAccumulatedDuration() {
	return accumulatedDuration;
	}

	/**
	* 
	* @param accumulatedDuration
	* The AccumulatedDuration
	*/
	@JsonProperty("AccumulatedDuration")
	public void setAccumulatedDuration(Integer accumulatedDuration) {
	this.accumulatedDuration = accumulatedDuration;
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
