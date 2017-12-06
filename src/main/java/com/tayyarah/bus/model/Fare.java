package com.tayyarah.bus.model;

import java.io.Serializable;

import com.tayyarah.common.gstconfig.model.BusGstTax;


public class Fare implements Serializable{	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String apiPrice;
	private String priceWithOutMarkup;
	private String bookingPrice;
	private String discount;
	private String payableAmount;
	private BusServiceTax busServiceTax;
	private BusGstTax busGstTax;
	
	public BusGstTax getBusGstTax() {
		return busGstTax;
	}
	public void setBusGstTax(BusGstTax busGstTax) {
		this.busGstTax = busGstTax;
	}
	public String getApiPrice() {
		return apiPrice;
	}
	public String getPriceWithOutMarkup() {
		return priceWithOutMarkup;
	}
	public String getBookingPrice() {
		return bookingPrice;
	}
	public String getDiscount() {
		return discount;
	}
	public String getPayableAmount() {
		return payableAmount;
	}
	public BusServiceTax getBusServiceTax() {
		return busServiceTax;
	}
	public void setApiPrice(String apiPrice) {
		this.apiPrice = apiPrice;
	}
	public void setPriceWithOutMarkup(String priceWithOutMarkup) {
		this.priceWithOutMarkup = priceWithOutMarkup;
	}
	public void setBookingPrice(String bookingPrice) {
		this.bookingPrice = bookingPrice;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public void setPayableAmount(String payableAmount) {
		this.payableAmount = payableAmount;
	}
	public void setBusServiceTax(BusServiceTax busServiceTax) {
		this.busServiceTax = busServiceTax;
	}
	
}
