package com.tayyarah.bus.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TayyarahBusSeatMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,List<BusMarkUpConfig>> busMarkUpConfiglistMap = new HashMap<String,List<BusMarkUpConfig>>();
	private BusMarkupCommissionDetails markupCommissionDetails;
	private Map<String,AvailableSeats> availableSeatslistMap = new HashMap<String,AvailableSeats>();
	private Map<String,com.tayyarah.bus.model.Seat> seatMap = new HashMap<String,com.tayyarah.bus.model.Seat>();
	private BusLayoutRequest busLayoutRequest;
	
	public BusLayoutRequest getBusLayoutRequest() {
		return busLayoutRequest;
	}
	public void setBusLayoutRequest(BusLayoutRequest busLayoutRequest) {
		this.busLayoutRequest = busLayoutRequest;
	}
	public Map<String, List<BusMarkUpConfig>> getBusMarkUpConfiglistMap() {
		return busMarkUpConfiglistMap;
	}
	public Map<String, AvailableSeats> getAvailableSeatslistMap() {
		return availableSeatslistMap;
	}
	public Map<String, com.tayyarah.bus.model.Seat> getSeatMap() {
		return seatMap;
	}
	public void setBusMarkUpConfiglistMap(Map<String, List<BusMarkUpConfig>> busMarkUpConfiglistMap) {
		this.busMarkUpConfiglistMap = busMarkUpConfiglistMap;
	}
	public void setAvailableSeatslistMap(Map<String, AvailableSeats> availableSeatslistMap) {
		this.availableSeatslistMap = availableSeatslistMap;
	}
	public void setSeatMap(Map<String, com.tayyarah.bus.model.Seat> seatMap) {
		this.seatMap = seatMap;
	}
	public BusMarkupCommissionDetails getMarkupCommissionDetails() {
		return markupCommissionDetails;
	}
	public void setMarkupCommissionDetails(BusMarkupCommissionDetails markupCommissionDetails) {
		this.markupCommissionDetails = markupCommissionDetails;
	}
	
}
