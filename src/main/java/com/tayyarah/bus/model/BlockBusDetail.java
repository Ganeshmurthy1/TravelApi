package com.tayyarah.bus.model;

import java.io.Serializable;

public class BlockBusDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String busOperator;
	private String busType;
	private BoardingPoint boardingPoint;
	private DroppingPoint droppingPoint;
	private String departureTime;
	private String arrivalTime;
	private Boolean mTicketAllowed;
	private Boolean partialCancellationAllowed;
	private String cancellationPolicy;
	
	public String getCancellationPolicy() {
		return cancellationPolicy;
	}
	public void setCancellationPolicy(String cancellationPolicy) {
		this.cancellationPolicy = cancellationPolicy;
	}
	public String getBusOperator() {
		return busOperator;
	}
	public String getBusType() {
		return busType;
	}
	public BoardingPoint getBoardingPoint() {
		return boardingPoint;
	}
	public DroppingPoint getDroppingPoint() {
		return droppingPoint;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public Boolean getmTicketAllowed() {
		return mTicketAllowed;
	}
	public Boolean getPartialCancellationAllowed() {
		return partialCancellationAllowed;
	}
	public void setBusOperator(String busOperator) {
		this.busOperator = busOperator;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public void setBoardingPoint(BoardingPoint boardingPoint) {
		this.boardingPoint = boardingPoint;
	}
	public void setDroppingPoint(DroppingPoint droppingPoint) {
		this.droppingPoint = droppingPoint;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public void setmTicketAllowed(Boolean mTicketAllowed) {
		this.mTicketAllowed = mTicketAllowed;
	}
	public void setPartialCancellationAllowed(Boolean partialCancellationAllowed) {
		this.partialCancellationAllowed = partialCancellationAllowed;
	}
	
	
}
