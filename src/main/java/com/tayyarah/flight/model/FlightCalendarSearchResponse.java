package com.tayyarah.flight.model;

import java.util.List;

public class FlightCalendarSearchResponse {

	private String ori;
	private String dest;
	private List<CalendarResponseData> searchResult;
	
	public String getOri() {
		return ori;
	}
	public void setOri(String ori) {
		this.ori = ori;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public List<CalendarResponseData> getSearchResult() {
		return searchResult;
	}
	public void setSearchResult(List<CalendarResponseData> searchResult) {
		this.searchResult = searchResult;
	}

}
