package com.tayyarah.company.entity;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="company_entity")
public class CompanyEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="company_entity_id")
	private int companyEntityId;
	@Column(name="company_entity_gstin")
	private String companyEntityGstIn;
	@Column(name="company_name")
	private String Companyname;
	@Column(name="company_entity_name")
	private String CompanyEntityName;	
	@Column(name="countryname")
	private String Countryname;
	@Column(name="address1")
	private String Address1;
	@Column(name="address2")
	private String Address2;
	@Column(name="email")
	private String Email;
	@Column(name="city")
	private String City;
	@Column(name="state")
	private String State;
	@Column(name="state_code")
	private String StateCode;
	@Column(name="phone_no")
	private String PhoneNo;
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Company.class)
	@JoinColumn(name = "company_id", referencedColumnName = "companyid")
	private Company company;

	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}

	public String getCompanyname() {
		return Companyname;
	}
	public void setCompanyname(String companyname) {
		Companyname = companyname;
	}
	public String getCompanyEntityName() {
		return CompanyEntityName;
	}
	public void setCompanyEntityName(String companyEntityName) {
		CompanyEntityName = companyEntityName;
	}
	public String getCountryname() {
		return Countryname;
	}
	public void setCountryname(String countryname) {
		Countryname = countryname;
	}
	public String getAddress1() {
		return Address1;
	}
	public void setAddress1(String address1) {
		Address1 = address1;
	}
	public String getAddress2() {
		return Address2;
	}
	public void setAddress2(String address2) {
		Address2 = address2;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getPhoneNo() {
		return PhoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		PhoneNo = phoneNo;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getCompanyEntityGstIn() {
		return companyEntityGstIn;
	}
	public void setCompanyEntityGstIn(String companyEntityGstIn) {
		this.companyEntityGstIn = companyEntityGstIn;
	}
	public String getStateCode() {
		return StateCode;
	}
	public void setStateCode(String stateCode) {
		StateCode = stateCode;
	}
}

