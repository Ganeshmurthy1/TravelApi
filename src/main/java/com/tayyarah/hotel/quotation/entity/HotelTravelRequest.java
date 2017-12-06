package com.tayyarah.hotel.quotation.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.tayyarah.common.util.Timestampable;



@Entity
@Table(name="hotel_travel_request")
public class HotelTravelRequest extends Timestampable{

	@Transient
	private String checkIn;
	@Transient
	private String checkOut;	

	@Column(name = "currency")
	private String currency;
	@Column(name="emp_name")
	private String empName;
	@Column(name="entity")
	private String entity;
	@Column(name="tr_no")
	private String TRNo;
	@Column(name = "check_in_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkInDate;
	@Column(name = "check_out_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkOutDate;
	@Column(name="no_nights")
	private int noOfNights;
	@Column(name="country")
	private String country;
	@Column(name="city")
	private String city;
	@Column(name = "company_id")
	private int companyId;	
	@Column(name = "user_id")
	private int userId;
	@OneToMany(targetEntity = HotelTravelRequestQuotation.class,   mappedBy = "hotelTravelRequest" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<HotelTravelRequestQuotation> hotelTravelRequestQuotation; // list of rooms

	public List<HotelTravelRequestQuotation> getHotelTravelRequestQuotation() {
		return hotelTravelRequestQuotation;
	}
	public void setHotelTravelRequestQuotation(List<HotelTravelRequestQuotation> hotelTravelRequestQuotation) {
		this.hotelTravelRequestQuotation = hotelTravelRequestQuotation;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}
	public String getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getTRNo() {
		return TRNo;
	}
	public void setTRNo(String tRNo) {
		TRNo = tRNo;
	}
	public Date getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public int getNoOfNights() {
		return noOfNights;
	}
	public void setNoOfNights(int noOfNights) {
		this.noOfNights = noOfNights;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}