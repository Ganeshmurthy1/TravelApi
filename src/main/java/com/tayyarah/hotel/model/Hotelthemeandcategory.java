package com.tayyarah.hotel.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the hotelthemeandcategory database table.
 * 
 */
@Entity
@Table(name="hotelthemeandcategory")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@NamedQuery(name="Hotelthemeandcategory.findAll", query="SELECT h FROM Hotelthemeandcategory h")
public class Hotelthemeandcategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String vendorId;

	private String categoryList;

	private String themeList;

	private String vendorName;

	//bi-directional one-to-one association to Hoteloverview
	@OneToOne
	@JoinColumn(name="VendorId")
	private HotelOverview hoteloverview;

	public Hotelthemeandcategory() {
	}

	public String getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getCategoryList() {
		return this.categoryList;
	}

	public void setCategoryList(String categoryList) {
		this.categoryList = categoryList;
	}

	public String getThemeList() {
		return this.themeList;
	}

	public void setThemeList(String themeList) {
		this.themeList = themeList;
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