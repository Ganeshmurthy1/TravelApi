package com.tayyarah.flight.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.tayyarah.flight.util.api.bluestar.PriceComparator;

public class BluestarSearchData implements Serializable 
{
	private static final long serialVersionUID = 1L;
	Map<String, String[]> flightDetailMap = new HashMap<String, String[]>();
	List<String> keyList = new ArrayList<String>();
	
	@SuppressWarnings("unchecked")
	TreeSet<String> uniquePriceSet = new TreeSet<String>(new PriceComparator());
	Map<String, String[]> fareDetailMap = new HashMap<String, String[]>();
	List<String[]> flightDetailArray = new ArrayList<String[]>();
	
	Map<String, Integer> flightDetailsHeaderMap = new HashMap<String, Integer>();
	Map<String, Integer> flightFareDetailsHeaderMap = new HashMap<String, Integer>();
	
	public Map<String, Integer> getFlightFareDetailsHeaderMap() {
		return flightFareDetailsHeaderMap;
	}
	public void setFlightFareDetailsHeaderMap(Map<String, Integer> flightFareDetailsHeaderMap) {
		this.flightFareDetailsHeaderMap = flightFareDetailsHeaderMap;
	}
	public Map<String, String[]> getFlightDetailMap() {
		return flightDetailMap;
	}
	public void setFlightDetailMap(Map<String, String[]> flightDetailMap) {
		this.flightDetailMap = flightDetailMap;
	}
	public List<String> getKeyList() {
		return keyList;
	}
	public void setKeyList(List<String> keyList) {
		this.keyList = keyList;
	}
	
	public TreeSet<String> getUniquePriceSet() {
		return uniquePriceSet;
	}
	public void setUniquePriceSet(TreeSet<String> uniquePriceSet) {
		this.uniquePriceSet = uniquePriceSet;
	}
	public Map<String, String[]> getFareDetailMap() {
		return fareDetailMap;
	}
	public void setFareDetailMap(Map<String, String[]> fareDetailMap) {
		this.fareDetailMap = fareDetailMap;
	}
	public List<String[]> getFlightDetailArray() {
		return flightDetailArray;
	}
	public void setFlightDetailArray(List<String[]> flightDetailArray) {
		this.flightDetailArray = flightDetailArray;
	}
	public Map<String, Integer> getFlightDetailsHeaderMap() {
		return flightDetailsHeaderMap;
	}
	public void setFlightDetailsHeaderMap(Map<String, Integer> flightDetailsHeaderMap) {
		this.flightDetailsHeaderMap = flightDetailsHeaderMap;
	}
}