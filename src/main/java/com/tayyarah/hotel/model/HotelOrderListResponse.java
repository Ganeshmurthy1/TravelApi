/**
 * 
 */
package com.tayyarah.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

/**
 * @author info : Manish Samrat
 * @createdAt : 01/06/2017
 * @version : 1.0
 */

public class HotelOrderListResponse implements Serializable {

	Logger logger = Logger.getLogger(HotelOrderListResponse.class);
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String location;
	private String HotelName;
	private String statusAction;
	private String paymentStatus;
	private String orderId;
	private String currency;
	private BigDecimal totalBookingAmount;
	private String bookingDate;

	
	public String getOrderId() {
		return orderId;
	}
	public String getCurrency() {
		return currency;
	}
	public BigDecimal getTotalBookingAmount() {
		return totalBookingAmount;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public void setTotalBookingAmount(BigDecimal totalBookingAmount) {
		this.totalBookingAmount = totalBookingAmount;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getHotelName() {
		return HotelName;
	}
	public void setHotelName(String hotelName) {
		HotelName = hotelName;
	}
	public String getStatusAction() {
		return statusAction;
	}
	public void setStatusAction(String statusAction) {
		this.statusAction = statusAction;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
}
