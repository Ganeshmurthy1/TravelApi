package com.tayyarah.bus.quotation.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tayyarah.common.util.Timestampable;


 

@Entity
@Table(name = "bus_travel_request_quotation")
public class BusTravelRequestQuotation extends Timestampable {

	private static final long serialVersionUID = 1L;

	@Transient
	private String travelDateTemp;

	@Column(name = "bus_type")
	private String busType;
	
	private String remarks;

	@Column(name = "location")
	private String location;

	@Column(name = "pickup")
	private String pickUp;

	@Column(name = "dropoff")
	private String dropOff;

	@Column(name = "currency")
	private String currency;
	
	@Column(name = "total_amount",columnDefinition="decimal(20,10) default '0.0'")
	private BigDecimal totalAmount;

	@ManyToOne (cascade = CascadeType.ALL) 
	@JoinColumn(name = "bus_travel_req_id", referencedColumnName = "id")
	private BusTravelRequest busTravelRequest;
	
	@Column(name = "order_row_Id")
	private Long  orderRowId;
	@Column(name = "is_booked",columnDefinition = "BOOLEAN DEFAULT false")//new column added by raham
	private boolean  isBooked;
	@Column(name = "is_hide" ,columnDefinition = "BOOLEAN DEFAULT false")//new column added by raham
	private boolean  isHide;
	@Column(name = "status_id")
	private int statusId;

	public String getTravelDateTemp() {
		return travelDateTemp;
	}

	public void setTravelDateTemp(String travelDateTemp) {
		this.travelDateTemp = travelDateTemp;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPickUp() {
		return pickUp;
	}

	public void setPickUp(String pickUp) {
		this.pickUp = pickUp;
	}

	public String getDropOff() {
		return dropOff;
	}

	public void setDropOff(String dropOff) {
		this.dropOff = dropOff;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BusTravelRequest getBusTravelRequest() {
		return busTravelRequest;
	}

	public void setBusTravelRequest(BusTravelRequest busTravelRequest) {
		this.busTravelRequest = busTravelRequest;
	}

	public Long getOrderRowId() {
		return orderRowId;
	}

	public void setOrderRowId(Long orderRowId) {
		this.orderRowId = orderRowId;
	}

	public boolean isBooked() {
		return isBooked;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	public boolean isHide() {
		return isHide;
	}

	public void setHide(boolean isHide) {
		this.isHide = isHide;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	
}
