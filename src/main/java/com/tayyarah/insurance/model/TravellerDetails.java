package com.tayyarah.insurance.model;

import java.io.Serializable;

public class TravellerDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String paxId;
	private String firstName;
	private String lastName;
	private String title;
	private String age;
	private String dateOfBirth;
	private String nominee;
	private String relationShipWithNominee;
	private String passportNumber;
	private String emailAddress;
	private String address;
	private String city;
	private String district;
	private String state;
	private String country;
	private String mobile;
	private String pinCode;
	private String customerId;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getAge() {
		return age;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public String getNominee() {
		return nominee;
	}
	public String getRelationShipWithNominee() {
		return relationShipWithNominee;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getDistrict() {
		return district;
	}
	public String getState() {
		return state;
	}
	public String getCountry() {
		return country;
	}
	public String getMobile() {
		return mobile;
	}
	public String getPinCode() {
		return pinCode;
	}
	
	public void setAge(String age) {
		this.age = age;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public void setNominee(String nominee) {
		this.nominee = nominee;
	}
	public void setRelationShipWithNominee(String relationShipWithNominee) {
		this.relationShipWithNominee = relationShipWithNominee;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getTitle() {
		return title;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPaxId() {
		return paxId;
	}
	public void setPaxId(String paxId) {
		this.paxId = paxId;
	}
}
