
package com.tayyarah.api.hotel.tbo.book.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "Title",
    "FirstName",
    "MiddleName",
    "LastName",
    "Phoneno",
    "Email",
    "PaxType",
    "LeadPassenger",
    "Age",
    "PassportNo",
    "PassportIssueDate",
    "PassportExpDate"
})
public class HotelPassenger {

    @JsonProperty("Title")
    private String Title;
    @JsonProperty("FirstName")
    private String FirstName;
    @JsonProperty("MiddleName")
    private String MiddleName;
    @JsonProperty("LastName")
    private String LastName;
    @JsonProperty("Phoneno")
    private String Phoneno;
    @JsonProperty("Email")
    private String Email;
    @JsonProperty("PaxType")
    private Integer PaxType;
    @JsonProperty("LeadPassenger")
    private Boolean LeadPassenger;
    @JsonProperty("Age")
    private Integer Age;
    @JsonProperty("PassportNo")
    private String PassportNo;
    @JsonProperty("PassportIssueDate")
    private String PassportIssueDate;
    @JsonProperty("PassportExpDate")
    private String PassportExpDate;
    @JsonIgnore
    private Map<String, String> additionalProperties = new HashMap<String, String>();
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

	/**
     * No args constructor for use in serialization
     * 
     */
    public HotelPassenger() {
    }

    /**
     * 
     * @param PaxType
     * @param Age
     * @param Email
     * @param Phoneno
     * @param Middlename
     * @param LeadPassenger
     * @param FirstName
     * @param PassportNo
     * @param LastName
     * @param PassportExpDate
     * @param PassportIssueDate
     * @param Title
     */
    public HotelPassenger(String Title, String FirstName, String Middlename, String LastName, String Phoneno, String Email, Integer PaxType, Boolean LeadPassenger, Integer Age, String PassportNo, String PassportIssueDate, String PassportExpDate) {
        this.Title = Title;
        this.FirstName = FirstName;
        this.MiddleName = Middlename;
        this.LastName = LastName;
        this.Phoneno = Phoneno;
        this.Email = Email;
        this.PaxType = PaxType;
        this.LeadPassenger = LeadPassenger;
        this.Age = Age;
        this.PassportNo = PassportNo;
        this.PassportIssueDate = PassportIssueDate;
        this.PassportExpDate = PassportExpDate;
    }

    /**
     * 
     * @return
     *     The Title
     */
    @JsonProperty("Title")
    public String getTitle() {
        return Title;
    }

    /**
     * 
     * @param Title
     *     The Title
     */
    @JsonProperty("Title")
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     * 
     * @return
     *     The FirstName
     */
    @JsonProperty("FirstName")
    public String getFirstName() {
        return FirstName;
    }

    /**
     * 
     * @param FirstName
     *     The FirstName
     */
    @JsonProperty("FirstName")
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    /**
     * 
     * @return
     *     The Middlename
     */
    @JsonProperty("MiddleName")
    public String getMiddleName() {
        return MiddleName;
    }

    /**
     * 
     * @param Middlename
     *     The Middlename
     */
    @JsonProperty("MiddleName")
    public void setMiddleName(String Middlename) {
        this.MiddleName = Middlename;
    }

    /**
     * 
     * @return
     *     The LastName
     */
    @JsonProperty("LastName")
    public String getLastName() {
        return LastName;
    }

    /**
     * 
     * @param LastName
     *     The LastName
     */
    @JsonProperty("LastName")
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    /**
     * 
     * @return
     *     The Phoneno
     */
    @JsonProperty("Phoneno")
    public String getPhoneno() {
        return Phoneno;
    }

    /**
     * 
     * @param Phoneno
     *     The Phoneno
     */
    @JsonProperty("Phoneno")
    public void setPhoneno(String Phoneno) {
        this.Phoneno = Phoneno;
    }

    /**
     * 
     * @return
     *     The Email
     */
    @JsonProperty("Email")
    public String getEmail() {
        return Email;
    }

    /**
     * 
     * @param Email
     *     The Email
     */
    @JsonProperty("Email")
    public void setEmail(String Email) {
        this.Email = Email;
    }

    /**
     * 
     * @return
     *     The PaxType
     */
    @JsonProperty("PaxType")
    public Integer getPaxType() {
        return PaxType;
    }

    /**
     * 
     * @param PaxType
     *     The PaxType
     */
    @JsonProperty("PaxType")
    public void setPaxType(Integer PaxType) {
        this.PaxType = PaxType;
    }

    /**
     * 
     * @return
     *     The LeadPassenger
     */
    @JsonProperty("LeadPassenger")
    public Boolean getLeadPassenger() {
        return LeadPassenger;
    }

    /**
     * 
     * @param LeadPassenger
     *     The LeadPassenger
     */
    @JsonProperty("LeadPassenger")
    public void setLeadPassenger(Boolean LeadPassenger) {
        this.LeadPassenger = LeadPassenger;
    }

    /**
     * 
     * @return
     *     The Age
     */
    @JsonProperty("Age")
    public Integer getAge() {
        return Age;
    }

    /**
     * 
     * @param Age
     *     The Age
     */
    @JsonProperty("Age")
    public void setAge(Integer Age) {
        this.Age = Age;
    }

    /**
     * 
     * @return
     *     The PassportNo
     */
    @JsonProperty("PassportNo")
    public String getPassportNo() {
        return PassportNo;
    }

    /**
     * 
     * @param PassportNo
     *     The PassportNo
     */
    @JsonProperty("PassportNo")
    public void setPassportNo(String PassportNo) {
        this.PassportNo = PassportNo;
    }

    /**
     * 
     * @return
     *     The PassportIssueDate
     */
    @JsonProperty("PassportIssueDate")
    public String getPassportIssueDate() {
        return PassportIssueDate;
    }

    /**
     * 
     * @param PassportIssueDate
     *     The PassportIssueDate
     */
    @JsonProperty("PassportIssueDate")
    public void setPassportIssueDate(String PassportIssueDate) {
        this.PassportIssueDate = PassportIssueDate;
    }

    /**
     * 
     * @return
     *     The PassportExpDate
     */
    @JsonProperty("PassportExpDate")
    public String getPassportExpDate() {
        return PassportExpDate;
    }

    /**
     * 
     * @param PassportExpDate
     *     The PassportExpDate
     */
    @JsonProperty("PassportExpDate")
    public void setPassportExpDate(String PassportExpDate) {
        this.PassportExpDate = PassportExpDate;
    }

    @JsonAnyGetter
    public Map<String, String> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, String value) {
        this.additionalProperties.put(name, value);
    }

}
