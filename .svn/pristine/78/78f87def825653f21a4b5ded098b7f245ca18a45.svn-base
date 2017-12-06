package com.tayyarah.hotel.model;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIRoomDetail implements Serializable{	
	/**
	 * 
	*/
	private static final long serialVersionUID = 3075867308800524579L;
	public APIRoomDetail() {
		super();
		this.hotelSearchCommand = null;
		this.rs = null;
		this.status = null;
		this.searchKey = new BigInteger("-1");
		this.transactionKey = new BigInteger("-1");
		this.roomStayMap = new HashMap<Integer, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>();
	}

	public APIRoomDetail(HotelSearchCommand hotelSearchCommand, RoomStay rs, APIStatus status, BigInteger searchKey,
			BigInteger transactionKey) {
		super();
		this.hotelSearchCommand = hotelSearchCommand;
		this.rs = rs;
		this.status = status;
		this.searchKey = searchKey;
		this.transactionKey = transactionKey;
		this.roomStayMap = new HashMap<Integer, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>();
	}
public APIRoomDetail(HotelSearchCommand hotelSearchCommand, RoomStay rs, APIStatus status, String gstNumber,
		 BigInteger searchKey, BigInteger transactionKey) {
	super();
	this.hotelSearchCommand = hotelSearchCommand;
	this.rs = rs;
	this.status = status;
	this.gstNumber = gstNumber;
	this.roomStayMap = new HashMap<Integer, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>();
	this.searchKey = searchKey;
	this.transactionKey = transactionKey;
}
	
	public APIRoomDetail(APIStatus status, BigInteger searchKey,
			BigInteger transactionKey) {
		super();
		this.hotelSearchCommand = null;
		this.rs = null;
		this.status = status;
		this.searchKey = new BigInteger("-1");
		this.transactionKey = new BigInteger("-1");
		this.roomStayMap = new HashMap<Integer, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>();
	}
	

	public APIRoomDetail(APIStatus status) {
		this.hotelSearchCommand = null;
		this.rs = null;
		this.status = status;
		this.searchKey = new BigInteger("-1");
		this.transactionKey = new BigInteger("-1");
		this.roomStayMap = new HashMap<Integer, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>();
		
	}
	
	public HotelSearchCommand getHotelSearchCommand() {
		return hotelSearchCommand;
	}
	public void setHotelSearchCommand(HotelSearchCommand hotelSearchCommand) {
		this.hotelSearchCommand = hotelSearchCommand;
	}
	public com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay getRs() {
		return rs;
	}
	public void setRs(com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs) {
		this.rs = rs;
	}
	public APIStatus getStatus() {
		return status;
	}
	public void setStatus(APIStatus status) {
		this.status = status;
	}
	private HotelSearchCommand hotelSearchCommand = null;
	private com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs;
	private APIStatus status;
	private String gstNumber;
	/*
	private HashMap<Integer, List<com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>> roomStayMap;	
	public HashMap<Integer, List<com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>> getRoomStayMap() {
		return roomStayMap;
	}
	public void setRoomStayMap(
			HashMap<Integer, List<com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>> roomStayMap) {
		this.roomStayMap = roomStayMap;
	}*/
	
	private HashMap<Integer, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay> roomStayMap;	
	public HashMap<Integer, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay> getRoomStayMap() {
		return roomStayMap;
	}
	public void setRoomStayMap(
			HashMap<Integer, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay> roomStayMap) {
		this.roomStayMap = roomStayMap;
	}
	private BigInteger searchKey;
	public BigInteger getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(BigInteger searchKey) {
		this.searchKey = searchKey;
	}
	public BigInteger getTransactionKey() {
		return transactionKey;
	}
	public void setTransactionKey(BigInteger transactionKey) {
		this.transactionKey = transactionKey;
	}
	private BigInteger transactionKey;
	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}
}
