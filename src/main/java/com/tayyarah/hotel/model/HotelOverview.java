package com.tayyarah.hotel.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;


/**
 * The persistent class for the hoteloverview database table.
 * 
 */
/*@Entity
@NamedQuery(name="Hoteloverview.findAll", query="SELECT h FROM Hoteloverview h")*/


@Entity
@Table(name = "hoteloverview")
//@NamedQuery(name="City.findAll", query="SELECT c FROM City c")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelOverview implements Serializable {
	

	@Override
	public String toString() {
		return "HotelOverview [hoteId=" + hoteId + ", apiVendorId=" + apiVendorId + ", apiProviderId=" + apiProviderId
				+ ", address=" + address + ", address1=" + address1 + ", address2=" + address2 + ", area=" + area
				+ ", area_Seo_Id=" + area_Seo_Id + ", city=" + city + ", city_Zone=" + city_Zone + ", country="
				+ country + ", defaultCheckInTime=" + defaultCheckInTime + ", defaultCheckOutTime="
				+ defaultCheckOutTime + ", hotel_Star=" + hotel_Star + ", hotelClass=" + hotelClass + ", hotelGroupID="
				+ hotelGroupID + ", hotelGroupName=" + hotelGroupName + ", hotelOverview=" + hotelOverview
				+ ", hotelSearchKey=" + hotelSearchKey + ", id=" + id + ", imagePath=" + imagePath + ", latitude="
				+ latitude + ", location=" + location + ", longitude=" + longitude + ", reviewCount=" + reviewCount
				+ ", reviewRating=" + reviewRating + ", vendorName=" + vendorName + ", weekdayRank=" + weekdayRank
				+ ", weekendRank=" + weekendRank + "]";
	}



	public Integer getHoteId() {
		return hoteId;
	}



	public void setHoteId(Integer hoteId) {
		this.hoteId = hoteId;
	}



	public String getApiVendorId() {
		return apiVendorId;
	}



	public void setApiVendorId(String apiVendorId) {
		this.apiVendorId = apiVendorId;
	}



	public String getApiProviderId() {
		return apiProviderId;
	}



	public void setApiProviderId(String apiProviderId) {
		this.apiProviderId = apiProviderId;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="hotel_id")
	private java.lang.Integer hoteId;
	
	@Column(name="api_vendor_id")
	private String apiVendorId;

	@Column(name="api_provider_id")
	private String apiProviderId;
	
	private String address;
	@Column(name="Address1")
	private String address1;
	@Column(name="Address2")
	private String address2;
	@Column(name="Area")
	private String area;
	@Column(name="Area_Seo_Id")
	private String area_Seo_Id;
	@Column(name="City")
	private String city;
	@Column(name="City_Zone")
	private String city_Zone;
	@Column(name="Country")
	private String country;
	@Column(name="DefaultCheckInTime")
	private Time defaultCheckInTime;
	@Column(name="DefaultCheckOutTime")
	private Time defaultCheckOutTime;
	@Column(name="Hotel_Star")
	private java.lang.Integer hotel_Star;
	@Column(name="HotelClass")
	private String hotelClass;
	@Column(name="HotelGroupID")
	private String hotelGroupID;
	@Column(name="HotelGroupName")
	private String hotelGroupName;
	@Column(name="HotelOverview")
	private String hotelOverview;
	@Column(name="HotelSearchKey")
	private String hotelSearchKey;
	@Column(name="id")
	private java.lang.Integer id;
	@Column(name="ImagePath")
	private String imagePath;
	@Column(name="Latitude")
	private BigDecimal latitude;
	@Column(name="Location")
	private String location;
	@Column(name="Longitude")
	private BigDecimal longitude;
	@Column(name="ReviewCount")
	private String reviewCount;
	@Column(name="ReviewRating")
	private String reviewRating;
	@Column(name="VendorName")
	private String vendorName;

	@Column(name="WEEKDAY_RANK")
	private java.lang.Integer weekdayRank;

	@Column(name="WEEKEND_RANK")
	private java.lang.Integer weekendRank;

	/*//bi-directional many-to-one association to Facility
	@OneToMany(mappedBy="hoteloverview")
	private List<Facility> facilities;
	 */
	//bi-directional many-to-one association to Hotelimage
	/*@OneToMany(mappedBy="hoteloverview")
	private List<Hotelimage> hotelimages;*/

	/*//bi-directional many-to-one association to Hotelreview
	@OneToMany(mappedBy="hoteloverview")
	private List<Hotelreview> hotelreviews;

	//bi-directional many-to-one association to Hotelroomdescription
	@OneToMany(mappedBy="hoteloverview")
	private List<Hotelroomdescription> hotelroomdescriptions;

	//bi-directional one-to-one association to Hotelsecondaryarea
	@OneToOne(mappedBy="hoteloverview")
	private Hotelsecondaryarea hotelsecondaryarea;

	//bi-directional one-to-one association to Hotelthemeandcategory
	@OneToOne(mappedBy="hoteloverview")
	private Hotelthemeandcategory hotelthemeandcategory;
*/
	public HotelOverview() {
	}

	

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArea_Seo_Id() {
		return this.area_Seo_Id;
	}

	public void setArea_Seo_Id(String area_Seo_Id) {
		this.area_Seo_Id = area_Seo_Id;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity_Zone() {
		return this.city_Zone;
	}

	public void setCity_Zone(String city_Zone) {
		this.city_Zone = city_Zone;
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

	public String getHotelClass() {
		return this.hotelClass;
	}

	public void setHotelClass(String hotelClass) {
		this.hotelClass = hotelClass;
	}

	public String getHotelGroupID() {
		return this.hotelGroupID;
	}

	public void setHotelGroupID(String hotelGroupID) {
		this.hotelGroupID = hotelGroupID;
	}

	public String getHotelGroupName() {
		return this.hotelGroupName;
	}

	public void setHotelGroupName(String hotelGroupName) {
		this.hotelGroupName = hotelGroupName;
	}

	public String getHotelOverview() {
		return this.hotelOverview;
	}

	public void setHotelOverview(String hotelOverview) {
		this.hotelOverview = hotelOverview;
	}

	public String getHotelSearchKey() {
		return this.hotelSearchKey;
	}

	public void setHotelSearchKey(String hotelSearchKey) {
		this.hotelSearchKey = hotelSearchKey;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
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

	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Integer getWeekdayRank() {
		return this.weekdayRank;
	}

	public void setWeekdayRank(Integer weekdayRank) {
		this.weekdayRank = weekdayRank;
	}

	public Integer getWeekendRank() {
		return this.weekendRank;
	}

	public void setWeekendRank(Integer weekendRank) {
		this.weekendRank = weekendRank;
	}

/*	public List<Facility> getFacilities() {
		return this.facilities;
	}

	public void setFacilities(List<Facility> facilities) {
		this.facilities = facilities;
	}
	
	public Facility addFacility(Facility facility) {
		getFacilities().add(facility);
		facility.setHoteloverview(this);

		return facility;
	}

	public Facility removeFacility(Facility facility) {
		getFacilities().remove(facility);
		facility.setHoteloverview(null);

		return facility;
	}*/

	/*public List<Hotelimage> getHotelimages() {
		return this.hotelimages;
	}

	public void setHotelimages(List<Hotelimage> hotelimages) {
		this.hotelimages = hotelimages;
	}
*/
	/*public Hotelimage addHotelimage(Hotelimage hotelimage) {
		getHotelimages().add(hotelimage);
		hotelimage.setHoteloverview(this);

		return hotelimage;
	}

	public Hotelimage removeHotelimage(Hotelimage hotelimage) {
		getHotelimages().remove(hotelimage);
		hotelimage.setHoteloverview(null);

		return hotelimage;
	}
*/
	/*public List<Hotelreview> getHotelreviews() {
		return this.hotelreviews;
	}

	public void setHotelreviews(List<Hotelreview> hotelreviews) {
		this.hotelreviews = hotelreviews;
	}*/
/*
	public Hotelreview addHotelreview(Hotelreview hotelreview) {
		getHotelreviews().add(hotelreview);
		hotelreview.setHoteloverview(this);

		return hotelreview;
	}

	public Hotelreview removeHotelreview(Hotelreview hotelreview) {
		getHotelreviews().remove(hotelreview);
		hotelreview.setHoteloverview(null);

		return hotelreview;
	}*/

	/*public List<Hotelroomdescription> getHotelroomdescriptions() {
		return this.hotelroomdescriptions;
	}

	public void setHotelroomdescriptions(List<Hotelroomdescription> hotelroomdescriptions) {
		this.hotelroomdescriptions = hotelroomdescriptions;
	}*/

	/*public Hotelroomdescription addHotelroomdescription(Hotelroomdescription hotelroomdescription) {
		getHotelroomdescriptions().add(hotelroomdescription);
		hotelroomdescription.setHoteloverview(this);

		return hotelroomdescription;
	}

	public Hotelroomdescription removeHotelroomdescription(Hotelroomdescription hotelroomdescription) {
		getHotelroomdescriptions().remove(hotelroomdescription);
		hotelroomdescription.setHoteloverview(null);

		return hotelroomdescription;
	}*/

	/*public Hotelsecondaryarea getHotelsecondaryarea() {
		return this.hotelsecondaryarea;
	}

	public void setHotelsecondaryarea(Hotelsecondaryarea hotelsecondaryarea) {
		this.hotelsecondaryarea = hotelsecondaryarea;
	}

	public Hotelthemeandcategory getHotelthemeandcategory() {
		return this.hotelthemeandcategory;
	}

	public void setHotelthemeandcategory(Hotelthemeandcategory hotelthemeandcategory) {
		this.hotelthemeandcategory = hotelthemeandcategory;
	}*/

	

	
	
}