package com.tayyarah.umrah.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "tayyarah_umrah_contact_detail")
public class TayyarahUmrahContactDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	private String startDateTemp;
	@Transient
	private String returnDateTemp;

	@Id
	@GeneratedValue
	private int id;
	@Column(name="name")
	private String name; 
	@Column(name = "mobile")
	private String mobile;
	@Column(name = "email")
	private String email;
	@Column(name = "umrah_package")
	private String tayyarahUmrahPackage;  
	@Column(name = "no_of_child")
	private String child; 
	@Column(name = "no_of_adults")
	private String adult;
	@Column(name="tentative_start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Column(name="tentative_return_date")
	@Temporal(TemporalType.DATE)
	private Date returnDate;
	@Column(name = "departure_city")
	private String city; 
	@Column(name = "departure_country")
	private String country;
	@Column(name = "message")
	private String message;

	public TayyarahUmrahContactDetails(){
		super();
	}

	public TayyarahUmrahContactDetails(String startDateTemp, String returnDateTemp, int id, String name, String mobile,
			String email, String tayyarahUmrahPackage, String child, String adult, Date startDate, Date returnDate,
			String city, String country, String message) {
		super();
		this.startDateTemp = startDateTemp;
		this.returnDateTemp = returnDateTemp;
		this.id = id;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.tayyarahUmrahPackage = tayyarahUmrahPackage;
		this.child = child;
		this.adult = adult;
		this.startDate = startDate;
		this.returnDate = returnDate;
		this.city = city;
		this.country = country;
		this.message = message;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTayyarahUmrahPackage() {
		return tayyarahUmrahPackage;
	}
	public void setTayyarahUmrahPackage(String tayyarahUmrahPackage) {
		this.tayyarahUmrahPackage = tayyarahUmrahPackage;
	}
	public String getChild() {
		return child;
	}
	public void setChild(String child) {
		this.child = child;
	}
	public String getAdult() {
		return adult;
	}
	public void setAdult(String adult) {
		this.adult = adult;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStartDateTemp() {
		return startDateTemp;
	}
	public String getReturnDateTemp() {
		return returnDateTemp;
	}
	public void setStartDateTemp(String startDateTemp) {
		this.startDateTemp = startDateTemp;
	}
	public void setReturnDateTemp(String returnDateTemp) {
		this.returnDateTemp = returnDateTemp;
	}
}