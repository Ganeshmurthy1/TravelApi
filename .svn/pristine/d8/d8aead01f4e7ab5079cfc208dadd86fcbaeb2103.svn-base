package com.tayyarah.hotel.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the hotelroomdescription database table.
 * 
 */
@Entity
@Table(name = "hotelroomdescription")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/*@NamedQuery(name="Hotelroomdescription.findAll", query="SELECT h FROM Hotelroomdescription h")*/
public class Hotelroomdescription implements Serializable {
	public java.lang.Integer getHoteId() {
		return hoteId;
	}

	public void setHoteId(java.lang.Integer hoteId) {
		this.hoteId = hoteId;
	}

	public String getApiVendorId() {
		return apiVendorId;
	}

	public void setApiVendorId(String apiVendorId) {
		this.apiVendorId = apiVendorId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private static final long serialVersionUID = 1L;

	
	@Column(name="ImagePath")
	private String imagePath;
	@Column(name="Max_Adult_Occupancy")
	private String max_Adult_Occupancy;
	@Column(name="Max_Child_Occupancy")
	private String max_Child_Occupancy;
	@Column(name="Max_Guest_Occupancy")
	private String max_Guest_Occupancy;
	@Column(name="Max_Infant_Occupancy")
	private String max_Infant_Occupancy;
	@Column(name="RoomDescription")
	private String roomDescription;
	@Column(name="RoomType")
	private String roomType;
	@JsonIgnore
	@Column(name="RoomTypeID")
	private Integer roomTypeID;
	
	@JsonIgnore
	@Id
	@Column(name="id")
	private Integer id;
	
	@JsonIgnore
	@Column(name="hotel_id")
	private java.lang.Integer hoteId;
	
	@JsonIgnore
	@Column(name="api_vendor_id")
	private String apiVendorId;	
	
	private String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	/*
	//bi-directional many-to-one association to Hoteloverview
	@ManyToOne
	@JoinColumn(name="VendorID")
	private HotelOverview hoteloverview;

	public Hotelroomdescription() {
	}
*/
	

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

	public String getRoomDescription() {
		return this.roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public String getRoomType() {
		return this.roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public Integer getRoomTypeID() {
		return this.roomTypeID;
	}

	public void setRoomTypeID(Integer roomTypeID) {
		this.roomTypeID = roomTypeID;
	}
/*
	public HotelOverview getHoteloverview() {
		return this.hoteloverview;
	}

	public void setHoteloverview(HotelOverview hoteloverview) {
		this.hoteloverview = hoteloverview;
	}*/

}