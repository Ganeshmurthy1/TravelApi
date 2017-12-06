package com.tayyarah.hotel.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tayyarah.hotel.model.HotelOverview;


/**
 * The persistent class for the hotelsecondaryareas database table.
 * 
 */
@Entity
@Table(name="hotelsecondaryareas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/*@NamedQuery(name="Hotelsecondaryarea.findAll", query="SELECT h FROM Hotelsecondaryarea h")*/
public class Hotelsecondaryarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String vendorId;

	private String secondaryAreaIds;

	private String secondaryAreaName;

	private String vendorName;

	//bi-directional one-to-one association to Hoteloverview
	@OneToOne
	@JoinColumn(name="VendorId")
	private HotelOverview hoteloverview;

	public Hotelsecondaryarea() {
	}

	public String getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getSecondaryAreaIds() {
		return this.secondaryAreaIds;
	}

	public void setSecondaryAreaIds(String secondaryAreaIds) {
		this.secondaryAreaIds = secondaryAreaIds;
	}

	public String getSecondaryAreaName() {
		return this.secondaryAreaName;
	}

	public void setSecondaryAreaName(String secondaryAreaName) {
		this.secondaryAreaName = secondaryAreaName;
	}

	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public HotelOverview getHoteloverview() {
		return this.hoteloverview;
	}

	public void setHoteloverview(HotelOverview hoteloverview) {
		this.hoteloverview = hoteloverview;
	}

}