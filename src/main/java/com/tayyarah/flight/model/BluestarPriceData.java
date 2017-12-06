package com.tayyarah.flight.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
public class BluestarPriceData extends Passenger implements Serializable 
{
	private static final long serialVersionUID = 1L;
	String[] flightdeatilArray;
	Map<String ,String[]> faredetailMap;
	Map<String, Integer> fareDetailsHeaderMap = new HashMap<String, Integer>();
	Map<String, Integer> flightDetailsHeaderMap = new HashMap<String, Integer>();
	
	public Map<String, Integer> getFlightDetailsHeaderMap() {
		return flightDetailsHeaderMap;
	}
	public void setFlightDetailsHeaderMap(Map<String, Integer> flightDetailsHeaderMap) {
		this.flightDetailsHeaderMap = flightDetailsHeaderMap;
	}
	public Map<String, Integer> getFareDetailsHeaderMap() {
		return fareDetailsHeaderMap;
	}
	public void setFareDetailsHeaderMap(Map<String, Integer> fareDetailsHeaderMap) {
		this.fareDetailsHeaderMap = fareDetailsHeaderMap;
	}
	public String[] getFlightdeatilArray() {
		return flightdeatilArray;
	}
	public void setFlightdeatilArray(String[] flightdeatilArray) {
		this.flightdeatilArray = flightdeatilArray;
	}
	public Map<String, String[]> getFaredetailMap() {
		return faredetailMap;
	}
	public void setFaredetailMap(Map<String, String[]> faredetailMap) {
		this.faredetailMap = faredetailMap;
	}
}