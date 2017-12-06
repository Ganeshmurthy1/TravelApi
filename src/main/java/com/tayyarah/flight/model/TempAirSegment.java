package com.tayyarah.flight.model;

import java.io.Serializable;
import java.math.BigInteger;








import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tempairsegment")
public class TempAirSegment implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id	
	@Column(name = "id")
	@GeneratedValue
	private int id;
	@Column(name = "flight_index")
	private String flightindex;
	@Column(name = "group_id")
	private int group;
	@Column(name = "carrier")
	private String carrier;
	@Column(name = "key_id")
	private String key;
	@Column(name = "flight_number")
	private String flightNumber;	
	@Column(name = "availability_source")
	private String availabilitySource;
	@Column(name = "availability_displaytype")
	private String availabilityDisplayType;
	@Column(name = "origin")
	private String origin;
	@Column(name = "destination")
	private String destination;
	@Column(name = "departure_time")
	private String departureTime;
	@Column(name = "arrival_time")
	private String arrivalTime;
	@Column(name = "flight_time")
	private BigInteger flightTime;
	@Column(name = "distance")
	private BigInteger distance;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getFlightindex() {
		return flightindex;
	}
	public void setFlightindex(String flightindex) {
		this.flightindex = flightindex;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getAvailabilitySource() {
		return availabilitySource;
	}
	public void setAvailabilitySource(String availabilitySource) {
		this.availabilitySource = availabilitySource;
	}
	public String getAvailabilityDisplayType() {
		return availabilityDisplayType;
	}
	public void setAvailabilityDisplayType(String availabilityDisplayType) {
		this.availabilityDisplayType = availabilityDisplayType;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public BigInteger getFlightTime() {
		return flightTime;
	}
	public void setFlightTime(BigInteger flightTime) {
		this.flightTime = flightTime;
	}
	public BigInteger getDistance() {
		return distance;
	}
	public void setDistance(BigInteger distance) {
		this.distance = distance;
	}
}