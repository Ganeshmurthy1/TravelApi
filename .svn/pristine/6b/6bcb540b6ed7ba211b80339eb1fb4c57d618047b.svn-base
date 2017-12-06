package com.tayyarah.hotel.model;
import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;


/**
 * The persistent class for the hotelpoi database table.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
/*@NamedQuery(name="Hotelpoi.findAll", query="SELECT h FROM Hotelpoi h")*/
@Table(name = "hotelpoi")

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HotelPoi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int poi_Id;

	private int city_Id;

	private String city_Name;

	private BigDecimal latitude;

	private BigDecimal longitude;

	private String poi_Name;

	private String poi_Seo_Id;

	private String seo_City_Name;

	public HotelPoi() {
	}

	public int getPoi_Id() {
		return this.poi_Id;
	}

	public void setPoi_Id(int poi_Id) {
		this.poi_Id = poi_Id;
	}

	public int getCity_Id() {
		return this.city_Id;
	}

	public void setCity_Id(int city_Id) {
		this.city_Id = city_Id;
	}

	public String getCity_Name() {
		return this.city_Name;
	}

	public void setCity_Name(String city_Name) {
		this.city_Name = city_Name;
	}

	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getPoi_Name() {
		return this.poi_Name;
	}

	public void setPoi_Name(String poi_Name) {
		this.poi_Name = poi_Name;
	}

	public String getPoi_Seo_Id() {
		return this.poi_Seo_Id;
	}

	public void setPoi_Seo_Id(String poi_Seo_Id) {
		this.poi_Seo_Id = poi_Seo_Id;
	}

	public String getSeo_City_Name() {
		return this.seo_City_Name;
	}

	public void setSeo_City_Name(String seo_City_Name) {
		this.seo_City_Name = seo_City_Name;
	}

}