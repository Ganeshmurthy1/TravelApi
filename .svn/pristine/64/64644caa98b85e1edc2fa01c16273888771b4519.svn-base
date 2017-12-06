package com.tayyarah.flight.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.entity.RmConfigTripDetailsModel;
import com.tayyarah.common.util.Timestampable;




@Entity
@Table(name = "flight_order_customer")
public class FlightOrderCustomer  extends Timestampable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	private OrderCustomer flightCustomer;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_row_id", referencedColumnName = "id")
	private FlightOrderRow flightOrderRow;



	@Column(name = "firstName")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "middleName")
	private String middleName;
	
	@Column(name = "title")
	private String title;

	@Column(name = "passenger_type_code")
	private String passengerTypeCode;

	@Column(name = "nationality")
	private String nationality;
	
	@Column(name = "city")
	private String city;

	@Column(name = "countryid")
	private String countryId;

	@Column(name = "gender")
	private String gender;

	@Column(name = "birthday")
	private String birthday;

	@Column(name = "address")
	private String address;

	@Column(name = "passport_expiry")
	@Temporal(TemporalType.DATE)
	
	private Date passportExpiryDate;

	@Column(name = "mobile")
	private String mobile;
	@Column(name = "phone")
	private String phone;
	@Column(name = "zip")
	private String zip;
	
	@Column(name = "passport_no")
	private String  passportNo;
	
	@Column(name = "passport_issuing_country")
	private String passportIssuingCountry;
	
	@Column(name = "eticketnumber")
	private String eticketnumber;
	
	@Column(name = "eticket_id")
	private String eticketid;
	
	@OneToOne(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "rm_config_trip_id", referencedColumnName = "id")
	private RmConfigTripDetailsModel rmConfigTripDetailsModel;
	
	@Column(name = "pax_id")
	private String paxId;
	
	
	public String getEticketid() {
		return eticketid;
	}
	public void setEticketid(String eticketid) {
		this.eticketid = eticketid;
	}
	public String getEticketnumber() {
		return eticketnumber;
	}
	public void setEticketnumber(String eticketnumber) {
		this.eticketnumber = eticketnumber;
	}	
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}	
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getPassportIssuingCountry() {
		return passportIssuingCountry;
	}
	public void setPassportIssuingCountry(String passportIssuingCountry) {
		this.passportIssuingCountry = passportIssuingCountry;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getGender() {
		return gender;
	}
	public OrderCustomer getFlightCustomer() {
		return flightCustomer;
	}
	public void setFlightCustomer(OrderCustomer flightCustomer) {
		this.flightCustomer = flightCustomer;
	}
	public FlightOrderRow getFlightOrderRow() {
		return flightOrderRow;
	}
	public void setFlightOrderRow(FlightOrderRow flightOrderRow) {
		this.flightOrderRow = flightOrderRow;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public OrderCustomer getCustomer() {
		return flightCustomer;
	}
	public void setCustomer(OrderCustomer flightCustomer) {
		this.flightCustomer = flightCustomer;
	}
	public FlightOrderRow getOrderRow() {
		return flightOrderRow;
	}
	public void setOrderRow(FlightOrderRow flightOrderRow) {
		this.flightOrderRow = flightOrderRow;
	}
	public Date getPassportExpiryDate() {
		return passportExpiryDate;
	}
	public void setPassportExpiryDate(Date passportExpiryDate) {
		this.passportExpiryDate = passportExpiryDate;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void setPassengerTypeCode(String passengerTypeCode) {
		this.passengerTypeCode = passengerTypeCode;
	}
	public String getPassengerTypeCode() {
		return passengerTypeCode;
	}
	public RmConfigTripDetailsModel getRmConfigTripDetailsModel() {
		return rmConfigTripDetailsModel;
	}
	public void setRmConfigTripDetailsModel(RmConfigTripDetailsModel rmConfigTripDetailsModel) {
		this.rmConfigTripDetailsModel = rmConfigTripDetailsModel;
	}
	public String getPaxId() {
		return paxId;
	}
	public void setPaxId(String paxId) {
		this.paxId = paxId;
	}
	
	
}
