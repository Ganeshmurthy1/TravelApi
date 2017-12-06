package com.tayyarah.bus.model;

import java.util.List;

public class BusStations {

	private Status status;
	private List<Station> stationList;
	
	public List<Station> getStationList() {
		return stationList;
	}
	public void setStationList(List<Station> stationList) {
		this.stationList = stationList;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}	
}
