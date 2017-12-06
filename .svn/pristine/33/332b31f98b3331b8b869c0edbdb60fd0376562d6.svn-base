package com.tayyarah.quotation.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.tayyarah.common.util.DateConversion;
import com.tayyarah.common.util.Timestampable;


@Entity
@Table(name = "insurance_travel_request_quotation")
public class InsuranceTravelRequestQuotation extends Timestampable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	private String onwardTravelDateTemp;
	
	@Transient
	private String returnTravelDatetemp;
	
	
	@Column(name="onward_travelDate")
	@Temporal(TemporalType.DATE)
	private Date onwardTravelDate;
	
	
	@Column(name="return_travelDate")
	@Temporal(TemporalType.DATE)
	private Date ReturnTravelDate;
	
	@Column(name = "passport_number")
	private String passportNumber;
	
	
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "remarks")
	private String remarks;
	
	
	
	
	@ManyToOne (cascade = CascadeType.ALL) 
	@JoinColumn(name = "insurance_travel_req_id", referencedColumnName = "id")
	private InsuranceTravelRequest insuranceTravelRequest;
	
	@Column(name = "order_row_Id")
	private Long  orderRowId;
	@Column(name = "is_booked",columnDefinition = "BOOLEAN DEFAULT false")//new column added by raham
	private boolean  isBooked;
	@Column(name = "is_hide" ,columnDefinition = "BOOLEAN DEFAULT false")//new column added by raham
	private boolean  isHide;
	@Column(name = "status_id")
	private int statusId;


	

	

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	

	
	public InsuranceTravelRequest getInsuranceTravelRequest() {
		return insuranceTravelRequest;
	}

	public void setInsuranceTravelRequest(InsuranceTravelRequest insuranceTravelRequest) {
		this.insuranceTravelRequest = insuranceTravelRequest;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOnwardTravelDateTemp() {
		return onwardTravelDateTemp;
	}

	public void setOnwardTravelDateTemp(String onwardTravelDateTemp) {
		this.onwardTravelDateTemp = onwardTravelDateTemp;
	}

	public String getReturnTravelDatetemp() {
		return returnTravelDatetemp;
	}

	public void setReturnTravelDatetemp(String returnTravelDatetemp) {
		this.returnTravelDatetemp = returnTravelDatetemp;
	}

	public Date getOnwardTravelDate() {
		return onwardTravelDate;
	}

	public void setOnwardTravelDate(String onwardTravelDateTemp) {
		onwardTravelDate = DateConversion.StringToDate(onwardTravelDateTemp);
	}

	public Date getReturnTravelDate() {
		return ReturnTravelDate;
	}

	public void setReturnTravelDate(String returnTravelDatetemp) {
		ReturnTravelDate = DateConversion.StringToDate(returnTravelDatetemp);
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	
}
