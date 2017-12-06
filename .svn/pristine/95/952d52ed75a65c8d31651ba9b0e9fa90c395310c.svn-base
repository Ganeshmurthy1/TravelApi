package com.tayyarah.hotel.reposit.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the facilities database table.
 * 
 */
@Entity
@Table(name="facilities")
public class Facility implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private int id;
	private String amenity_id;
	private String amenityType;
	private String description;
	private int vendorID;
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAmenity_id() {
		return this.amenity_id;
	}
	public void setAmenity_id(String amenity_id) {
		this.amenity_id = amenity_id;
	}
	public String getAmenityType() {
		return this.amenityType;
	}
	public void setAmenityType(String amenityType) {
		this.amenityType = amenityType;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getVendorID() {
		return this.vendorID;
	}
	public void setVendorID(int vendorID) {
		this.vendorID = vendorID;
	}
}