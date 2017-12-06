package com.tayyarah.api.flight.tbo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PassengerLCC implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("Title")
	private String title;
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("LastName")
	private String lastName;
	@JsonProperty("PaxType")
	private Integer paxType;
	@JsonProperty("DateOfBirth")
	private String dateOfBirth;
	@JsonProperty("Gender")
	private Integer gender;
	@JsonProperty("PassportNo")
	private String passportNo;
	@JsonProperty("PassportExpiry")
	private String passportExpiry;
	@JsonProperty("AddressLine1")
	private String addressLine1;
	@JsonProperty("AddressLine2")
	private String addressLine2;
	@JsonProperty("City")
	private String city;
	@JsonProperty("CountryCode")
	private String countryCode;
	@JsonProperty("CountryName")
	private String countryName;
	@JsonProperty("ContactNo")
	private String contactNo;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("IsLeadPax")
	private Boolean isLeadPax;
	@JsonProperty("FFAirline")
	private String fFAirline;
	@JsonProperty("FFNumber")
	private String fFNumber;
	@JsonProperty("Fare")
	private Fare fare;
	@JsonProperty("Meal")
	private Meal meal;
	@JsonProperty("Seat")
	private Seat seat;
	@JsonProperty("MealDynamic")
	private List<MealDynamic> mealDynamic = new ArrayList<MealDynamic>();
	@JsonProperty("Baggage")
	private List<Baggage> baggage = new ArrayList<Baggage>();
	@JsonProperty("GSTCompanyAddress")
	private String gSTCompanyAddress;
	@JsonProperty("GSTCompanyContactNumber")
	private String gSTCompanyContactNumber;
	@JsonProperty("GSTCompanyEmail")
	private String gSTCompanyEmail;
	@JsonProperty("GSTCompanyName")
	private String gSTCompanyName;
	@JsonProperty("GSTNumber")
	private String gSTNumber;
	

	public String getgSTCompanyAddress() {
		return gSTCompanyAddress;
	}

	public String getgSTCompanyContactNumber() {
		return gSTCompanyContactNumber;
	}

	public String getgSTCompanyEmail() {
		return gSTCompanyEmail;
	}

	public String getgSTCompanyName() {
		return gSTCompanyName;
	}

	public String getgSTNumber() {
		return gSTNumber;
	}

	public void setgSTCompanyAddress(String gSTCompanyAddress) {
		this.gSTCompanyAddress = gSTCompanyAddress;
	}

	public void setgSTCompanyContactNumber(String gSTCompanyContactNumber) {
		this.gSTCompanyContactNumber = gSTCompanyContactNumber;
	}

	public void setgSTCompanyEmail(String gSTCompanyEmail) {
		this.gSTCompanyEmail = gSTCompanyEmail;
	}

	public void setgSTCompanyName(String gSTCompanyName) {
		this.gSTCompanyName = gSTCompanyName;
	}

	public void setgSTNumber(String gSTNumber) {
		this.gSTNumber = gSTNumber;
	}

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/*public PassengerLCC(String title,String firstName,String lastName,Integer paxType,String dateOfBirth,Integer gender,String passportNo,String passportExpiry,String addressLine1,String addressLine2,String city,String countryCode,
		String countryName,String contactNo,String email,Boolean isLeadPax,String fFAirline,String fFNumber,Fare fare,List<MealDynamic> mealDynamic,Seat seat,List<Baggage> baggage){
	    this.title = title;
	    this.firstName = firstName;	    
	    this.lastName = lastName;
	    this.paxType = paxType;
	    this.dateOfBirth = dateOfBirth;
	    this.gender = gender;
	    this.passportNo = passportNo;
	    this.passportExpiry = passportExpiry;
	    this.addressLine1 = addressLine1;
	    this.addressLine2 = addressLine2;
	    this.city = city;
	    this.countryCode = countryCode;
	    this.countryName = countryName;
	    this.contactNo = contactNo;
	    this.email = email;
	    this.isLeadPax = isLeadPax;
	    this.fFAirline = fFAirline;
	    this.fFNumber = fFNumber;
	    this.fare = fare;
	    this.mealDynamic = mealDynamic;
	    this.baggage = baggage;
	
	}
	
	public PassengerLCC(String title,String firstName,String lastName,Integer paxType,String dateOfBirth,Integer gender,String passportNo,String passportExpiry,String addressLine1,String addressLine2,String city,String countryCode,
			String countryName,String contactNo,String email,Boolean isLeadPax,String fFAirline,String fFNumber,Fare fare,Meal meal,Seat seat,List<Baggage> baggage){
		    this.title = title;
		    this.firstName = firstName;	    
		    this.lastName = lastName;
		    this.paxType = paxType;
		    this.dateOfBirth = dateOfBirth;
		    this.gender = gender;
		    this.passportNo = passportNo;
		    this.passportExpiry = passportExpiry;
		    this.addressLine1 = addressLine1;
		    this.addressLine2 = addressLine2;
		    this.city = city;
		    this.countryCode = countryCode;
		    this.countryName = countryName;
		    this.contactNo = contactNo;
		    this.email = email;
		    this.isLeadPax = isLeadPax;
		    this.fFAirline = fFAirline;
		    this.fFNumber = fFNumber;
		    this.fare = fare;
		    this.meal = meal;
		    this.baggage = baggage;
		
		}
		*/
	
	/**
	* 
	* @return
	* The firstName
	*/
	@JsonProperty("FirstName")
	public String getFirstName() {
	return firstName;
	}

	/**
	* 
	* @param firstName
	* The FirstName
	*/
	@JsonProperty("FirstName")
	public void setFirstName(String firstName) {
	this.firstName = firstName;
	}

	/**
	* 
	* @return
	* The lastName
	*/
	@JsonProperty("LastName")
	public String getLastName() {
	return lastName;
	}

	/**
	* 
	* @param lastName
	* The LastName
	*/
	@JsonProperty("LastName")
	public void setLastName(String lastName) {
	this.lastName = lastName;
	}

	/**
	* 
	* @return
	* The paxType
	*/
	@JsonProperty("PaxType")
	public Integer getPaxType() {
	return paxType;
	}

	/**
	* 
	* @param paxType
	* The PaxType
	*/
	@JsonProperty("PaxType")
	public void setPaxType(Integer paxType) {
	this.paxType = paxType;
	}

	/**
	* 
	* @return
	* The dateOfBirth
	*/
	@JsonProperty("DateOfBirth")
	public String getDateOfBirth() {
	return dateOfBirth;
	}

	/**
	* 
	* @param dateOfBirth
	* The DateOfBirth
	*/
	@JsonProperty("DateOfBirth")
	public void setDateOfBirth(String dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
	}

	/**
	* 
	* @return
	* The gender
	*/
	@JsonProperty("Gender")
	public Integer getGender() {
	return gender;
	}

	/**
	* 
	* @param gender
	* The Gender
	*/
	@JsonProperty("Gender")
	public void setGender(Integer gender) {
	this.gender = gender;
	}

	/**
	* 
	* @return
	* The passportNo
	*/
	@JsonProperty("PassportNo")
	public String getPassportNo() {
	return passportNo;
	}

	/**
	* 
	* @param passportNo
	* The PassportNo
	*/
	@JsonProperty("PassportNo")
	public void setPassportNo(String passportNo) {
	this.passportNo = passportNo;
	}

	/**
	* 
	* @return
	* The passportExpiry
	*/
	@JsonProperty("PassportExpiry")
	public String getPassportExpiry() {
	return passportExpiry;
	}

	/**
	* 
	* @param passportExpiry
	* The PassportExpiry
	*/
	@JsonProperty("PassportExpiry")
	public void setPassportExpiry(String passportExpiry) {
	this.passportExpiry = passportExpiry;
	}

	/**
	* 
	* @return
	* The addressLine1
	*/
	@JsonProperty("AddressLine1")
	public String getAddressLine1() {
	return addressLine1;
	}

	/**
	* 
	* @param addressLine1
	* The AddressLine1
	*/
	@JsonProperty("AddressLine1")
	public void setAddressLine1(String addressLine1) {
	this.addressLine1 = addressLine1;
	}

	/**
	* 
	* @return
	* The addressLine2
	*/
	@JsonProperty("AddressLine2")
	public String getAddressLine2() {
	return addressLine2;
	}

	/**
	* 
	* @param addressLine2
	* The AddressLine2
	*/
	@JsonProperty("AddressLine2")
	public void setAddressLine2(String addressLine2) {
	this.addressLine2 = addressLine2;
	}

	/**
	* 
	* @return
	* The city
	*/
	@JsonProperty("City")
	public String getCity() {
	return city;
	}

	/**
	* 
	* @param city
	* The City
	*/
	@JsonProperty("City")
	public void setCity(String city) {
	this.city = city;
	}

	/**
	* 
	* @return
	* The countryCode
	*/
	@JsonProperty("CountryCode")
	public String getCountryCode() {
	return countryCode;
	}

	/**
	* 
	* @param countryCode
	* The CountryCode
	*/
	@JsonProperty("CountryCode")
	public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
	}

	/**
	* 
	* @return
	* The countryName
	*/
	@JsonProperty("CountryName")
	public String getCountryName() {
	return countryName;
	}

	/**
	* 
	* @param countryName
	* The CountryName
	*/
	@JsonProperty("CountryName")
	public void setCountryName(String countryName) {
	this.countryName = countryName;
	}

	/**
	* 
	* @return
	* The contactNo
	*/
	@JsonProperty("ContactNo")
	public String getContactNo() {
	return contactNo;
	}

	/**
	* 
	* @param contactNo
	* The ContactNo
	*/
	@JsonProperty("ContactNo")
	public void setContactNo(String contactNo) {
	this.contactNo = contactNo;
	}

	/**
	* 
	* @return
	* The email
	*/
	@JsonProperty("Email")
	public String getEmail() {
	return email;
	}

	/**
	* 
	* @param email
	* The Email
	*/
	@JsonProperty("Email")
	public void setEmail(String email) {
	this.email = email;
	}

	/**
	* 
	* @return
	* The isLeadPax
	*/
	@JsonProperty("IsLeadPax")
	public Boolean getIsLeadPax() {
	return isLeadPax;
	}

	/**
	* 
	* @param isLeadPax
	* The IsLeadPax
	*/
	@JsonProperty("IsLeadPax")
	public void setIsLeadPax(Boolean isLeadPax) {
	this.isLeadPax = isLeadPax;
	}

	/**
	* 
	* @return
	* The fFAirline
	*/
	@JsonProperty("FFAirline")
	public String getFFAirline() {
	return fFAirline;
	}

	/**
	* 
	* @param fFAirline
	* The FFAirline
	*/
	@JsonProperty("FFAirline")
	public void setFFAirline(String fFAirline) {
	this.fFAirline = fFAirline;
	}

	/**
	* 
	* @return
	* The fFNumber
	*/
	@JsonProperty("FFNumber")
	public String getFFNumber() {
	return fFNumber;
	}

	/**
	* 
	* @param fFNumber
	* The FFNumber
	*/
	@JsonProperty("FFNumber")
	public void setFFNumber(String fFNumber) {
	this.fFNumber = fFNumber;
	}

	/**
	* 
	* @return
	* The fare
	*/
	@JsonProperty("Fare")
	public Fare getFare() {
	return fare;
	}

	/**
	* 
	* @param fare
	* The Fare
	*/
	@JsonProperty("Fare")
	public void setFare(Fare fare) {
	this.fare = fare;
	}

	/**
	* 
	* @return
	* The mealDynamic
	*/
	@JsonProperty("MealDynamic")
	public List<MealDynamic> getMealDynamic() {
	return mealDynamic;
	}

	/**
	* 
	* @param mealDynamic
	* The MealDynamic
	*/
	@JsonProperty("MealDynamic")
	public void setMealDynamic(List<MealDynamic> mealDynamic) {
	this.mealDynamic = mealDynamic;
	}

	
	/**
	* 
	* @return
	* The title
	*/
	@JsonProperty("Title")
	public String getTitle() {
	return title;
	}

	/**
	* 
	* @param title
	* The Title
	*/
	@JsonProperty("Title")
	public void setTitle(String title) {
	this.title = title;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}
	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	
	/**
	* 
	* @return
	* The baggage
	*/
	@JsonProperty("Baggage")
	public List<Baggage> getBaggage() {
	return baggage;
	}

	/**
	* 
	* @param baggage
	* The Baggage
	*/
	@JsonProperty("Baggage")
	public void setBaggage(List<Baggage> baggage) {
	this.baggage = baggage;
	}

	}
