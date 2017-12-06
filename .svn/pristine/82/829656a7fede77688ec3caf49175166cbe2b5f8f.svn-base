/**
@Author VimalSusaiRaj
20-Jul-2015 2015
Companyregistration.java
 */
/**
 * 
 */
package com.tayyarah.company.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.tayyarah.configuration.CommonConfig;


@Entity
@Table(name="company")
public class Company implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int companyid;
	@Column(name="username")
	private String Username;
	@Column(name="currency_code")
	private String currencyCode;
	@Column(name="location")
	private String location;
	@Column(name="companyname")
	private String Companyname;	
	@Column(name="countryname")
	private String Countryname;
	@Column(name="password")
	private String Password;
	@Column(name="address")
	private String Address;	
	@Column(name="email")
	private String Email;
	@Column(name="city")
	private String City;
	@Column(name="phone")
	private String Phone;	
	@Column(name="website")
	private String Website;
	@Column(name="imagepath")
	private String Imagepath;
	@Column(name="service")
	private String Service;
	@Column(name="companydescription")
	private String Companydescription;
	@Column(name="securityquestion")
	private String Securityquestion;
	@Column(name="securityanswer")
	private String Securityanswer;	
	@Column(name="billingcompany")
	private String Billingcompany;
	@Column(name="billingreference")
	private String Billingreference;
	@Column(name="billingaddress")
	private String Billingaddress;	 
	@Column(name="billingstate")
	private String Billingstate;
	@Column(name="billingcountry")
	private String Billingcountry;	
	@Column(name="createddate")
	private Date Createddate;	
	@Column(name="modifieddate")
	private Date Modifieddate;	
	@Column(name="is_status")
	private boolean isStatus;
	@Column(name="is_locked")
	private boolean isLocked;	
	@Column(name="language")
	private String Language;
	@Column(name="company_userid")
	private String company_userid;	
	@Column(name="parent_company_userid")
	private String parent_company_userid;
	@Column(name="super_company_userid")
	private String super_company_userid;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "company_roleid" ,referencedColumnName = "company_roleid")
	private CompanyRole companyRole; 
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "booking_system_id" ,referencedColumnName = "booking_id")
	private CompanyBookingSystemType bookingSystemType; 
	@Column(name="is_my_email_dir",columnDefinition="BIT(1) default false")
	private boolean isMyEmailDir;
	@Column(name="mail_status")
	private int mailStatus;
	@Column(name="email_code")
	private String  emailCode="0";
	@Column(name="register_number")
	private String registerNumber;	
	@Transient
	private String agreementExpiryDt;
	@Column(name = "agreement_expiry_date")
	@Temporal(TemporalType.DATE)
	private Date agreementExpiryDate;
	@Transient
	@Column(name="logo_displayable")
	private String logoDisplayable;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company",targetEntity = CompanyEntity.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CompanyEntity> companyEntity;

	@Column(name="company_gstin")
	private String companyGstIn;

	@Column(name="tems_and_condtions",columnDefinition = "LONGTEXT")
	private String temsandcondtions;

	@Transient
	private String stateCode;;

	@Column(name="country_code")
	private String countryCode;

	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getTemsandcondtions() {
		return temsandcondtions;
	}
	public void setTemsandcondtions(String temsandcondtions) {
		this.temsandcondtions = temsandcondtions;
	}
	public String getCompanyGstIn() {
		return companyGstIn;
	}
	public void setCompanyGstIn(String companyGstIn) {
		this.companyGstIn = companyGstIn;
	}
	public List<CompanyEntity> getCompanyEntity() {
		return companyEntity;
	}
	public void setCompanyEntity(List<CompanyEntity> companyEntity) {
		this.companyEntity = companyEntity;
	}

	public boolean isMyEmailDir() {
		return isMyEmailDir;
	}

	public void setMyEmailDir(boolean isMyEmailDir) {
		this.isMyEmailDir = isMyEmailDir;
	}

	public int getMailStatus() {
		return mailStatus;
	}
	public void setMailStatus(int mailStatus) {
		this.mailStatus = mailStatus;
	}
	public String getEmailCode() {
		return emailCode;
	}
	public void setEmailCode(String emailCode) {
		this.emailCode = emailCode;
	}	

	public String getRegisterNumber() {
		return registerNumber;
	}
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	public CompanyRole getCompanyRole() {
		return companyRole;
	}
	public void setCompanyRole(CompanyRole companyRole) {
		this.companyRole = companyRole;
	} 
	public String getService() {
		return Service;
	}
	public void setService(String service) {
		Service = service;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public int getCompanyid() {
		return companyid;
	}
	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	} 
	public String getLanguage() {
		return Language;
	}
	public void setLanguage(String language) {
		Language = language;
	}

	public String getCompanyname() {
		return Companyname;
	}
	public void setCompanyname(String companyname) {
		Companyname = companyname;
	}
	public String getCountryname() {
		return Countryname;
	}
	public void setCountryname(String countryname) {
		Countryname = countryname;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getImagepath() {
		return Imagepath;
	}
	public void setImagepath(String imagepath) {
		Imagepath = imagepath;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getWebsite() {
		return Website;
	}
	public void setWebsite(String website) {
		Website = website;
	}
	public String getSecurityquestion() {
		return Securityquestion;
	}
	public void setSecurityquestion(String securityquestion) {
		Securityquestion = securityquestion;
	}
	public String getSecurityanswer() {
		return Securityanswer;
	}
	public void setSecurityanswer(String securityanswer) {
		Securityanswer = securityanswer;
	}
	public String getBillingcompany() {
		return Billingcompany;
	}
	public void setBillingcompany(String billingcompany) {
		Billingcompany = billingcompany;
	}
	public String getBillingreference() {
		return Billingreference;
	}
	public void setBillingreference(String billingreference) {
		Billingreference = billingreference;
	}
	public String getBillingaddress() {
		return Billingaddress;
	}
	public void setBillingaddress(String billingaddress) {
		Billingaddress = billingaddress;
	}
	public String getBillingstate() {
		return Billingstate;
	}
	public void setBillingstate(String billingstate) {
		Billingstate = billingstate;
	}
	public String getBillingcountry() {
		return Billingcountry;
	}
	public void setBillingcountry(String billingcountry) {
		Billingcountry = billingcountry;
	}
	public Date getCreateddate() {
		return Createddate;
	}
	public void setCreateddate(Date createddate) {
		Createddate = createddate;
	}
	public Date getModifieddate() {
		return Modifieddate;
	}
	public void setModifieddate(Date modifieddate) {
		Modifieddate = modifieddate;
	}

	public String getCompanydescription() {
		return Companydescription;
	}
	public void setCompanydescription(String companydescription) {
		Companydescription = companydescription;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	/**
	 * @return the company_userid
	 */
	public String getCompany_userid() {
		return company_userid;
	}
	/**
	 * @param company_userid the company_userid to set
	 */
	public void setCompany_userid(String company_userid) {
		this.company_userid = company_userid;
	}
	/**
	 * @return the super_userid
	 */
	public String getParent_company_userid() {
		return parent_company_userid;
	}
	/**
	 * @param parent_company_userid the super_userid to set
	 */
	public void setParent_company_userid(String parent_company_userid) {
		this.parent_company_userid = parent_company_userid;
	}
	/**
	 * @return the bookingSystemType
	 */
	public CompanyBookingSystemType getBookingSystemType() {
		return bookingSystemType;
	}
	/**
	 * @param bookingSystemType the bookingSystemType to set
	 */
	public void setBookingSystemType(CompanyBookingSystemType bookingSystemType) {
		this.bookingSystemType = bookingSystemType;
	}
	/**
	 * @return the isStatus
	 */
	public boolean isStatus() {
		return isStatus;
	}
	/**
	 * @param isStatus the isStatus to set
	 */
	public void setStatus(boolean isStatus) {
		this.isStatus = isStatus;
	}
	/**
	 * @return the isLocked
	 */
	public boolean isLocked() {
		return isLocked;
	}
	/**
	 * @param isLocked the isLocked to set
	 */
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	public String getSuper_company_userid() {
		return super_company_userid;
	}
	public void setSuper_company_userid(String super_company_userid) {
		this.super_company_userid = super_company_userid;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getAgreementExpiryDt() {
		return agreementExpiryDt;
	}
	public void setAgreementExpiryDt(String agreementExpiryDt) {
		this.agreementExpiryDt = agreementExpiryDt;
	}
	public Date getAgreementExpiryDate() {
		return agreementExpiryDate;
	}
	public void setAgreementExpiryDate(Date agreementExpiryDate) {
		this.agreementExpiryDate = agreementExpiryDate;
	}
	public void setLogoDisplayable(String logoDisplayable) {
		this.logoDisplayable = logoDisplayable;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getLogoDisplayable() {
		if(logoDisplayable == null)
		{
			CommonConfig conf = CommonConfig.GetCommonConfig();	
			if(conf != null)
			{
				logoDisplayable = conf.getDefult_image_path();	
				if(Imagepath != null){
					logoDisplayable = (conf.getImage_path()+Imagepath);
				}	
			}

		}
		return logoDisplayable;
	}
	public void initLogoDisplayable() {
		CommonConfig conf = CommonConfig.GetCommonConfig();	
		if(conf != null)
		{
			logoDisplayable = conf.getDefult_image_path();	
			if(Imagepath != null){
				logoDisplayable = (conf.getImage_path()+Imagepath);
			}	
		}
	}
	public Company tranformDisplayable() {
		if(this.logoDisplayable == null)
		{
			CommonConfig conf = CommonConfig.GetCommonConfig();	
			if(conf != null)
			{
				this.logoDisplayable = conf.getDefult_image_path();	
				if(this.Imagepath != null){
					this.logoDisplayable = (conf.getImage_path()+this.Imagepath);
				}	
			}
		}
		try
		{
			this.Address = this.Address.replaceAll("[2C%-+.^:,]"," ");
			this.Billingaddress = this.Billingaddress.replaceAll("[2C%-+.^:,]"," ");
			this.Countryname = this.Countryname.replaceAll("[2C%-+.^:,]"," ");
			this.City = this.City.replaceAll("[2C%-+.^:,]"," ");
			this.location = this.location.replaceAll("[2C%-+.^:,]"," ");			
		}
		catch(Exception e)
		{

		}	
		return this;
	}
	@Override
	public String toString() {
		return "Company [companyid=" + companyid + ", Username=" + Username
				+ ", currencyCode=" + currencyCode + ", location=" + location
				+ ", Companyname=" + Companyname + ", Countryname="
				+ Countryname + ", Password=" + Password + ", Address="
				+ Address + ", Email=" + Email + ", City=" + City + ", Phone="
				+ Phone + ", Website=" + Website + ", Imagepath=" + Imagepath
				+ ", Service=" + Service + ", Companydescription="
				+ Companydescription + ", Securityquestion=" + Securityquestion
				+ ", Securityanswer=" + Securityanswer + ", Billingcompany="
				+ Billingcompany + ", Billingreference=" + Billingreference
				+ ", Billingaddress=" + Billingaddress + ", Billingstate="
				+ Billingstate + ", Billingcountry=" + Billingcountry
				+ ", Createddate=" + Createddate + ", Modifieddate="
				+ Modifieddate + ", isStatus=" + isStatus + ", isLocked="
				+ isLocked + ", Language=" + Language + ", company_userid="
				+ company_userid + ", parent_company_userid="
				+ parent_company_userid + ", super_company_userid="
				+ super_company_userid + ", companyRole=" + companyRole
				+ ", bookingSystemType=" + bookingSystemType
				+ ", logoDisplayable=" + logoDisplayable + ", mailStatus="
				+ mailStatus + ", emailCode=" + emailCode + "]";
	}

}
