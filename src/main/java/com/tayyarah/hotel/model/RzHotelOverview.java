package com.tayyarah.hotel.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.sql.Time;


/**
 * The persistent class for the rshoteloverview database table.
 * 
 */
@Entity
@Table(name="Rshoteloverview")
@JsonInclude(JsonInclude.Include.NON_NULL)
//@NamedQuery(name="Rshoteloverview.findAll", query="SELECT r FROM Rshoteloverview r")
public class RzHotelOverview implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RzHoteloverviewPk id;

	private String address1;

	private String address2;

	private String city;

	private String country;

	private Time defaultCheckInTime;

	private Time defaultCheckOutTime;

	private Integer hotel_Star;

	private Byte hotelClass;

	private String hotelOverview;

	private String imagePath;

	private BigDecimal latitude;

	private String location;

	private BigDecimal longitude;

	private String reviewCount;

	private String reviewRating;

	private String state;

	private String vendorName;

	public RzHotelOverview() {
	}

	public RzHoteloverviewPk getId() {
		return this.id;
	}

	public void setId(RzHoteloverviewPk id) {
		this.id = id;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Time getDefaultCheckInTime() {
		return this.defaultCheckInTime;
	}

	public void setDefaultCheckInTime(Time defaultCheckInTime) {
		this.defaultCheckInTime = defaultCheckInTime;
	}

	public Time getDefaultCheckOutTime() {
		return this.defaultCheckOutTime;
	}

	public void setDefaultCheckOutTime(Time defaultCheckOutTime) {
		this.defaultCheckOutTime = defaultCheckOutTime;
	}

	public Integer getHotel_Star() {
		return this.hotel_Star;
	}

	public void setHotel_Star(Integer hotel_Star) {
		this.hotel_Star = hotel_Star;
	}

	public Byte getHotelClass() {
		return this.hotelClass;
	}

	public void setHotelClass(Byte hotelClass) {
		this.hotelClass = hotelClass;
	}

	public String getHotelOverview() {
		return this.hotelOverview;
	}

	public void setHotelOverview(String hotelOverview) {
		this.hotelOverview = hotelOverview;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getReviewCount() {
		return this.reviewCount;
	}

	public void setReviewCount(String reviewCount) {
		this.reviewCount = reviewCount;
	}

	public String getReviewRating() {
		return this.reviewRating;
	}

	public void setReviewRating(String reviewRating) {
		this.reviewRating = reviewRating;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Override
	public String toString() {
		return "RzHotelOverview [id=" + id + ", address1=" + address1
				+ ", address2=" + address2 + ", city=" + city + ", country="
				+ country + ", defaultCheckInTime=" + defaultCheckInTime
				+ ", defaultCheckOutTime=" + defaultCheckOutTime
				+ ", hotel_Star=" + hotel_Star + ", hotelClass=" + hotelClass
				+ ", hotelOverview=" + hotelOverview + ", imagePath="
				+ imagePath + ", latitude=" + latitude + ", location="
				+ location + ", longitude=" + longitude + ", reviewCount="
				+ reviewCount + ", reviewRating=" + reviewRating + ", state="
				+ state + ", vendorName=" + vendorName + "]";
	}

}