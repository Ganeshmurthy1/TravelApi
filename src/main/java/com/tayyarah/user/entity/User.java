/**
@Author VimalSusaiRaj
20-Jul-2015 2015
Userregistration.java
 */
/**
 * 
 */
package com.tayyarah.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tayyarah.configuration.CommonConfig;

@Entity
@Table(name="user")
public class User implements Serializable{

	@Override
	public String toString() {
		return "User [id=" + id + ", userrole_id=" + userrole_id
				+ ", agentWallet=" + agentWallet + ", company_userid="
				+ company_userid + ", Username=" + Username + ", Firstname="
				+ Firstname + ", Lastname=" + Lastname + ", Password="
				+ Password + ", Address=" + Address + ", City=" + City
				+ ", Countryname=" + Countryname + ", Imagepath=" + Imagepath
				+ ", Securityquestion=" + Securityquestion
				+ ", Securityanswer=" + Securityanswer + ", Email=" + Email
				+ ", Phone=" + Phone + ", Mobile=" + Mobile + ", Description="
				+ Description + ", Language=" + Language
				+ ", createdbyCompanyUserId=" + createdbyCompanyUserId
				+ ", modifiedbyCompanyUserId=" + modifiedbyCompanyUserId
				+ ", Createddate=" + Createddate + ", LockedDate=" + LockedDate
				+ ", attemt=" + attemt + ", Companyid=" + Companyid
				+ ", isStatus=" + isStatus + ", isLocked=" + isLocked
				+ ", mailStatus=" + mailStatus + ", emailCode=" + emailCode
				+ ", logoDisplayable=" + logoDisplayable + ", Modifieddate="
				+ Modifieddate + "]";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_role_id", referencedColumnName = "roleid")
	private UserRole userrole_id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "walletId" )
	private UserWallet agentWallet;

	@Column(name="company_userid")
	private String company_userid;
	@Column(name="username")
	private String Username;
	@Column(name="firstname")
	private String Firstname;
	@Column(name="lastname")
	private String Lastname;
	@Column(name="password")
	private String Password;
	@Column(name="address")
	private String Address;
	@Column(name="city")
	private String City;
	@Column(name="countryname")
	private String Countryname;
	@Column(name="imagepath")
	private String Imagepath;
	@Column(name="securityquestion")
	private String Securityquestion;
	@Column(name="securityanswer")
	private String Securityanswer;	
	@Column(name="email")
	private String Email;
	@Column(name="phone")
	private String Phone;
	@Column(name="mobile")
	private String Mobile;
	@Column(name="description")
	private String Description;
	/*@Column(name="status")
	private String status;*/
	@Column(name="language")
	private String Language;
	@Column(name="createdby_company_user_id")
	private int createdbyCompanyUserId;
	@Column(name="modifiedby_company_user_id")
	private int modifiedbyCompanyUserId;
	@Column(name="createddate")
	private Date Createddate;
	/*@Column(name="locked")
	private String Locked;*/
	@Column(name="lockedDate")
	private Date LockedDate;
	@Column(name="attempts")
	private int attemt;
	@Column(name="companyid")
	private int Companyid;
	@Column(name="is_status")
	private boolean isStatus;
	@Column(name="is_locked")
	private boolean isLocked;
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<UserRole> roles;*/
	
	
	@Column(name="mail_status")
	private int mailStatus;
	
	@Column(name="email_code")
	private String  emailCode;
	
	@Transient
	@Column(name="logo_displayable")
	private String logoDisplayable;
	
	@Column(name="passportno")
	private String passportno; 
	@Column(name="passport_expirydate")
	private String passport_expirydate; 
	@Column(name="passport_issuedate")
	private String passport_issuedate; 
	@Column(name="passport_issueplace")
	private String passport_issueplace; 
	@Column(name="dateofbirth")
	private String dateofbirth; 	
	private transient String headers;
	@Column(name="passport_size_image")
	private String  passportSizeImage;
	@Column(name="passport_scan_copy")
	private String  passportScanCopy;
	@Column(name="designation")
	private String  designation;
	@Column(name="country_code")
	 private String countryCode;
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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

	
	public User tranformDisplayable() {
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
			this.Firstname = this.Firstname.replaceAll("[2C%-+.^:,]"," ");
			this.Countryname = this.Countryname.replaceAll("[2C%-+.^:,]"," ");
			this.City = this.City.replaceAll("[2C%-+.^:,]"," ");
			this.Lastname = this.Lastname.replaceAll("[2C%-+.^:,]"," ");			
		}
		catch(Exception e)
		{
			
		}	
		return this;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCompanyid() {
		return Companyid;
	}

	public void setCompanyid(int companyid) {
		Companyid = companyid;
	}


	public Date getModifieddate() {
		return Modifieddate;
	}

	public void setModifieddate(Date modifieddate) {
		Modifieddate = modifieddate;
	}


	@Column(name="modifieddate")
	private Date Modifieddate;	
	/*public int getCompanyid() {
		return Companyid;
	}
	public void setCompanyid(int companyid) {
		Companyid = companyid;
	}*/
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getFirstname() {
		return Firstname;
	}
	public void setFirstname(String firstname) {
		Firstname = firstname;
	}
	public String getLastname() {
		return Lastname;
	}
	public void setLastname(String lastname) {
		Lastname = lastname;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	/*public String getFax() {
		return Fax;
	}
	public void setFax(String fax) {
		Fax = fax;
	}*/
	/*public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}*/
	public String getLanguage() {
		return Language;
	}
	public void setLanguage(String language) {
		Language = language;
	}
	 
	public Date getCreateddate() {
		return Createddate;
	}
	public void setCreateddate(Date createddate) {
		Createddate = createddate;
	}
	/*public String getLocked() {
		return Locked;
	}
	public void setLocked(String locked) {
		Locked = locked;
	}*/
	public Date getLockedDate() {
		return LockedDate;
	}
	public void setLockedDate(Date lockedDate) {
		LockedDate = lockedDate;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getCountryname() {
		return Countryname;
	}
	public void setCountryname(String countryname) {
		Countryname = countryname;
	}
	public String getImagepath() {
		return Imagepath;
	}
	public void setImagepath(String imagepath) {
		Imagepath = imagepath;
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


	/**
	 * @return the userrole_id
	 */
	public UserRole getUserrole_id() {
		return userrole_id;
	}

	/**
	 * @param userrole_id the userrole_id to set
	 */
	public void setUserrole_id(UserRole userrole_id) {
		this.userrole_id = userrole_id;
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
	 * @return the attemt
	 */
	public int getAttemt() {
		return attemt;
	}

	/**
	 * @param attemt the attemt to set
	 */
	public void setAttemt(int attemt) {
		this.attemt = attemt;
	}

	/**
	 * @return the agentWallet
	 */
	public UserWallet getAgentWallet() {
		return agentWallet;
	}

	/**
	 * @param agentWallet the agentWallet to set
	 */
	public void setAgentWallet(UserWallet agentWallet) {
		this.agentWallet = agentWallet;
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

	public int getCreatedbyCompanyUserId() {
		return createdbyCompanyUserId;
	}

	public void setCreatedbyCompanyUserId(int createdbyCompanyUserId) {
		this.createdbyCompanyUserId = createdbyCompanyUserId;
	}

	public int getModifiedbyCompanyUserId() {
		return modifiedbyCompanyUserId;
	}

	public void setModifiedbyCompanyUserId(int modifiedbyCompanyUserId) {
		this.modifiedbyCompanyUserId = modifiedbyCompanyUserId;
	}
	public String getPassportno() {
		return passportno;
	}
	public void setPassportno(String passportno) {
		this.passportno = passportno;
	}
	public String getPassport_expirydate() {
		return passport_expirydate;
	}
	public void setPassport_expirydate(String passport_expirydate) {
		this.passport_expirydate = passport_expirydate;
	}
	public String getPassport_issuedate() {
		return passport_issuedate;
	}
	public void setPassport_issuedate(String passport_issuedate) {
		this.passport_issuedate = passport_issuedate;
	}
	public String getPassport_issueplace() {
		return passport_issueplace;
	}
	public void setPassport_issueplace(String passport_issueplace) {
		this.passport_issueplace = passport_issueplace;
	}
	public String getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getHeaders() {
		return headers;
	}
	public void setHeaders(String headers) {
		this.headers = headers;
	}
	public String getPassportSizeImage() {
		return passportSizeImage;
	}
	public void setPassportSizeImage(String passportSizeImage) {
		this.passportSizeImage = passportSizeImage;
	}
	public String getPassportScanCopy() {
		return passportScanCopy;
	}
	public void setPassportScanCopy(String passportScanCopy) {
		this.passportScanCopy = passportScanCopy;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public void setLogoDisplayable(String logoDisplayable) {
		this.logoDisplayable = logoDisplayable;
	}

	 

}
