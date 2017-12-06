package com.tayyarah.hotel.model;

import java.io.Serializable;

public class RoomBookingKeyMap implements Serializable {

	@Override
	public String toString() {
		return "RoomBookingKeyMap [ratePlanCode=" + ratePlanCode + ", roomTypeCode=" + roomTypeCode + ", bookingKey="
				+ bookingKey + "]";
	}
	public String getRatePlanCode() {
		return ratePlanCode;
	}
	public void setRatePlanCode(String ratePlanCode) {
		this.ratePlanCode = ratePlanCode;
	}
	public String getRoomTypeCode() {
		return roomTypeCode;
	}
	public void setRoomTypeCode(String roomTypeCode) {
		this.roomTypeCode = roomTypeCode;
	}
	public String getBookingKey() {
		return bookingKey;
	}
	public void setBookingKey(String bookingKey) {
		this.bookingKey = bookingKey;
	}
	public RoomBookingKeyMap() {
		// TODO Auto-generated constructor stub
	}
	protected String ratePlanCode;
	protected String roomTypeCode;
	protected String bookingKey;
	
}
