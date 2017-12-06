package com.tayyarah.hotel.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the hotelimages database table.
 * 
 */
@Entity
@Table(name="hotelimages")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/*@NamedQuery(name="Hotelimage.findAll", query="SELECT h FROM Hotelimage h")*/
public class Hotelimage implements Serializable {
	
	public Integer getId() {
		return id;
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

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}

	private static final long serialVersionUID = 1L;

	
	@Column(name="Content_Title")
	private String Content_Title;

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
	
	@JsonIgnore
	@Column(name="roomid")
	private String roomid;	
	
	@Column(name="ImageUrl")
	private String ImageUrl;
	

	public Hotelimage() {
	}

	

	public String getContent_Title() {
		return this.Content_Title;
	}

	public void setContent_Title(String content_Title) {
		this.Content_Title = content_Title;
	}

	
	/*public HotelOverview getHoteloverview() {
		return this.hoteloverview;
	}

	public void setHoteloverview(HotelOverview hoteloverview) {
		this.hoteloverview = hoteloverview;
	}*/

}