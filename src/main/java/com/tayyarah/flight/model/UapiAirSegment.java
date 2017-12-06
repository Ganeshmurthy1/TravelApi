/**
@Author yogeshsharma
28-Aug-2015 2015
UapiAirSegment.java
 */
/**
 * 
 */
package com.tayyarah.flight.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;

public class UapiAirSegment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private String availabilitySource;	
	private String availabilityDisplayType;
	private String origin;	
	private String destination;
	private String departureTime;	
	private String arrivalTime;
	private BigInteger flightTime;	
	private BigInteger distance;    
	private String farebasiscode;
	private String providecode;    
	private String classOfService;	
	private int group;
	private String carrier;	
	private String key;	
	private String flightNumber;
	private String bookingCurrency;
	private BigDecimal exchangeRate;
	private BigDecimal apiToBaseExchangeRate;

	public BigDecimal getApiToBaseExchangeRate() {
		return apiToBaseExchangeRate;
	}
	public void setApiToBaseExchangeRate(BigDecimal apiToBaseExchangeRate) {
		this.apiToBaseExchangeRate = apiToBaseExchangeRate;
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
	public String getClassOfService() {
		return classOfService;
	}
	public void setClassOfService(String classOfService) {
		this.classOfService = classOfService;
	}
	public String getProvidecode() {
		return providecode;
	}
	public void setProvidecode(String providecode) {
		this.providecode = providecode;
	}
	public String getFarebasiscode() {
		return farebasiscode;
	}
	public void setFarebasiscode(String farebasiscode) {
		this.farebasiscode = farebasiscode;
	}
	public String getBookingCurrency() {
		return bookingCurrency;
	}
	public void setBookingCurrency(String bookingCurrency) {
		this.bookingCurrency = bookingCurrency;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
}