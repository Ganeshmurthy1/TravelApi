package com.tayyarah.bus.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tayyarah.bus.model.BusMarkUpConfig;


public class TayyarahBusSearchMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal exchangeRate;
	private Map<String, AvailableBuses> availableBusesMap = new HashMap<String, AvailableBuses>();
	private Map<String, List<BoardingPoint>> boardingPointsMap = new HashMap<String, List<BoardingPoint>>();
	private Map<String, List<DroppingPoint>> droppingPointMap = new HashMap<String, List<DroppingPoint>>();
	private Map<String,List<BusMarkUpConfig>> busMarkUpConfiglistMap = new HashMap<String,List<BusMarkUpConfig>>();
	private BusSearchRequest busSearchRequest;
	private BusMarkupCommissionDetails markupCommissionDetails;
	
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public Map<String, AvailableBuses> getAvailableBusesMap() {
		return availableBusesMap;
	}
	public Map<String, List<BusMarkUpConfig>> getBusMarkUpConfiglistMap() {
		return busMarkUpConfiglistMap;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public void setAvailableBusesMap(Map<String, AvailableBuses> availableBusesMap) {
		this.availableBusesMap = availableBusesMap;
	}
	public void setBusMarkUpConfiglistMap(Map<String, List<BusMarkUpConfig>> busMarkUpConfiglistMap) {
		this.busMarkUpConfiglistMap = busMarkUpConfiglistMap;
	}
	public Map<String, List<BoardingPoint>> getBoardingPointsMap() {
		return boardingPointsMap;
	}
	public Map<String, List<DroppingPoint>> getDroppingPointMap() {
		return droppingPointMap;
	}
	public void setBoardingPointsMap(Map<String, List<BoardingPoint>> boardingPointsMap) {
		this.boardingPointsMap = boardingPointsMap;
	}
	public void setDroppingPointMap(Map<String, List<DroppingPoint>> droppingPointMap) {
		this.droppingPointMap = droppingPointMap;
	}
	public BusSearchRequest getBusSearchRequest() {
		return busSearchRequest;
	}
	public void setBusSearchRequest(BusSearchRequest busSearchRequest) {
		this.busSearchRequest = busSearchRequest;
	}
	public BusMarkupCommissionDetails getMarkupCommissionDetails() {
		return markupCommissionDetails;
	}
	public void setMarkupCommissionDetails(BusMarkupCommissionDetails markupCommissionDetails) {
		this.markupCommissionDetails = markupCommissionDetails;
	}

}
