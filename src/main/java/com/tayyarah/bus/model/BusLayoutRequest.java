package com.tayyarah.bus.model;

import java.io.Serializable;

public class BusLayoutRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String app_key;
	private String origin;
	private String destination;
	private String onwardDate;
	private String inventoryType;
	private String routeScheduleId;
	private String searchkey;
	
	public String getApp_key() {
		return app_key;
	}
	public String getOrigin() {
		return origin;
	}
	public String getDestination() {
		return destination;
	}
	public String getOnwardDate() {
		return onwardDate;
	}
	public String getInventoryType() {
		return inventoryType;
	}
	public String getRouteScheduleId() {
		return routeScheduleId;
	}
	public String getSearchkey() {
		return searchkey;
	}
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public void setOnwardDate(String onwardDate) {
		this.onwardDate = onwardDate;
	}
	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}
	public void setRouteScheduleId(String routeScheduleId) {
		this.routeScheduleId = routeScheduleId;
	}
	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}
}
