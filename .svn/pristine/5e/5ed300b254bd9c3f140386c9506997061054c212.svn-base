package com.tayyarah.hotel.reposit.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hotelimages database table.
 * 
 */
@Entity
@Table(name="hotelimages")
public class Hotelimage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private int id;
	private String content_Title;
	private String imageUrl;
	private int vendorID;	

	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent_Title() {
		return this.content_Title;
	}
	public void setContent_Title(String content_Title) {
		this.content_Title = content_Title;
	}
	public String getImageUrl() {
		return this.imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getVendorID() {
		return this.vendorID;
	}
	public void setVendorID(int vendorID) {
		this.vendorID = vendorID;
	}
}