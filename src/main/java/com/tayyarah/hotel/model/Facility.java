package com.tayyarah.hotel.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




/**
 * The persistent class for the facilities database table.
 * 
 */
@Entity
@Table(name="facilities")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/*@NamedQuery(name="Facility.findAll", query="SELECT f FROM Facility f")*/
public class Facility implements Serializable {
	
	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Facility [hoteId=" + hoteId + ", apiVendorId=" + apiVendorId + ", Description=" + Description
				+ ", AmenityType=" + AmenityType + ", id=" + id + "]";
	}

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

	@JsonIgnore
	@Column(name="hotel_id")
	private java.lang.Integer hoteId;
	
	@JsonIgnore
	@Column(name="api_vendor_id")
	private String apiVendorId;	
	
	@Column(name="Description")
	private String Description;	

	@JsonIgnore
	@Column(name="AmenityType")
	private String AmenityType;	
	
	@JsonIgnore
	@Id
	@Column(name="id")
	private Integer id;

	/*//bi-directional many-to-one association to Hoteloverview
	@ManyToOne
	@JoinColumn(name="VendorID")
	@JsonIgnore
	private HotelOverview hoteloverview;*/

	public Facility() {
	}
	
	public String getAmenityType() {
		return this.AmenityType;
	}

	public void setAmenityType(String amenityType) {
		this.AmenityType = amenityType;
	}

	public String getDescription() {
		return this.Description;
	}

	public void setDescription(String description) {
		this.Description = description;
	}

	

	/*	public HotelOverview getHoteloverview() {
		return this.hoteloverview;
	}

	public void setHoteloverview(HotelOverview hoteloverview) {
		this.hoteloverview = hoteloverview;
	}*/

}