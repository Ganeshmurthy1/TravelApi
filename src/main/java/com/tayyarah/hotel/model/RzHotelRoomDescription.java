package com.tayyarah.hotel.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The persistent class for the rshotelroomdescription database table.
 * 
 */
@Entity
@Table(name="Rshotelroomdescription")
             
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})*/
//@NamedQuery(name="Rshotelroomdescription.findAll", query="SELECT r FROM Rshotelroomdescription r")

public class RzHotelRoomDescription implements Serializable {
	private static final long serialVersionUID = 1L;
	 @JsonInclude(Include.NON_EMPTY)
	private String imagePath;

	private String max_Adult_Occupancy;

	private String max_Child_Occupancy;

	private String max_Guest_Occupancy;

	private String max_Infant_Occupancy;

	private String roomcode;

	private String roomDescription;
	@Id
	private int roomid;

	private String roomType;
	
	private Integer  roomTypeID;

	private short totalrooms;
	
	private String vendorID;

	public RzHotelRoomDescription() {
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getMax_Adult_Occupancy() {
		return this.max_Adult_Occupancy;
	}

	public void setMax_Adult_Occupancy(String max_Adult_Occupancy) {
		this.max_Adult_Occupancy = max_Adult_Occupancy;
	}

	public String getMax_Child_Occupancy() {
		return this.max_Child_Occupancy;
	}

	public void setMax_Child_Occupancy(String max_Child_Occupancy) {
		this.max_Child_Occupancy = max_Child_Occupancy;
	}

	public String getMax_Guest_Occupancy() {
		return this.max_Guest_Occupancy;
	}

	public void setMax_Guest_Occupancy(String max_Guest_Occupancy) {
		this.max_Guest_Occupancy = max_Guest_Occupancy;
	}

	public String getMax_Infant_Occupancy() {
		return this.max_Infant_Occupancy;
	}

	public void setMax_Infant_Occupancy(String max_Infant_Occupancy) {
		this.max_Infant_Occupancy = max_Infant_Occupancy;
	}

	public String getRoomcode() {
		return this.roomcode;
	}

	public void setRoomcode(String roomcode) {
		this.roomcode = roomcode;
	}

	public String getRoomDescription() {
		return this.roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public int getRoomid() {
		return this.roomid;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

	public String getRoomType() {
		return this.roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public Integer  getRoomTypeID() {
		return this.roomTypeID;
	}

	public void setRoomTypeID(Integer  roomTypeID) {
		this.roomTypeID = roomTypeID;
	}

	public short getTotalrooms() {
		return this.totalrooms;
	}

	public void setTotalrooms(short totalrooms) {
		this.totalrooms = totalrooms;
	}

	public String getVendorID() {
		return this.vendorID;
	}

	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}

	@Override
	public String toString() {
		return "RzHotelRoomDescription [imagePath=" + imagePath
				+ ", max_Adult_Occupancy=" + max_Adult_Occupancy
				+ ", max_Child_Occupancy=" + max_Child_Occupancy
				+ ", max_Guest_Occupancy=" + max_Guest_Occupancy
				+ ", max_Infant_Occupancy=" + max_Infant_Occupancy
				+ ", roomcode=" + roomcode + ", roomDescription="
				+ roomDescription + ", roomid=" + roomid + ", roomType="
				+ roomType + ", roomTypeID=" + roomTypeID + ", totalrooms="
				+ totalrooms + ", vendorID=" + vendorID + "]";
	}

}