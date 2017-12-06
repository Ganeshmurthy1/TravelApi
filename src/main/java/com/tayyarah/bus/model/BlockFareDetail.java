package com.tayyarah.bus.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tayyarah.common.gstconfig.model.BusGstTax;

public class BlockFareDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal basePrice;
	private BigDecimal taxes;
	private BigDecimal apiPrice;
	private BigDecimal priceWithOutMarkup;
	private BigDecimal bookingPrice;
	private BigDecimal totalPayableAmount;
	private BusServiceTax busServiceTax;
	private BusGstTax busGstTax;
	
	public BusGstTax getBusGstTax() {
		return busGstTax;
	}
	public void setBusGstTax(BusGstTax busGstTax) {
		this.busGstTax = busGstTax;
	}
	public BigDecimal getApiPrice() {
		return apiPrice;
	}
	public BigDecimal getPriceWithOutMarkup() {
		return priceWithOutMarkup;
	}
	public BigDecimal getBookingPrice() {
		return bookingPrice;
	}
	public BigDecimal getTotalPayableAmount() {
		return totalPayableAmount;
	}
	public BusServiceTax getBusServiceTax() {
		return busServiceTax;
	}
	public void setApiPrice(BigDecimal apiPrice) {
		this.apiPrice = apiPrice;
	}
	public void setPriceWithOutMarkup(BigDecimal priceWithOutMarkup) {
		this.priceWithOutMarkup = priceWithOutMarkup;
	}
	public void setBookingPrice(BigDecimal bookingPrice) {
		this.bookingPrice = bookingPrice;
	}
	public void setTotalPayableAmount(BigDecimal totalPayableAmount) {
		this.totalPayableAmount = totalPayableAmount;
	}
	public void setBusServiceTax(BusServiceTax busServiceTax) {
		this.busServiceTax = busServiceTax;
	}
	public BigDecimal getBasePrice() {
		return basePrice;
	}
	public BigDecimal getTaxes() {
		return taxes;
	}
	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}
	public void setTaxes(BigDecimal taxes) {
		this.taxes = taxes;
	}
	
}
