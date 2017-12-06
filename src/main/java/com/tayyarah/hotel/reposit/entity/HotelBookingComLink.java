package com.tayyarah.hotel.reposit.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hotelbookinglink")
public class HotelBookingComLink implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	private int id;
	@Column(name="VendorName")
	private String vendorName;	
	@Column(name="Address")
	private String address;	
	@Column(name="booking_link")
	private String bookingLink;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBookingLink() {
		return bookingLink;
	}
	public void setBookingLink(String bookingLink) {
		this.bookingLink = bookingLink;
	}
}