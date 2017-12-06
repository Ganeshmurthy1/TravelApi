/**
@Author ilyas
27-Aug-2015 
UAPISearchFlightKeyMap.java
 */
/**
 * 
 */
package com.tayyarah.flight.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class UAPISearchFlightKeyMap implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private BigDecimal exchangeRate;
	private Map<String ,String[]> faredetailMap = new HashMap<String, String[]>();
	private Map<String, FareFlightSegment> FareFlightSegmentMap = new HashMap<String, FareFlightSegment>();
	private LinkedHashMap<String, FlightSegments> FlightSegmentstMap = new LinkedHashMap<String, FlightSegments>();
	private Map<String, FareRules> FareRulesMap = new HashMap<String, FareRules>();
	private LinkedHashMap<String, List<UapiAirSegment>> uapiAirSegmentListMap = new LinkedHashMap<String, List<UapiAirSegment>>();
	private Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap=new HashMap<String,List<FlightMarkUpConfig>>();
	private Set<String> airlineList = new HashSet<String>();
	private Flightsearch flightsearch;
	private MarkupCommissionDetails markupCommissionDetails;	

	public Set<String> getAirlineList() {
		return airlineList;
	}
	public void setAirlineList(Set<String> airlineList) {
		this.airlineList = airlineList;
	}
	public Map<String, List<FlightMarkUpConfig>> getFlightMarkUpConfiglistMap() {
		return FlightMarkUpConfiglistMap;
	}
	public void setFlightMarkUpConfiglistMap(
			Map<String, List<FlightMarkUpConfig>> flightMarkUpConfiglistMap) {
		FlightMarkUpConfiglistMap = flightMarkUpConfiglistMap;
	}
	public Map<String, String[]> getFaredetailMap() {
		return faredetailMap;
	}
	public void setFaredetailMap(Map<String, String[]> faredetailMap) {
		this.faredetailMap = faredetailMap;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public Map<String, FareFlightSegment> getFareFlightSegmentMap() {
		return FareFlightSegmentMap;
	}
	public void setFareFlightSegmentMap(
			Map<String, FareFlightSegment> fareFlightSegmentMap) {
		FareFlightSegmentMap = fareFlightSegmentMap;
	}
	public LinkedHashMap<String, FlightSegments> getFlightSegmentstMap() {
		return FlightSegmentstMap;
	}
	public void setFlightSegmentstMap(
			LinkedHashMap<String, FlightSegments> flightSegmentstMap) {
		FlightSegmentstMap = flightSegmentstMap;
	}
	public Map<String, FareRules> getFareRulesMap() {
		return FareRulesMap;
	}
	public void setFareRulesMap(Map<String, FareRules> fareRulesMap) {
		FareRulesMap = fareRulesMap;
	}	
	public LinkedHashMap<String, List<UapiAirSegment>> getUapiAirSegmentListMap() {
		return uapiAirSegmentListMap;
	}	
	public void setUapiAirSegmentListMap(
			LinkedHashMap<String, List<UapiAirSegment>> uapiAirSegmentListMap) {
		this.uapiAirSegmentListMap = uapiAirSegmentListMap;
	}
	public MarkupCommissionDetails getMarkupCommissionDetails() {
		return markupCommissionDetails;
	}
	public void setMarkupCommissionDetails(
			MarkupCommissionDetails markupCommissionDetails) {
		this.markupCommissionDetails = markupCommissionDetails;
	}
	public Flightsearch getFlightsearch() {
		return flightsearch;
	}	
	public void setFlightsearch(Flightsearch flightsearch) {
		this.flightsearch = flightsearch;
	}
}
